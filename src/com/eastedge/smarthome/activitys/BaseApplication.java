package com.eastedge.smarthome.activitys;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.util.Log;

import com.eastedge.smarthome.service.BackgroundService;
import com.eastedge.smarthome.util.UseFullMedth;

/**
 * 
 */
public class BaseApplication extends Application {
//	public static  List<Activity> mList = new LinkedList<Activity>();
	private static BaseApplication instance;
	@Override
	public void onCreate() {
		super.onCreate();
	}
	private BaseApplication() {
	}

	public synchronized static BaseApplication getInstance() {
		if (null == instance) {
			instance = new BaseApplication();
		}
		return instance;
	}

	// add Activity
	/**
	 * 添加activity
	 * @param activity
	 */
//	public void addActivity(Activity activity) {
//		mList.add(activity);
//	}

//	/**
//	 * 程序退出
//	 */
//	public void exit() {
//		try {
//			for (Activity activity : mList) {
//				if (activity != null)
//					activity.finish();
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			System.exit(0);
//		}
//	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
		System.gc();
	}

}