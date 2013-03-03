package com.eastedge.smarthome.activitys;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eastedge.smarthome.R;
import com.eastedge.smarthome.adapters.RoomAdapter;
import com.eastedge.smarthome.dao.ElecPidDao;
import com.eastedge.smarthome.dao.RoomDao;
import com.eastedge.smarthome.domain.Customview;
import com.eastedge.smarthome.domain.Room;
import com.eastedge.smarthome.dragview.Configure;
import com.eastedge.smarthome.dragview.DragGridView;
import com.eastedge.smarthome.dragview.DragGridView.ChildPopupOnClick;
import com.eastedge.smarthome.util.CommentsUtil;
import com.eastedge.smarthome.util.DensityUtil;

public class ElectricSecondActivity extends BaseActivity implements android.view.View.OnClickListener{
	private String[]fileNames=null;
	private LinearLayout container;
	private File file;
	private File childFile;
	private AlertDialog dialog;
	private ArrayList<Customview> listViews=null;
	private File[]files=null;
	private String deviceName;
	protected Button main_btn_lifecontent, main_btn_roomctrl,
	main_btn_eleclass, main_btn_add;
	protected TextView mTextPage, mTextMessage, mTextTime;
	RoomDao roomDao;
	Room itemRoom;
	ArrayList<Room> roomContainerList=null;
	/** 更新时间 */
	protected static final int UPDATETIME = 0;
	private HashMap<Integer,Room> hashRoom=new HashMap<Integer,Room>();
	ArrayList <Integer>elecPidLists; 
	public ElecPidDao elecPidDao;
	@Override
	protected void setView() {
		setContentView(R.layout.room_second_layout);
		initView();
		setClick();
		elecPidDao=new ElecPidDao(this);
		elecPidDao.isFirstInsert();
		elecPidLists=elecPidDao.findAllElecId();
		roomDao=new RoomDao(this);
		roomContainerList=roomDao.findAllByRoomContainer();
		file=new File(CommentsUtil.FILE_PATH);
		deviceName=getIntent().getStringExtra("device_name");
		if(file.exists()&&file.isDirectory()){
			fileNames=file.list();
			files=file.listFiles();
		}
		MyHandler.sendEmptyMessageDelayed(UPDATETIME, 1000);
	}
	@Override
	protected boolean isInitMain() {
		return true;
	}
	@Override
	protected void initData() {
		int filesCount=files.length;
		int viewsCount=0;
		for (int i = 0; i < filesCount; i++) {
			childFile=files[i];
			listViews=CommentsUtil.readCustomview(childFile);
			viewsCount=listViews.size();
			for(int j=0;j<viewsCount;j++){
				Customview customView=listViews.get(j);
				Log.e("ElecSecond","device_name---->"+customView.getDeviceName());
				int pos=Integer.parseInt(childFile.getName().substring(4));
				Room room=roomDao.findByRoomId(pos);
//				hashRoom.put(pos, room);
				if((deviceName.equals(customView.getDeviceName()))&&(!hashRoom.containsKey(pos))){
					if(null!=room){
						listData.add(room);
						hashRoom.put(pos,room);
					}
				}
			}
		}
		main_btn_lifecontent.setText(deviceName);
	}
	/**
	 * 初始化布局
	 */
	private void initView() {
		main_btn_lifecontent = (Button) this
				.findViewById(R.id.main_btn_lifecontent);
		main_btn_roomctrl = (Button) this.findViewById(R.id.main_btn_roomctrl);
		main_btn_roomctrl.setText("");
		main_btn_eleclass = (Button) this.findViewById(R.id.main_btn_eleclass);
		main_btn_add = (Button) this.findViewById(R.id.main_btn_add);
		main_btn_add.setText("");
		container = (LinearLayout) this.findViewById(R.id.container);
		mTextTime = (TextView) this.findViewById(R.id.main_tv_time);
		mTextTime.setText(CommentsUtil.getTime());
		mTextMessage = (TextView) this.findViewById(R.id.main_tv_message);
		main_btn_eleclass.setText(R.string.main_btn_back);
		main_btn_roomctrl.setClickable(false);
		
		
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
	 * 单击事件
	 */
	private void setClick() {
		main_btn_lifecontent.setOnClickListener(this);
		main_btn_eleclass.setOnClickListener(this);
//		main_btn_add.setOnClickListener(this);
		mTextMessage.setOnClickListener(this);
	}

	@Override
	public LinearLayout addGridView(int countPages) {
		linear = new LinearLayout(ElectricSecondActivity.this);
		gridView = new DragGridView(ElectricSecondActivity.this);
		gridView.start=false;
		//房间控制窗口标识为3
		gridView.whichPageMove=DragGridView.HANDLE_SECOND_ELECTRIC;
		// 获取当前页面
		curentPage = countPages;
		//监听弹框选项操作
		gridView.setNumColumns(NUM_COlUMNS);
		gridView.setHorizontalSpacing(DensityUtil.dip2px(this, 40));
		gridView.setVerticalSpacing(DensityUtil.dip2px(this, 8));
		dataAdapter = new RoomAdapter(this, lists.get(curentPage));
		gridView.setAdapter(dataAdapter);
		gridView.setChildPopupOnClick(new ChildPopupOnClick() {
			
			@SuppressWarnings("deprecation")
			public void childPopupOnClick(View v,int which,final int item) {
				
				switch(which){
				case DragGridView.HANDLE_SECOND_ELECTRIC:
					Log.e("RoomGrid","------>1111111");
					itemRoom =(Room) lists.get(Configure.curentPage).get(item);
					File file=new File (CommentsUtil.FILE_PATH+"room"+itemRoom.getRoomCtrlPosition());
					switch(v.getId()){
					case Configure.EDIT:
						Intent intent =new Intent (ElectricSecondActivity.this,ElectricRoomShow.class);
						intent.putExtra("file_path", file.getAbsolutePath());
						intent.putExtra("device_name", deviceName);
						intent.putExtra("room_name", itemRoom.getRoomCtrlName());
						startActivity(intent);
						break;
					case Configure.DELETE:
						if(curentPage==Configure.curentPage){
							isFirstIn=false;
						}
						ArrayList<Customview> viewLists=CommentsUtil.readCustomview(file);
						int count=viewLists.size();
						Log.e("----","count--->"+count);
						for (int i = 0; i <count; i++) {
							Customview customView=viewLists.get(i);
							if(deviceName.equals(customView.getDeviceName())){
								elecPidLists.add(customView.getDevice_id());
								elecPidDao.insertElecPid(customView.getDevice_id());
								viewLists.remove(customView);
								i--;
								count--;
								Log.e("----","i----"+i);
							}
						}
						CommentsUtil.writeCustomview(file, viewLists);
						lists.get(Configure.curentPage).remove(item);
						listData.remove(0);
						adapterList.get(Configure.curentPage).notifyDataSetChanged();
						break;
					case Configure.RENAME:
						final EditText text=new EditText(ElectricSecondActivity.this);
						text.setText(itemRoom.getRoomCtrlName());
						alert=createBuilder(R.string.rename_title,text);
						alert.setButton("确定", new DialogInterface.OnClickListener() {
						@Override
							public void onClick(DialogInterface dialog, int which) {
								String str=text.getText().toString().trim();
								itemRoom.setRoomCtrlName(str);
								if(!"".equals(str)){
									//重命名更新数据库
									lists.get(Configure.curentPage).set(item, itemRoom);
									roomDao.updateFromRoomCtrl(itemRoom, str);
									adapterList.get(Configure.curentPage).notifyDataSetChanged();
									alert.dismiss();
								}else{
									Toast.makeText(ElectricSecondActivity.this, "文件名不能为空", 2000).show();
								}
							}
							
						});
						alert.setButton2("取消", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								alert.dismiss();
							}
						});
						alert.show();
						break;
					case Configure.RECOLOR:
						View view= LayoutInflater.from(ElectricSecondActivity.this).inflate(R.layout.alert_folder_bg, null);
						alert=createBuilder(R.string.change_color, view);
						alert.setView(view);
						view.findViewById(R.id.color1).setOnClickListener(ElectricSecondActivity.this);
						view.findViewById(R.id.color2).setOnClickListener(ElectricSecondActivity.this);
						view.findViewById(R.id.color3).setOnClickListener(ElectricSecondActivity.this);
						view.findViewById(R.id.color4).setOnClickListener(ElectricSecondActivity.this);
						view.findViewById(R.id.color5).setOnClickListener(ElectricSecondActivity.this);
						view.findViewById(R.id.color6).setOnClickListener(ElectricSecondActivity.this);
						alert.setButton("取消", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
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
		gridView.setSelector(R.drawable.folder_select_bg);
		gridViews.add(gridView);
		linear.addView(gridViews.get(countPages), linearParam);
		return linear;
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.main_btn_lifecontent:
			break;
		case R.id.main_btn_eleclass:
			finish();
			break;
		case R.id.main_btn_add:
			
			break;
		case R.id.color1:
			updateRoomColor(roomDao,"room_control1",itemRoom);
			break;
		case R.id.color2:
			updateRoomColor(roomDao,"room_control2",itemRoom);
			break;
		case R.id.color3:
			updateRoomColor(roomDao,"room_control3",itemRoom);
			break;
		case R.id.color4:
			updateRoomColor(roomDao,"room_control4",itemRoom);
			break;
		case R.id.color5:
			updateRoomColor(roomDao,"room_control5",itemRoom);
			break;
		case R.id.color6:
			updateRoomColor(roomDao,"room_control6",itemRoom);
			break;
		}
	}
	/**
	 * 更新数据库颜色
	 * @param roomDao
	 * @param str
	 * @param room
	 */
	private  void updateRoomColor(RoomDao roomDao,String str,Room room){
		roomDao.updateRoomColor(room, str);
		room.setRoomCtrlBg(str);
		alert.dismiss();
		adapterList.get(Configure.curentPage).notifyDataSetChanged();
	}

}
