package com.eastedge.smarthome.broadcast;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.eastedge.smarthome.domain.WarningDeviceBean;
import com.eastedge.smarthome.service.BackgroundService;

public class MyBroadCast extends BroadcastReceiver {
	private String fun;
	private WarningDeviceBean warnDevice;
	@Override
	public void onReceive(Context context, Intent intent) {
		if("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())){
			Log.e("MyBroadCast","---->+onReceive");
		Intent serviceIntent=new Intent(context,BackgroundService.class);
		context.startService(serviceIntent);
		}
		if("receiveFun53".equals(intent.getAction())){
			fun=intent.getStringExtra("fun");
			if(null!=warnDevice){
				warnDevice.setDevice_fun(fun);
				warnDevice.setIsLearn(true);
				Log.e("onReceive----","---->"+warnDevice.getIsLearn());
				warnDevice=null;
			}
		}
		if("studyMod".equals(intent.getAction())){
			warnDevice=(WarningDeviceBean) intent.getSerializableExtra("warnDevice");
		}
	}

}
