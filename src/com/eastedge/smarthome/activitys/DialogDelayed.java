package com.eastedge.smarthome.activitys;

import com.eastedge.smarthome.R;
import com.eastedge.smarthome.wheelview.WheelDateTimePicker;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

/** 延时时间的Dialog */
public class DialogDelayed extends Activity implements OnClickListener {
	private static final String TAG = "DelayedActivity";

	private String time;
	private View timePicker;
	private WheelDateTimePicker wheelTimer;
	private Button mButtonConfirm, mButtonCancel;

	// 时钟轮子的控件
	static int[] layoutTimer = { R.id.start_hour, R.id.start_minute,
			R.id.start_second };

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		intiView();
		setData();
	}
	protected boolean isLoadScrollView() {
		return false;

	}

	protected void setData() {
		wheelTimer = new WheelDateTimePicker(timePicker);
		wheelTimer.initTimePicker(layoutTimer[0], layoutTimer[1],
				layoutTimer[2]);

	}

	protected void intiView() {
		setContentView(R.layout.dialog_delay_option);
		timePicker = findViewById(R.id.timer_picker);

		mButtonConfirm = (Button) findViewById(R.id.dialog_btn_confirm);
		mButtonCancel = (Button) findViewById(R.id.dialog_btn_cancel);
		mButtonConfirm.setOnClickListener(this);
		mButtonCancel.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.dialog_btn_confirm:
			getTime();
			Log.e(TAG, "TIME --->" + time);
			break;
		case R.id.dialog_btn_cancel:
			this.finish();
			break;
		}
	}

	private void getTime() {
		time = wheelTimer.getTime(layoutTimer[0], layoutTimer[1],
				layoutTimer[2]);
	}
}