package com.eastedge.smarthome.util;

import java.util.List;

import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;

public class UseFullMedth {

	/**
	 * 
	 * @author jianzhic
	 * @date 2012-12-10下午12:05:05
	 * @param mContext
	 *            上下文对象
	 * @param className
	 *            要检查的服务的名字
	 * @return className服务是否开启（true/false）
	 */
	public static boolean CheckDaysServiceIsStart(Context mContext,
			String className) {
		ActivityManager mActivityManager = (ActivityManager) mContext
				.getSystemService("activity");
		List<ActivityManager.RunningServiceInfo> mServiceList = mActivityManager
				.getRunningServices(30);
		for (int i = 0; i < mServiceList.size(); i++) {
			if (className.equals(mServiceList.get(i).service.getClassName())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 字节数组转字符串
	 * 
	 * @param b
	 * @return
	 */
	public static String bytes2HexString(byte[] b) {
		String ret = "";
		for (int i = 0; i < b.length; i++) {
			String hex = Integer.toHexString(b[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			ret += hex.toUpperCase();
		}
		return ret;
	}

	/**
	 * 从机返回字节数组中取检验码
	 * 
	 * @param buffer
	 * @return
	 */
	public static String getResaveDataCrc(byte[] buffer) {
		int len = buffer.length;
		byte[] bs = new byte[len - 2];
		for (int i = 0; i < len - 2; i++) {
			bs[i] = buffer[i];
		}
		;
		CRC16 crc16 = new CRC16();
		return crc16.getCrc(bytes2HexString(bs));
	}

	/**
	 * 从机返回字节数组中取要进行校验的数据
	 * 
	 * @param buffer
	 * @return String
	 */
	public static String getResaveDataToCrc(byte[] buffer) {
		int len = buffer.length;
		byte[] bs = new byte[len - 2];
		for (int i = 0; i < len - 2; i++) {
			bs[i] = buffer[i];
		}
		return bytes2HexString(bs);
	}
	/**
	 * 取出一个从机地址
	 * 
	 * @param flag 设备分类标志
	 * @return String
	 */
	public static String getDeviceAdress(Context context,int flag) {
		return null;
		
	}

}
