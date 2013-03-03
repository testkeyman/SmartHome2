package com.eastedge.smarthome.activitys;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.eastedge.smarthome.R;
import com.eastedge.smarthome.wheelview.WheelDateTimePicker;

/** 消息界面的查询 */
public class DialogSearch extends Activity implements OnClickListener {
	private static final String TAG = "SearchDateActivity";

	private String startDate, endDate;
	private View timePickerStart, timePickerEnd;
	private WheelDateTimePicker wheelStartDate, wheelEndDate;

	private Button mButtonConfirm, mButtonCancel;

	// 滚轮控件 年，月，日
	static int[] layoutStart = { R.id.start_year, R.id.start_month,
			R.id.start_day };
	static int[] layoutEnd = { R.id.end_year, R.id.end_month, R.id.end_day };

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		intiView();
		setData();
	}

	protected void setData() {
		// 实例自定义的滚轮样式控件
		wheelStartDate = new WheelDateTimePicker(timePickerStart);
		wheelEndDate = new WheelDateTimePicker(timePickerEnd);

		// 获取设置日期的控件
		wheelStartDate.initDatePicker(layoutStart[0], layoutStart[1],
				layoutStart[2]);
		wheelEndDate.initDatePicker(layoutEnd[0], layoutEnd[1], layoutEnd[2]);
	}

	/**
	 * 查询界面
	 */
	protected void intiView() {

		setContentView(R.layout.message_dialog_search);

		// timePicker
		timePickerStart = this.findViewById(R.id.time_pick_start);
		timePickerEnd = this.findViewById(R.id.time_pick_end);

		// button
		mButtonConfirm = (Button) this.findViewById(R.id.search_btn_confirm);
		mButtonCancel = (Button) this.findViewById(R.id.search_btn_cancel);
		mButtonConfirm.setOnClickListener(this);
		mButtonCancel.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.search_btn_confirm:
			getDate();
			Log.i(TAG, "START --->" + startDate);
			Log.i(TAG, "END --->" + endDate);
			Toast.makeText(getApplicationContext(),
					"startDate-->" + startDate + "------endDate" + endDate, 0)
					.show();
			this.finish();
			break;
		case R.id.search_btn_cancel:
			this.finish();
			break;
		}
	}

	private void getDate() {
		startDate = wheelStartDate.getDate(layoutStart[0], layoutStart[1],
				layoutStart[2]);

		endDate = wheelEndDate
				.getDate(layoutEnd[0], layoutEnd[1], layoutEnd[2]);
	}

}