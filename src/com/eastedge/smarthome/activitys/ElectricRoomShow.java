package com.eastedge.smarthome.activitys;

import java.io.File;
import java.util.ArrayList;

import android.content.Intent;
import android.view.View;

import com.eastedge.smarthome.R;
import com.eastedge.smarthome.adapters.ElectricControlAdapter;
import com.eastedge.smarthome.domain.Customview;
import com.eastedge.smarthome.util.CommentsUtil;

public class ElectricRoomShow extends EletricBaseActivity{
	private Intent intent;
	//文件夹路径
	private String filePath;
	//当前电器名称
	private String deviceName;
	private String roomName;
	//所有的customview
	private ArrayList<Customview> allViewLists;
	private File file;
	@Override
	public void initContainer() {
		intent=getIntent();
		if(null!=intent){
			filePath=intent.getStringExtra("file_path");
			deviceName=intent.getStringExtra("device_name");
			roomName=intent.getStringExtra("room_name");
			file=new File(filePath);
			allViewLists=CommentsUtil.readCustomview(file);
			int count=allViewLists.size();
			main_btn_eleclass.setText(R.string.main_btn_back);
			main_btn_lifecontent.setText(roomName);
			main_btn_roomctrl.setText(R.string.main_btn_add_socket);
			main_btn_add.setText("");
			for(int i=0;i<count;i++){
				Customview customview=allViewLists.get(i);
				if(deviceName.equals(customview.getDeviceName())){
					choiceViewLists.add(customview);
				}
			}
			electricAdapter=new ElectricControlAdapter(this, choiceViewLists);
			listView.setAdapter(electricAdapter);
			container.addView(listView);
		}
		
	}
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.main_btn_eleclass:
			CommentsUtil.writeCustomview(file,  allViewLists);
			finish();
			break;
			
		}
	}
	
}
