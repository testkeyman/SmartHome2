package com.eastedge.smarthome.activitys;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.eastedge.smarthome.R;
import com.eastedge.smarthome.activitygroup.MainGroup;
import com.eastedge.smarthome.activitygroup.MainGroup.SwitchAdd;
import com.eastedge.smarthome.adapters.DataAdapter;
import com.eastedge.smarthome.adapters.RoomAdapter;
import com.eastedge.smarthome.dao.RoomDao;
import com.eastedge.smarthome.domain.Customview;
import com.eastedge.smarthome.domain.Room;
import com.eastedge.smarthome.dragview.Configure;
import com.eastedge.smarthome.dragview.DragGridView;
import com.eastedge.smarthome.dragview.DragGridView.ChildPopupOnClick;
import com.eastedge.smarthome.dragview.DragGridView.SwitchPositionListener;
import com.eastedge.smarthome.util.CommentsUtil;
import com.eastedge.smarthome.util.DensityUtil;

/** 房间控制 */
public class RoomControlActivity extends BaseActivity implements
		android.view.View.OnClickListener {
	private final String ROOMTAG = "RoomControlActivity---->";
	private RoomDao roomDao;
	ArrayList<Room> roomList;
	ArrayList<Room> roomContainerList = null;
	Room itemRoom;
	/**
	 * 标志来自那个窗体（0：默认，2：来自生活情景）
	 */
	private int fromActivity;
	private ArrayList<Customview> viewLists = null;

	@Override
	protected boolean isInitMain() {
		return true;
	}

	@Override
	protected void setView() {
		setContentView(R.layout.activity_room_control);

	}

	@Override
	protected void initData() {
		fromActivity = getIntent().getIntExtra("fromActivity", 0);
		roomDao = new RoomDao(this);
		roomList = roomDao.findAllByRoomCtrl();
		if (roomDao.ifFirstInitContainer()) {
			progressDialog = new ProgressDialog(RoomControlActivity.this);
			progressDialog.setTitle(R.string.progress_title);
			progressDialog.show();
			new Thread() {
				public void run() {
					for (int i = 100; i < 120; i++) {
						Room r = new Room(i, i);
						roomDao.insertIntoRoomContainer(r);
					}
					roomContainerList = roomDao.findAllByRoomContainer();
					mHandler.sendEmptyMessage(0);
				};
			}.start();
		} else {
			roomContainerList = roomDao.findAllByRoomContainer();
		}
		int sceneSize = roomList.size();
		for (int i = 0; i < sceneSize; i++) {
			listData.add(roomList.get(i));
		}
	}

	@Override
	/**
	 * 添加新的GridView 全部数据的集合集lists.size()==countPage;
	 * 
	 * @param countPages
	 *            总共有多少个页面
	 * @return linear 新添加的页面
	 */
	public LinearLayout addGridView(final int countPages) {

		linear = new LinearLayout(RoomControlActivity.this);
		gridView = new DragGridView(RoomControlActivity.this);
		// 房间控制窗口标识为3
		gridView.whichPageMove = DragGridView.HANDLE_ROOM_CTR;
		// 获取当前页面
		curentPage = countPages;

		dataAdapter = new RoomAdapter(this, lists.get(curentPage));

		// TODO

		gridView.setAdapter(dataAdapter);
		gridView.setNumColumns(NUM_COlUMNS);
		gridView.setHorizontalSpacing(DensityUtil.dip2px(this, 40));
		gridView.setVerticalSpacing(DensityUtil.dip2px(this, 8));
		// 点击gridView时，改变背景的
		// gridView.setSelector(R.drawable.grid_light);
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				itemRoom = (Room) lists.get(Configure.curentPage).get(arg2);
				toDoEdit();
			}
		});
		gridView.setPageListener(new DragGridView.G_PageListener() {
			@Override
			public void page(int cases, int page) {
				switch (cases) {
				case 0:// 滑动页面
					mScrollLayout.snapToScreen(page);
					setCurPage(page);
					new Handler().postDelayed(new Runnable() {
						@Override
						public void run() {
							Configure.isChangingPage = false;
						}
					}, 500);
					break;
				}
			}
		});
		gridView.setSelector(R.drawable.folder_select_bg);

		// 跨屏拖动
		gridView.setOnItemChangeListener(new DragGridView.G_ItemChangeListener() {
			@Override
			public void change(int from, int to, int count) {
				Room toString = (Room) lists.get(Configure.curentPage - count)
						.get(from);

				lists.get(Configure.curentPage - count).add(from,
						lists.get(Configure.curentPage).get(to));
				lists.get(Configure.curentPage - count).remove(from + 1);
				lists.get(Configure.curentPage).add(to, toString);
				lists.get(Configure.curentPage).remove(to + 1);

				((DataAdapter) ((gridViews.get(Configure.curentPage - count))
						.getAdapter())).notifyDataSetChanged();
				((DataAdapter) ((gridViews.get(Configure.curentPage))
						.getAdapter())).notifyDataSetChanged();
			}
		});
		if (fromActivity != 2) {// 如果来自默认，添加长按，拖拽监听器
			// 监听弹框选项操作
			gridView.setChildPopupOnClick(new ChildPopupOnClick() {

				@SuppressWarnings("deprecation")
				public void childPopupOnClick(View v, int which, final int item) {

					switch (which) {
					case DragGridView.HANDLE_ROOM_CTR:
						itemRoom = (Room) lists.get(Configure.curentPage).get(
								item);
						switch (v.getId()) {
						case Configure.EDIT:// 编辑
							toDoEdit();
							break;
						case Configure.DELETE:
							if (curentPage == Configure.curentPage) {
								isFirstIn = false;
							}
							String delFileName = "room"
									+ itemRoom.getRoomCtrlPosition();
							File delFile = new File(CommentsUtil.FILE_PATH
									+ delFileName);
							if (delFile.exists()) {
								delFile.delete();
							}
							Room conRoom = new Room(itemRoom.getRoomCtrlId(),
									itemRoom.getRoomCtrlPosition());
							roomDao.insertIntoRoomContainer(itemRoom);
							roomDao.deleteFromRoomCtrl(itemRoom
									.getRoomCtrlPosition());
							roomContainerList.add(conRoom);
							lists.get(Configure.curentPage).remove(item);
							listData.remove(0);
							adapterList.get(Configure.curentPage)
									.notifyDataSetChanged();
							break;
						case Configure.RENAME:
							final EditText text = new EditText(
									RoomControlActivity.this);
							text.setText(itemRoom.getRoomCtrlName());
							alert = createBuilder(R.string.rename_title, text);
							alert.setButton("确定", new OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									String str = text.getText().toString()
											.trim();
									itemRoom.setRoomCtrlName(str);
									if (!"".equals(str)) {
										// 重命名更新数据库
										lists.get(Configure.curentPage).set(
												item, itemRoom);
										roomDao.updateFromRoomCtrl(itemRoom,
												str);
										adapterList.get(Configure.curentPage)
												.notifyDataSetChanged();
										alert.dismiss();
									} else {
										Toast.makeText(
												RoomControlActivity.this,
												"文件名不能为空", 2000).show();
									}
								}

							});
							alert.setButton2("取消", new OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									alert.dismiss();
								}
							});
							alert.show();
							break;
						case Configure.RECOLOR:
							View view = LayoutInflater.from(
									RoomControlActivity.this).inflate(
									R.layout.alert_folder_bg, null);
							alert = createBuilder(R.string.change_color, view);
							alert.setView(view);
							view.findViewById(R.id.color1).setOnClickListener(
									RoomControlActivity.this);
							view.findViewById(R.id.color2).setOnClickListener(
									RoomControlActivity.this);
							view.findViewById(R.id.color3).setOnClickListener(
									RoomControlActivity.this);
							view.findViewById(R.id.color4).setOnClickListener(
									RoomControlActivity.this);
							view.findViewById(R.id.color5).setOnClickListener(
									RoomControlActivity.this);
							view.findViewById(R.id.color6).setOnClickListener(
									RoomControlActivity.this);
							alert.setButton("取消", new OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									alert.dismiss();
								}
							});
							alert.show();
							break;

						default:
							break;
						}
						break;
					}
				}

			});

			// 跨屏交换位置
			gridView.setOnItemChangeListener(new DragGridView.G_ItemChangeListener() {
				@Override
				public void change(int from, int to, int count) {
					if (to < lists.get(Configure.curentPage).size()) {
						Room room = (Room) lists.get(
								Configure.curentPage - count).get(from);

						lists.get(Configure.curentPage - count).add(
								from,
								(String) lists.get(Configure.curentPage)
										.get(to));
						lists.get(Configure.curentPage - count)
								.remove(from + 1);
						lists.get(Configure.curentPage).add(to, room);
						lists.get(Configure.curentPage).remove(to + 1);
						updateTable((Configure.curentPage - count) * 8 + from,
								(Configure.curentPage) * 8 + to);
						((RoomAdapter) ((gridViews.get(Configure.curentPage))
								.getAdapter())).notifyDataSetChanged();
						// dataAdapter.notifyDataSetChanged();
					}
					((RoomAdapter) ((gridViews
							.get(Configure.curentPage - count)).getAdapter()))
							.notifyDataSetChanged();
				}

			});
		}
		// 交换位置更新
		gridView.tellChildUpdate(new SwitchPositionListener() {

			public void post(int dragPosition, int dropPosition) {
				switch (gridView.whichPageMove) {
				case DragGridView.HANDLE_ROOM_CTR:
					Log.e("Roompost", "--->" + dragPosition + "---->"
							+ dropPosition);
					updateTable(dragPosition, dropPosition);
					break;
				}
			}

		});
		MainGroup.getWhichAdd(new SwitchAdd() {

			@Override
			public void addChoice(int flag) {
				if (isInitMain()) {
					switch (flag) {
					case 2:
						Room conRoom = roomContainerList.get(0);
						roomContainerList.remove(0);
						Room r = new Room(conRoom.getRoomCtrlId(), conRoom
								.getRoomCtrlPosition(), "ROOM"
								+ conRoom.getRoomCtrlPosition(),
								"room_control1");
						roomDao.deleteFromRoomContainer(r.getRoomCtrlPosition());
						roomDao.insertIntoRoomCtrl(r);
						addItem(curentPage, r);
						listData.add(r);
						break;
					}
				}
			}
		});
		gridViews.add(gridView);
		linear.addView(gridViews.get(countPages), linearParam);
		return linear;
	}

	/**
	 * 编辑功能方法
	 */
	private void toDoEdit() {
		String fileName = "room" + itemRoom.getRoomCtrlPosition();
		File file = new File(CommentsUtil.FILE_PATH + fileName);
		if (!file.exists()) {
			viewLists = new ArrayList<Customview>();
			file.getParentFile().mkdirs();
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			CommentsUtil.writeCustomview(file, viewLists);
		}

		Intent intent = new Intent(RoomControlActivity.this, RoomActivity.class);
		intent.putExtra("room", itemRoom);
		if (fromActivity == 2) {
			intent.putExtra("fromActivity", 2);
			startActivity(intent);
			RoomControlActivity.this.finish();
			return;
		}
		startActivity(intent);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.color1:
			updateRoomColor(roomDao, "room_control1", itemRoom);
			break;
		case R.id.color2:
			updateRoomColor(roomDao, "room_control2", itemRoom);
			break;
		case R.id.color3:
			updateRoomColor(roomDao, "room_control3", itemRoom);
			break;
		case R.id.color4:
			updateRoomColor(roomDao, "room_control4", itemRoom);
			break;
		case R.id.color5:
			updateRoomColor(roomDao, "room_control5", itemRoom);
			break;
		case R.id.color6:
			updateRoomColor(roomDao, "room_control6", itemRoom);
			break;
		}
		adapterList.get(Configure.curentPage).notifyDataSetChanged();
		alert.dismiss();
	}

	/**
	 * 更新数据库颜色
	 * 
	 * @param roomDao
	 * @param str
	 * @param room
	 */
	private void updateRoomColor(RoomDao roomDao, String str, Room room) {
		roomDao.updateRoomColor(room, str);
		room.setRoomCtrlBg(str);
	}

	/**
	 * 根据位置更新数据库
	 * 
	 * @param from
	 *            开始位置
	 * @param to
	 *            结束位置
	 */
	private void updateTable(int from, int to) {
		Room room1 = (Room) lists.get(Configure.curentPage).get(from);
		Room room2 = (Room) lists.get(Configure.curentPage).get(to);
		roomDao.updateRoom(room1, room2);
		roomDao.updateRoom(room2, room1);
	}
}
