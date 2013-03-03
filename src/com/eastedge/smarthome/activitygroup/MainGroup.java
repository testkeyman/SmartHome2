package com.eastedge.smarthome.activitygroup;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.eastedge.smarthome.R;
import com.eastedge.smarthome.activitys.AddNewDeviceActivity;
import com.eastedge.smarthome.activitys.BaseApplication;
import com.eastedge.smarthome.activitys.ElectricalActivity;
import com.eastedge.smarthome.activitys.LifeSceneActivity;
import com.eastedge.smarthome.activitys.MessagePromptActivity;
import com.eastedge.smarthome.activitys.RoomControlActivity;
import com.eastedge.smarthome.activitys.SceneUsedActivity;
import com.eastedge.smarthome.service.BackgroundService;
import com.eastedge.smarthome.util.CommentsUtil;
import com.eastedge.smarthome.util.UseFullMedth;

import android.annotation.SuppressLint;
import android.app.ActivityGroup;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * 项目名称：SmartHome 类名称：MainGroup.java 类描述：主界面 创建人： liujinlong
 * 创建时间：2012-12-25上午11:24:15 修改备注：
 * 
 * @version 1.0
 */
@SuppressWarnings("deprecation")
@SuppressLint("NewApi")
public class MainGroup extends ActivityGroup implements OnClickListener {
	private LinearLayout container;
	// 主界面的按钮，以及文本
	protected Button main_btn_lifecontent, main_btn_roomctrl,
			main_btn_eleclass, main_btn_add;
	protected TextView mTextPage, mTextMessage, mTextTime;
	/** 更新时间 */
	protected static final int UPDATETIME = 0;
	private static SwitchAdd switchAdd;
	/**
	 * 标志来自那个窗体（1：默认应用，2：来自生活情景，房间分类）
	 */
	private int fromActivity;
	private int flag = 1;
	private LocalActivityManager mActivityManager = getLocalActivityManager();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_group);
		fromActivity = getIntent().getIntExtra("fromActivity", 1);
		initView();
		setClick();
		if (!UseFullMedth.CheckDaysServiceIsStart(this,
				"com.eastedge.smarthome.service.BackgroundService")) {
			Intent intent = new Intent();
			intent.setClass(this, BackgroundService.class);
			startService(intent);
		} else {
			Log.e("MainGroup", "BackgroundService--->start");
		}
		MyHandler.sendEmptyMessageDelayed(UPDATETIME, 1000);

		switchActivity(fromActivity);
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
		if (fromActivity == 2) {// 来自生活情景房间分类
			main_btn_lifecontent.setText("");
			main_btn_lifecontent.setClickable(false);
			main_btn_roomctrl.setText("");
			main_btn_roomctrl.setClickable(false);
			main_btn_eleclass.setText("返回");
			main_btn_add.setBackgroundResource(R.drawable.normal);
			main_btn_add.setClickable(false);
			SceneUsedActivity.MyHandler.sendEmptyMessage(0);
		}
	}

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
		main_btn_lifecontent.setBackgroundResource(R.drawable.left_up_click);
		mTextMessage = (TextView) this.findViewById(R.id.main_tv_message);

	}

	/**
	 * 切换activity
	 * 
	 * @param flag
	 */
	private void switchActivity(int flag) {
		// TODO Auto-generated method stub
		Intent intent = null;
		if (flag == 1) {
			intent = new Intent(MainGroup.this, LifeSceneActivity.class);
		} else if (flag == 2) {
			intent = new Intent(MainGroup.this, RoomControlActivity.class);
			if (fromActivity == 2) {
				intent.putExtra("fromActivity", 2);
			}
		} else if (flag == 3) {
			intent = new Intent(MainGroup.this, ElectricalActivity.class);
		} else if (flag == 4) {
			intent = new Intent(MainGroup.this, MessagePromptActivity.class);
		}
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		// Activity转为View
		Window subActivity = mActivityManager.startActivity("subActivity",
				intent);
		AnimationSet set = new AnimationSet(true);

		Animation animation = new AlphaAnimation(0.0f, 1.0f);
		animation.setDuration(500);
		set.addAnimation(animation);

		animation = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.5f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f);
		animation.setDuration(500);
		set.addAnimation(animation);

		LayoutAnimationController controller = new LayoutAnimationController(
				set, 0.5f);
		container.setLayoutAnimation(controller);
		container.removeAllViews();
		container.addView(subActivity.getDecorView(), LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.main_btn_lifecontent:// pressed
			main_btn_lifecontent
					.setBackgroundResource(R.drawable.left_up_click);
			main_btn_roomctrl.setBackgroundResource(R.drawable.right_up);
			main_btn_eleclass.setBackgroundResource(R.drawable.left_down);
			main_btn_add.setBackgroundResource(R.drawable.right_down);
			main_btn_add.setTextColor(Color.WHITE);
			main_btn_lifecontent.setClickable(false);
			main_btn_roomctrl.setClickable(true);
			main_btn_eleclass.setClickable(true);
			main_btn_add.setClickable(true);
			flag = 1;
			switchActivity(1);
			break;
		case R.id.main_btn_roomctrl:
			main_btn_lifecontent.setBackgroundResource(R.drawable.left_up);
			main_btn_roomctrl.setBackgroundResource(R.drawable.right_up_click);
			main_btn_eleclass.setBackgroundResource(R.drawable.left_down);
			main_btn_add.setBackgroundResource(R.drawable.right_down);
			main_btn_add.setTextColor(Color.WHITE);
			main_btn_lifecontent.setClickable(true);
			main_btn_roomctrl.setClickable(false);
			main_btn_eleclass.setClickable(true);
			main_btn_add.setClickable(true);
			flag = 2;
			switchActivity(2);
			break;
		case R.id.main_btn_eleclass:
			if (fromActivity == 2) {
				SceneUsedActivity.MyHandler.sendEmptyMessage(0);
				MainGroup.this.finish();
			}
			main_btn_lifecontent.setBackgroundResource(R.drawable.left_up);
			main_btn_roomctrl.setBackgroundResource(R.drawable.right_up);
			main_btn_eleclass.setBackgroundResource(R.drawable.left_down_click);
			main_btn_add.setBackgroundResource(R.drawable.right_down);
			main_btn_lifecontent.setClickable(true);
			main_btn_roomctrl.setClickable(true);
			main_btn_add.setClickable(false);
			main_btn_add.setTextColor(Color.GRAY);
			main_btn_eleclass.setClickable(false);
			flag = 3;
			switchActivity(3);
			break;
		case R.id.main_btn_add:
			MainGroup.switchAdd.addChoice(flag);
			main_btn_add.setBackgroundResource(R.drawable.right_down_bg);
			break;
		// case R.id.main_tv_message:
		// switchActivity(4);
		// break;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {// 返回按鈕
			if (fromActivity == 2) {
				SceneUsedActivity.MyHandler.sendEmptyMessage(0);
				MainGroup.this.finish();
			}

		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * @author baojianming 获取是在哪个页面进行的单击事件
	 * 
	 */
	public static interface SwitchAdd {
		void addChoice(int flag);
	}

	public static void getWhichAdd(SwitchAdd switchAdd) {
		MainGroup.switchAdd = switchAdd;
	}

}
