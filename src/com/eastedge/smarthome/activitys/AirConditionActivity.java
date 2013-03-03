package com.eastedge.smarthome.activitys;

import com.eastedge.smarthome.R;

import android.view.View;
import android.widget.LinearLayout;

/**
 * 空调界面
 */
public class AirConditionActivity extends BaseActivity {
	
	@Override
	protected void setView() {
		setContentView(R.layout.activity_air);

	}

	

	@Override
	protected void initData() {

	}

	@Override
	public LinearLayout addGridView(int countPages) {
		return null;
	}

}
