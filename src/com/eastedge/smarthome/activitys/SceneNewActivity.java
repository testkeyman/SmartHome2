package com.eastedge.smarthome.activitys;

import com.eastedge.smarthome.R;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;


/** 新建智能联动 */
public class SceneNewActivity extends BaseActivity implements OnClickListener {
	private Button mButtonSave,mButtonAuto,mButtonBack,mButtonAdd;
	@Override
	protected void setView() {
		setContentView(R.layout.activity_scene_new);
		mButtonSave=(Button) findViewById(R.id.main_btn_save);
		mButtonAuto=(Button) findViewById(R.id.scene_btn_roomctrl);
		mButtonBack=(Button) findViewById(R.id.scene_btn_back);
		mButtonAdd=(Button) findViewById(R.id.scene_btn_add);
		mButtonSave.setOnClickListener(this);
		mButtonAuto.setOnClickListener(this);
		mButtonBack.setOnClickListener(this);
		mButtonAdd.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.scene_btn_back:
			finish();
			break;
		}
	}

	@Override
	protected void initData() {

	}

	@Override
	public LinearLayout addGridView(int countPages) {
		return null;
	}

}
