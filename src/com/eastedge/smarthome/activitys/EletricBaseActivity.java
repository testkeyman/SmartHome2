package com.eastedge.smarthome.activitys;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.eastedge.smarthome.R;
import com.eastedge.smarthome.adapters.ElectricControlAdapter;
import com.eastedge.smarthome.util.CommentsUtil;

public abstract class EletricBaseActivity extends Activity implements OnClickListener {
	protected LinearLayout container;
	protected Intent intent;
	protected ListView listView;
	// 主界面的按钮，以及文本
	protected Button main_btn_lifecontent, main_btn_roomctrl,
			main_btn_eleclass, main_btn_add;
	protected TextView mTextPage, mTextMessage, mTextTime;
	protected ElectricControlAdapter electricAdapter;
	protected ArrayList<Object>choiceViewLists=new ArrayList<Object>();
	/** 更新时间 */
	protected static final int UPDATETIME = 0;
	public abstract void initContainer();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.ele_show_main);
		initView();
		setClick();
		initContainer();
		MyHandler.sendEmptyMessageDelayed(UPDATETIME, 1000);
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
		main_btn_roomctrl.setOnClickListener(this);
		main_btn_eleclass.setOnClickListener(this);
		main_btn_add.setOnClickListener(this);
		mTextMessage.setOnClickListener(this);
	}

	/**
	 * 初始化布局
	 */
	private void initView() {
		listView=new ListView(this);
		main_btn_lifecontent = (Button) this
				.findViewById(R.id.main_btn_lifecontent);
		main_btn_roomctrl = (Button) this.findViewById(R.id.main_btn_roomctrl);
		main_btn_eleclass = (Button) this.findViewById(R.id.main_btn_eleclass);
		main_btn_add = (Button) this.findViewById(R.id.main_btn_add);
		container = (LinearLayout) this.findViewById(R.id.container);
		mTextTime = (TextView) this.findViewById(R.id.main_tv_time);
		mTextTime.setText(CommentsUtil.getTime());
		mTextMessage = (TextView) this.findViewById(R.id.main_tv_message);
		
	}

}
