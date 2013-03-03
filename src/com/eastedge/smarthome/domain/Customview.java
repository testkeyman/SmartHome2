package com.eastedge.smarthome.domain;

import java.io.Serializable;

import com.eastedge.smarthome.service.BackgroundService;
import com.eastedge.smarthome.util.CRC16;
import com.eastedge.smarthome.util.Const;

import android.content.Context;
import android.view.View;

//public interface Customview extends Serializable  {
//	//返回一个视图
//	public View getBeanView(Context context);
//}
public abstract class Customview implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**颜色id*/
	protected int bgId;
	/**电器名称*/
	protected String deviceName;
	/**电器id*/
	protected int device_id;
	/**电器是否学习*/
	protected boolean isLearn;
	/**电器房间*/
	String device_location;
	public String getDevice_location() {
		return device_location;
	}

	public void setDevice_location(String device_location) {
		this.device_location = device_location;
	}
	public abstract View getBeanView(Context context);
	public int getBgId() {
		return bgId;
	}
	public void setBgId(int device_id) {
		this.bgId = device_id;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public int getDevice_id() {
		return device_id;
	}
	
	public void setDevice_id(int device_id) {
		this.device_id = device_id;
	}
	public boolean getIsLearn(){
		return isLearn;
	}
	public void setIsLearn(boolean isLearn){
		this.isLearn=isLearn;
	}
	/**
	 * 判断当前设备是否已经学习
	 * @param custom 当前设备
	 * @param buttonId 当前设备上选中的按钮
	 */
	public void getEleIsLearn(String buttonId){
			byte[]studyByte=CRC16.getSendBuf(Const.studySendPid+CRC16.int2Str(getDevice_id()));
			BackgroundService.sendMsg(studyByte);
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			byte[]mBuffer1=CRC16.getSendBuf(buttonId);
			BackgroundService.sendMsg(mBuffer1);
	}
	
}