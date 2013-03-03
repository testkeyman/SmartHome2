package com.eastedge.smarthome.activitys;

import java.io.File;
import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.eastedge.smarthome.R;
import com.eastedge.smarthome.adapters.ElecNameAdapter;
import com.eastedge.smarthome.adapters.RoomSecondAdapter;
import com.eastedge.smarthome.dao.ElecPidDao;
import com.eastedge.smarthome.dao.ExistsElecDao;
import com.eastedge.smarthome.domain.ControlLightBean;
import com.eastedge.smarthome.domain.Customview;
import com.eastedge.smarthome.domain.Lightbean;
import com.eastedge.smarthome.domain.NewElecBean;
import com.eastedge.smarthome.domain.Room;
import com.eastedge.smarthome.domain.WarningDeviceBean;
import com.eastedge.smarthome.dragview.Configure;
import com.eastedge.smarthome.dragview.DragGridView;
import com.eastedge.smarthome.dragview.DragGridView.ChildPopupOnClick;
import com.eastedge.smarthome.util.CommentsUtil;
import com.eastedge.smarthome.util.DensityUtil;

public class RoomActivity extends BaseActivity implements OnClickListener {
	private LinearLayout container;
	protected Button main_btn_lifecontent, main_btn_roomctrl,
			main_btn_eleclass, main_btn_add;
	protected TextView mTextPage, mTextMessage, mTextTime;
	private AlertDialog dialog;
	private Intent intent = null;
	private static final int REQUESTCODE = 1;
	/** 更新时间 */
	protected static final int UPDATETIME = 0;
	/** 选中传送过来的Room */
	private Room itemRoom;
	ArrayList<Customview> viewLists;
	// 长按选中的CustomView
	Customview customview = null;
	ArrayList<Integer> elecPidLists;
	public ElecPidDao elecPidDao;
	private ArrayList<String> elecNameList;
	private ExistsElecDao existsElecName;
	// 电器设备数量
	private int count;
	private File file;
	private String filePath;

	/**
	 * 标志来自那个窗体（0：默认，2：来自生活情景）
	 */
	private int fromActivity;

	@Override
	protected boolean isInitMain() {
		return true;
	}

	@Override
	protected void setView() {
		setContentView(R.layout.room_second_layout);
		intent = getIntent();
		fromActivity = intent.getIntExtra("fromActivity", 0);
		initView();
		setClick();
		elecPidDao = new ElecPidDao(this);
		existsElecName = new ExistsElecDao(this);
		if (elecPidDao.isFirstInsert()) {
			progressDialog = new ProgressDialog(RoomActivity.this);
			progressDialog.setTitle(R.string.progress_title);
			progressDialog.show();
			new Thread() {
				public void run() {
					for (int i = 1; i < 201; i++) {
						elecPidDao.insertElecPid(i);
					}
					existsElecName.insertIntoElecName(1, "三路灯");
					existsElecName.insertIntoElecName(2, "烟感");
					existsElecName.insertIntoElecName(3, "门磁");
					existsElecName.insertIntoElecName(4, "红外探测");
					existsElecName.insertIntoElecName(5, "调光灯");
					existsElecName.insertIntoElecName(6, "电视");
					existsElecName.insertIntoElecName(7, "冰箱");
					existsElecName.insertIntoElecName(8, "空调");
					existsElecName.insertIntoElecName(9, "洗衣机");
					existsElecName.insertIntoElecName(10, "求救器");
					elecNameList = existsElecName.findAllElecName();
					elecPidLists = elecPidDao.findAllElecId();
					mHandler.sendEmptyMessage(0);
				};
			}.start();
		} else {
			elecPidLists = elecPidDao.findAllElecId();
			elecNameList = existsElecName.findAllElecName();
		}

		if (null != intent) {
			itemRoom = (Room) intent.getSerializableExtra("room");
		}
		String fileName = "room" + itemRoom.getRoomCtrlPosition();
		filePath = CommentsUtil.FILE_PATH + fileName;
		file = new File(filePath);
		MyHandler.sendEmptyMessageDelayed(UPDATETIME, 1000);
		main_btn_lifecontent.setText(itemRoom.getRoomCtrlName());
		viewLists = CommentsUtil.readCustomview(file);
	}

	@Override
	protected void initData() {
		if (null != viewLists) {
			count = viewLists.size();
			for (int i = 0; i < count; i++) {
				listData.add(viewLists.get(i));
			}
		}
	}

	@Override
	public LinearLayout addGridView(int countPages) {
		linear = new LinearLayout(RoomActivity.this);
		gridView = new DragGridView(RoomActivity.this);
		gridView.start = false;
		// 房间控制窗口标识为3
		gridView.whichPageMove = DragGridView.HANDLE_SECOND_ROOM;
		// 获取当前页面
		curentPage = countPages;
		gridView.setNumColumns(NUM_COlUMNS);
		gridView.setHorizontalSpacing(DensityUtil.dip2px(this, 40));
		gridView.setVerticalSpacing(DensityUtil.dip2px(this, 8));
		dataAdapter = new RoomSecondAdapter(this, lists.get(curentPage));
		gridView.setAdapter(dataAdapter);
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				customview = (Customview) lists.get(Configure.curentPage).get(
						arg2);
				toDoEdit();
			}
		});
		/**
		 * 如果来自默认，添加长按，拖拽监听器
		 */
		if (fromActivity != 2) {
			// 监听弹框选项操作
			gridView.setChildPopupOnClick(new ChildPopupOnClick() {

				@SuppressWarnings("deprecation")
				public void childPopupOnClick(View v, int which, final int item) {

					switch (which) {
					case DragGridView.HANDLE_SECOND_ROOM:
						customview = (Customview) lists.get(
								Configure.curentPage).get(item);
						switch (v.getId()) {
						case Configure.EDIT:// 编辑
							toDoEdit();
							break;
						case Configure.DELETE:
							lists.get(Configure.curentPage).remove(item);
							listData.remove(0);
							viewLists.remove(customview);
							elecPidLists.add(customview.getDevice_id());
							elecPidDao.insertElecPid(customview.getDevice_id());
							adapterList.get(Configure.curentPage)
									.notifyDataSetChanged();
							// android.database.sqlite.SQLiteConstraintException:
							// error code 19: constraint failed

							break;
						case Configure.RENAME:
							final EditText text = new EditText(
									RoomActivity.this);
							text.setText(customview.getDeviceName());
							alert = createBuilder(R.string.rename_title, text);
							alert.setButton("确定",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											String str = text.getText()
													.toString().trim();
											itemRoom.setRoomCtrlName(str);
											if (!"".equals(str)) {
												customview.setDeviceName(str);
												lists.get(Configure.curentPage)
														.set(item, customview);
												adapterList.get(
														Configure.curentPage)
														.notifyDataSetChanged();
											}
										}
									});
							alert.show();
							break;
						case Configure.RECOLOR:
							View view = LayoutInflater.from(RoomActivity.this)
									.inflate(R.layout.alert_folder_bg, null);
							alert = createBuilder(R.string.change_color, view);
							alert.setView(view);
							alert.setButton("取消",
									new DialogInterface.OnClickListener() {
										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											alert.dismiss();
										}
									});
							view.findViewById(R.id.color1).setOnClickListener(
									RoomActivity.this);
							view.findViewById(R.id.color2).setOnClickListener(
									RoomActivity.this);
							view.findViewById(R.id.color3).setOnClickListener(
									RoomActivity.this);
							view.findViewById(R.id.color4).setOnClickListener(
									RoomActivity.this);
							view.findViewById(R.id.color5).setOnClickListener(
									RoomActivity.this);
							view.findViewById(R.id.color6).setOnClickListener(
									RoomActivity.this);
							alert.show();
							break;

						default:
							break;
						}
						// gridView.hideView();
						break;
					}
				}

			});
		}
		gridView.setSelector(R.drawable.folder_select_bg);
		gridViews.add(gridView);

		linear.addView(gridViews.get(countPages), linearParam);
		return linear;
	}

	/**
	 * 编辑方法 fromActivity==2(来自生活情景)
	 * 
	 * fromActivity==其他（来自默认）
	 */
	private void toDoEdit() {
		if (fromActivity == 2) {
			int device_id = customview.getDevice_id();
			SceneUsedActivity.MyHandler.sendEmptyMessage(device_id);
			RoomActivity.this.finish();
		} else {
			Intent intent = new Intent();
			intent.setClass(RoomActivity.this, ElectricControlActivity.class);
			intent.putExtra("customview", customview);
			startActivityForResult(intent, REQUESTCODE);
		}

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (fromActivity == 2) {
			SceneUsedActivity.MyHandler.sendEmptyMessage(0);
		}

		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 显示时间
	 */
	Handler MyHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case UPDATETIME:
				mTextTime.setText(CommentsUtil.getTime());
				MyHandler.sendEmptyMessageDelayed(UPDATETIME, 1000);
				break;

			default:
				break;
			}
		}
	};

	/**
	 * 初始化布局
	 */
	private void initView() {
		main_btn_lifecontent = (Button) this
				.findViewById(R.id.main_btn_lifecontent);
		main_btn_roomctrl = (Button) this.findViewById(R.id.main_btn_roomctrl);
		main_btn_eleclass = (Button) this.findViewById(R.id.main_btn_eleclass);
		main_btn_add = (Button) this.findViewById(R.id.main_btn_add);
		container = (LinearLayout) this.findViewById(R.id.container);
		mTextTime = (TextView) this.findViewById(R.id.main_tv_time);
		mTextTime.setText(CommentsUtil.getTime());
		mTextMessage = (TextView) this.findViewById(R.id.main_tv_message);
		main_btn_eleclass.setText(R.string.main_btn_back);
		main_btn_roomctrl.setText("");

	}

	/**
	 * 单击事件
	 */
	private void setClick() {
		main_btn_lifecontent.setOnClickListener(this);
		main_btn_eleclass.setOnClickListener(this);
		main_btn_add.setOnClickListener(this);
		mTextMessage.setOnClickListener(this);
		if (fromActivity == 2) {
			// main_btn_roomctrl.setBackgroundResource(R.drawable.left_up_click);
			main_btn_roomctrl.setClickable(false);
			main_btn_lifecontent.setClickable(false);
			main_btn_add.setClickable(false);
			main_btn_add.setBackgroundResource(R.drawable.normal);
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.main_btn_eleclass:
			finish();
			break;
		case R.id.main_btn_add:
			View view = LayoutInflater.from(RoomActivity.this).inflate(
					R.layout.elec_name, null);
			final PopupWindow popup = new PopupWindow(view, getWindowManager()
					.getDefaultDisplay().getWidth() / 3 * 1,
					container.getHeight(), true);
			ListView listView = (ListView) view.findViewById(R.id.listView1);
			final ElecNameAdapter nameNdapter = new ElecNameAdapter(this,
					elecNameList);
			listView.setAdapter(nameNdapter);
			popup.setBackgroundDrawable(new ColorDrawable());
			popup.setOutsideTouchable(true);
			popup.showAsDropDown(main_btn_roomctrl);
			// popup.showAtLocation(container, Gravity.BOTTOM,
			// main_btn_add.getLeft(),mTextTime.getBottom()*2);
			listView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {

					switch (position) {
					case 0:
						Lightbean lightbean = new Lightbean();
						lightbean.setBgId(R.drawable.room_control1_bg);
						lightbean.setDevice_id(elecPidLists.get(0));
						ctrlElecPidDao();
						lightbean.setDeviceName("三路灯");
						lightbean.setDevice_location(filePath);
						lightbean.setDevice_state(false);
						lightbean.setIsLearn(true);
						popup.dismiss();
						addItem(curentPage, lightbean);
						elecPidLists.remove(0);
						listData.add(lightbean);
						viewLists.add(lightbean);
						break;
					case 1:
						WarningDeviceBean warningDeviceBean = new WarningDeviceBean();
						warningDeviceBean.setDevice_id(elecPidLists.get(0));
						ctrlElecPidDao();
						warningDeviceBean.setBgId(R.drawable.room_control2_bg);
						warningDeviceBean.setDeviceName("烟感");
						warningDeviceBean.setIsLearn(false);
						addItem(curentPage, warningDeviceBean);
						warningDeviceBean.setDevice_location(filePath);
						popup.dismiss();
						elecPidLists.remove(0);
						listData.add(warningDeviceBean);
						viewLists.add(warningDeviceBean);
						break;
					case 2:
						WarningDeviceBean doorWarn = new WarningDeviceBean();
						doorWarn.setDevice_id(elecPidLists.get(0));
						ctrlElecPidDao();
						doorWarn.setDevice_location(filePath);
						doorWarn.setBgId(R.drawable.room_control3_bg);
						doorWarn.setDeviceName("门磁");
						doorWarn.setIsLearn(false);
						addItem(curentPage, doorWarn);
						popup.dismiss();
						elecPidLists.remove(0);
						listData.add(doorWarn);
						viewLists.add(doorWarn);
						break;
					case 3:
						WarningDeviceBean redDetectedWarn = new WarningDeviceBean();
						redDetectedWarn.setDevice_id(elecPidLists.get(0));
						ctrlElecPidDao();
						redDetectedWarn.setDevice_location(filePath);
						redDetectedWarn.setBgId(R.drawable.room_control4_bg);
						redDetectedWarn.setDeviceName("红外探测");
						redDetectedWarn.setIsLearn(false);
						addItem(curentPage, redDetectedWarn);
						popup.dismiss();
						elecPidLists.remove(0);
						listData.add(redDetectedWarn);
						viewLists.add(redDetectedWarn);
						break;
					case 4:
						ControlLightBean controlLightBean = new ControlLightBean();
						controlLightBean.setDevice_id(elecPidLists.get(0));
						ctrlElecPidDao();
						controlLightBean.setDevice_location(filePath);
						controlLightBean.setBgId(R.drawable.room_control5_bg);
						controlLightBean.setDeviceName("调光灯");
						controlLightBean.setIsLearn(false);
						addItem(curentPage, controlLightBean);
						popup.dismiss();
						elecPidLists.remove(0);
						listData.add(controlLightBean);
						viewLists.add(controlLightBean);
						break;
					case 5:
						break;
					case 6:
						break;
					case 7:
						break;
					case 8:
						break;
					case 9:
						break;
					case 10:
						break;
					default:
						NewElecBean newElec = new NewElecBean();
						newElec.setDevice_id(elecPidLists.get(0));
						ctrlElecPidDao();
						newElec.setBgId(R.drawable.room_control5_bg);
						String elecName = elecNameList.get(position);
						newElec.setDevice_location(filePath);
						newElec.setDeviceName(elecName);
						newElec.setIsLearn(false);
						addItem(curentPage, newElec);
						popup.dismiss();
						elecPidLists.remove(0);
						listData.add(newElec);
						viewLists.add(newElec);
						break;
					}
				}
			});
			// 单击添加新设备按钮
			Button addNewButton = (Button) view.findViewById(R.id.button1);
			addNewButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					final EditText text = new EditText(RoomActivity.this);
					text.setHint("输入电器名称");
					alert = createBuilder(R.string.add_ele_name, text);
					alert.setButton("确定",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									String eleName = text.getText().toString();
									if (elecNameList.contains(eleName)) {
										Toast.makeText(context,
												R.string.add_ele_name_toast,
												2000).show();
									} else {
										existsElecName.insertIntoElecName(
												elecNameList.size() + 1,
												eleName);
										elecNameList.add(eleName);
										nameNdapter.notifyDataSetChanged();
									}
								}
							});
					alert.setButton2("取消",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									alert.dismiss();
								}
							});
					alert.show();
				}

			});

			break;
		case R.id.color1:
			updateElectricColor(R.drawable.room_control1_bg);
			break;
		case R.id.color2:
			updateElectricColor(R.drawable.room_control2_bg);
			break;
		case R.id.color3:
			updateElectricColor(R.drawable.room_control3_bg);
			break;
		case R.id.color4:
			updateElectricColor(R.drawable.room_control4_bg);
			break;
		case R.id.color5:
			updateElectricColor(R.drawable.room_control5_bg);
			break;
		case R.id.color6:
			updateElectricColor(R.drawable.room_control6_bg);
			break;

		}
	}

	/**
	 * 更新数据库颜色
	 * 
	 * @param roomDao
	 * @param str
	 * @param room
	 */
	private void updateElectricColor(int id) {
		customview.setBgId(id);
		alert.dismiss();
		adapterList.get(Configure.curentPage).notifyDataSetChanged();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (REQUESTCODE == requestCode) {
			if (resultCode == ElectricControlActivity.RESULTCODE) {
				Log.e("customeIsLearn-----", "----->" + customview.getIsLearn());
			}

		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * 从pid数据库中删除对应id
	 */
	private void ctrlElecPidDao() {
		elecPidDao.deleteElecPid(elecPidLists.get(0));
		elecPidLists.remove(0);
	}

	@Override
	protected void onStop() {
		CommentsUtil.writeCustomview(file, viewLists);
		super.onStop();
	}

}
