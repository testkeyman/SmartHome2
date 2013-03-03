package com.eastedge.smarthome.activitys;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.eastedge.smarthome.R;
import com.eastedge.smarthome.activitygroup.MainGroup;
import com.eastedge.smarthome.util.CommentsUtil;

/**
 * 
 * <p>
 * 类描述
 * </p>
 * 情景模式 添加显示窗体
 * 
 * @author jianzhic
 * @date 2013-2-28
 */
public class AddNewDeviceActivity extends ActivityGroup implements
		OnClickListener {
	/** 更新时间 */
	protected static final int UPDATETIME = 0;
	/**
	 * 普通设备、自动情景设备、默认按钮、时间显示(TextView)
	 */
	TextView addnewdevice_tv_autodevice, addnewdevice_tv_commondevice,
			addnewdevice_tv_defaultbtn, main_tv_time;
	Button backbtn, addbtn;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addnewdevice);
		init();
	}

	/**
	 * 显示时间
	 */
	Handler MyHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case UPDATETIME:
				main_tv_time.setText(CommentsUtil.getTime());
				MyHandler.sendEmptyMessageDelayed(UPDATETIME, 1000);
				break;

			default:
				break;
			}
		} 
	};

	private void init() {
		// TODO Auto-generated method stub
		addnewdevice_tv_autodevice = (TextView) findViewById(R.id.addnewdevice_tv_autodevice);
		addnewdevice_tv_autodevice.setOnClickListener(this);
		addnewdevice_tv_commondevice = (TextView) findViewById(R.id.addnewdevice_tv_commondevice);
		addnewdevice_tv_commondevice.setOnClickListener(this);
		addnewdevice_tv_defaultbtn = (TextView) findViewById(R.id.addnewdevice_tv_defaultbtn);
		addnewdevice_tv_defaultbtn.setOnClickListener(this);
		main_tv_time = (TextView) findViewById(R.id.main_tv_time);
		backbtn = (Button) findViewById(R.id.scene_btn_eleclass);
		backbtn.setOnClickListener(this);
		addbtn = (Button) findViewById(R.id.scene_btn_add);

		MyHandler.sendEmptyMessageDelayed(UPDATETIME, 1000);
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.addnewdevice_tv_autodevice:// 自动情景设备

			break;
		case R.id.addnewdevice_tv_commondevice:// 普通设备
			Intent intent = new Intent(AddNewDeviceActivity.this,
					MainGroup.class);
			intent.putExtra("fromActivity", 2);
			startActivity(intent);
			AddNewDeviceActivity.this.finish();
			break;
		case R.id.addnewdevice_tv_defaultbtn:// 默认按鈕

			break;
		case R.id.scene_btn_eleclass:// 返回按鈕
			SceneUsedActivity.MyHandler.sendEmptyMessage(0);
			AddNewDeviceActivity.this.finish();
			break;

		default:
			break;
		}

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {// 返回按鈕
			SceneUsedActivity.MyHandler.sendEmptyMessage(0);
			AddNewDeviceActivity.this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}
}
