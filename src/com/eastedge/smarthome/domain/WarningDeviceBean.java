package com.eastedge.smarthome.domain;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.eastedge.smarthome.R;
import com.eastedge.smarthome.broadcast.MyBroadCast;

public class WarningDeviceBean extends Customview {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 位置
	String device_fun;
	// 开关状态
	boolean device_state;
	// 延时时间
	String device_delaytime;
	@Override
	public View getBeanView(final Context context) {
		View layoutInflater = LayoutInflater.from(context).inflate(
				R.layout.layout_waringdeviceitem, null);
		Button studyBut=(Button) layoutInflater.findViewById(R.id.ele_learn);
		if(isLearn){
			studyBut.setText(R.string.ele_learn);
		}else{
		studyBut.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
					Intent intent=new Intent(context,MyBroadCast.class);
					intent.setAction("studyMod");
					intent.putExtra("warnDevice", WarningDeviceBean.this);
					context.sendBroadcast(intent);
			}
		});
		}
		return layoutInflater;
	}
	

	public String getDevice_fun() {
		return device_fun;
	}


	public void setDevice_fun(String device_fun) {
		this.device_fun = device_fun;
	}


	public boolean isDevice_state() {
		return device_state;
	}

	public void setDevice_state(boolean device_state) {
		this.device_state = device_state;
	}

	public String getDevice_delaytime() {
		return device_delaytime;
	}

	public void setDevice_delaytime(String device_delaytime) {
		this.device_delaytime = device_delaytime;
	}
}
