package com.eastedge.smarthome.activitys;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.eastedge.smarthome.R;
import com.eastedge.smarthome.adapters.ElectricControlAdapter;
import com.eastedge.smarthome.domain.Customview;

public class ElectricControlActivity extends EletricBaseActivity{
	private Customview mCustomview;
	public static final int RESULTCODE=2;
	@Override
	public void initContainer() {
		intent=getIntent();
		if(null!=intent){
			mCustomview= (Customview) intent.getSerializableExtra("customview");
			Log.e("mCustomview-----","----->"+mCustomview.getIsLearn());
			choiceViewLists.add(mCustomview);
			electricAdapter=new ElectricControlAdapter(this, choiceViewLists);
			listView.setAdapter(electricAdapter);
			container.addView(listView);
			main_btn_eleclass.setText(R.string.main_btn_back);
			main_btn_lifecontent.setText(mCustomview.getDeviceName());
			main_btn_roomctrl.setText(R.string.main_btn_add_socket);
			main_btn_add.setText("");
		}
	}
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.main_btn_eleclass:
			finish();
			break;
			
		}
	}
	@Override
	protected void onStop() {
		Intent intent=new Intent();
		intent.putExtra("customeIsLearn", mCustomview.getIsLearn());
		Log.e("customeIsLearn-----","----->"+mCustomview.getIsLearn());
		setResult(RESULTCODE, intent); 
		super.onStop();
	}
}
