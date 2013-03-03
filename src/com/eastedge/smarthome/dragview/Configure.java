package com.eastedge.smarthome.dragview;

import android.app.Activity;
import android.util.DisplayMetrics;

public class Configure {

	/** 是否移动 */
	public static boolean isMove = false;
	/** 页面是否发生改变 */
	public static boolean isChangingPage = false;
	/** 当期屏幕的宽、高、密度 */
	public static int screenHeight = 0;
	public static int screenWidth = 0;
	public static float screenDensity = 0;

	/** 当前界面的页码 */
	public static int curentPage = 0;
	/** 总共有多少页 */
	public static int countPages = 0;

	public final static int LAYOUT = 100, EDIT = 101, DELETE = 102,
			RENAME = 103, RECOLOR = 104;

	/**
	 * 根据屏幕的密度来设置布局的宽和高
	 * 
	 * @param context
	 */
	public static void init(Activity context) {
		if (screenDensity == 0 || screenWidth == 0 || screenHeight == 0) {
			DisplayMetrics dm = new DisplayMetrics();
			context.getWindowManager().getDefaultDisplay().getMetrics(dm);
			Configure.screenDensity = dm.density;
			Configure.screenHeight = dm.heightPixels;
			Configure.screenWidth = dm.widthPixels;
		}
		curentPage = 0;
		countPages = 0;
	}

}