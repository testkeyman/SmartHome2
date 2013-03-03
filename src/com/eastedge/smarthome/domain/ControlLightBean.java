package com.eastedge.smarthome.domain;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.eastedge.smarthome.R;
import com.eastedge.smarthome.service.BackgroundService;
import com.eastedge.smarthome.util.CRC16;
import com.eastedge.smarthome.util.Const;

public class ControlLightBean extends Customview implements OnClickListener {
	private static final long serialVersionUID = 1L;

	@Override
	public View getBeanView(Context context) {
		View layoutInflater = LayoutInflater.from(context).inflate(
				R.layout.control_light_bean, null);
		Button lightOpen=(Button) layoutInflater.findViewById(R.id.light_open);
		Button lightClose=(Button) layoutInflater.findViewById(R.id.light_close);
		Button lightAdd=(Button) layoutInflater.findViewById(R.id.add_light);
		Button lightCut=(Button) layoutInflater.findViewById(R.id.cut_light);
		lightOpen.setOnClickListener(this);
		lightClose.setOnClickListener(this);
		lightAdd.setOnClickListener(this);
		lightCut.setOnClickListener(this);
		return layoutInflater;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.light_open:
				getEleIsLearn(Const.controlOnlyOpenPid+"01");
				break;
			case R.id.light_close:
				getEleIsLearn(Const.controlOnlyClosePid+"01");
				break;
			case R.id.add_light:
				getEleIsLearn(Const.controlLightAddPid+"01");
				break;
			case R.id.cut_light:
				getEleIsLearn(Const.controlLightCutPid+"01");
				break;
				
		}
	}
	
}
