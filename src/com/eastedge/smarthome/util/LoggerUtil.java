package com.eastedge.smarthome.util;

import android.util.Log;

/**
 *  日志过滤器
 * @author wenshengping
 *
 */
public class LoggerUtil {
	
	public static int LOG_LEVEL  = 0;
	public static int VERBOSE  = 5;
	public static int DEBUG  = 4;
	public static int INFO  = 3;
	public static int WARN  = 2;
	public static int ERROR  = 1;
	
	public static void v(String tag,String msg){
		if(LOG_LEVEL>VERBOSE){
			Log.v(tag, msg);
		}
	}
	
	public static void d(String tag,String msg){
		if(LOG_LEVEL>DEBUG){
			Log.d(tag, msg);
		}
	}
	public static void i(String tag,String msg){
		if(LOG_LEVEL>INFO){
			Log.i(tag, msg);
		}
	}
	public static void w(String tag,String msg){
		if(LOG_LEVEL>WARN){
			Log.w(tag, msg);
		}
	}
	public static void e(String tag,String msg){
		if(LOG_LEVEL>ERROR){
			Log.e(tag, msg);
		}
	}
	
}
