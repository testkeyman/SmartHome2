package com.eastedge.smarthome.domain;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.eastedge.smarthome.R;
import com.eastedge.smarthome.util.Const;

public class Lightbean extends Customview implements OnClickListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 设备id（byte）
//	String device_id;
	// 位置
	// 开关状态
	boolean device_state;
	public boolean isDevice_state() {
		return device_state;
	}

	public void setDevice_state(boolean device_state) {
		this.device_state = device_state;
	}

	@Override
	public View getBeanView(final Context context) {
		View layoutInflater = LayoutInflater.from(context).inflate(
				R.layout.layout_lightitem, null);
		Button light1Open=(Button) layoutInflater.findViewById(R.id.lightitem_radiobtn_open);
		Button light1Close=(Button) layoutInflater.findViewById(R.id.lightitem_radiobtn_close);
		Button light2Open=(Button) layoutInflater.findViewById(R.id.lightitem_radiobtn_open2);
		Button light2Close=(Button) layoutInflater.findViewById(R.id.lightitem_radiobtn_close2);
		Button light3Open=(Button) layoutInflater.findViewById(R.id.lightitem_radiobtn_open3);
		Button light3Close=(Button) layoutInflater.findViewById(R.id.lightitem_radiobtn_close3);
		light1Open.setOnClickListener(this);
		light1Close.setOnClickListener(this);
		light2Open.setOnClickListener(this);
		light2Close.setOnClickListener(this);
		light3Open.setOnClickListener(this);
		light3Close.setOnClickListener(this);
		return layoutInflater;
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.lightitem_radiobtn_open:
			getEleIsLearn(Const.controlOnlyOpenPid+"01");
			break;
		case R.id.lightitem_radiobtn_close:
			getEleIsLearn(Const.controlOnlyClosePid+"01");
			break;
		case R.id.lightitem_radiobtn_open2:
			getEleIsLearn(Const.controlOnlyOpenPid+"02");
			break;
		case R.id.lightitem_radiobtn_close2:
			getEleIsLearn(Const.controlOnlyClosePid+"02");
			break;
		case R.id.lightitem_radiobtn_open3:
			getEleIsLearn(Const.controlOnlyOpenPid+"03");
			break;
		case R.id.lightitem_radiobtn_close3:
			getEleIsLearn(Const.controlOnlyClosePid+"03");
			break;
		}
	}
	

}
