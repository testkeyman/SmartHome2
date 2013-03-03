package com.eastedge.smarthome.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidParameterException;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.WindowManager;
import android_serialport_api.SerialPort;
import android_serialport_api.SerialPortFinder;

import com.eastedge.smarthome.R;
import com.eastedge.smarthome.broadcast.MyBroadCast;
import com.eastedge.smarthome.util.CRC16;
import com.eastedge.smarthome.util.Const;
import com.eastedge.smarthome.util.DataCon;
import com.eastedge.smarthome.util.UseFullMedth;

public class BackgroundService extends Service{
	public static boolean IsLearnModel;
	public static byte[] mValueToSend;
	public static  SerialPortFinder mSerialPortFinder = new SerialPortFinder();
	public static  SerialPort mSerialPort = null;
	private static final String TAG="BackgroundService";
	public static  OutputStream mOutputStream;
	public static  InputStream mInputStream;
	private static ReadThread mReadThread;
	public static boolean mByteReceivedBack;
	public static SendingThread mSendingThread;
	public Handler handler;
	@Override
	public void onCreate() {
		try {
			handler=new Handler(){
				public void handleMessage(Message mes){
					AlertDialog.Builder builder = new AlertDialog.Builder(
							getApplicationContext());
					builder.setTitle("警报器");
					builder.setNegativeButton("确定", null);
					AlertDialog d = builder.create();
					d.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
					d.show();
				}
			};
			mSerialPort=getSerialPort();
			mOutputStream = mSerialPort.getOutputStream();
			mInputStream = mSerialPort.getInputStream();
			mReadThread = new ReadThread();
			mReadThread.start();
		} catch (InvalidParameterException e) {
			Log.e(TAG,"InvalidParameterException-->"+e.getMessage());
			e.printStackTrace();
		} catch (SecurityException e) {
			Log.e(TAG,"SecurityException-->"+e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			Log.e(TAG,"IOException-->"+e.getMessage());
			e.printStackTrace();
		}
		
		super.onCreate();
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	/**
	 * 得到端口
	 * @return
	 * @throws SecurityException
	 * @throws IOException
	 * @throws InvalidParameterException
	 */
	public SerialPort getSerialPort() throws SecurityException, IOException,
			InvalidParameterException {
		if (mSerialPort == null) {
			/* Open the serial port */
			String[] strings = mSerialPortFinder.getAllDevicesPath();
			for (int i = 0; i < strings.length; i++) {

				System.out.println("" + strings[i]);
			}
			mSerialPort = new SerialPort(new File("/dev/ttyS2"), 115200, 0);
		}
		return mSerialPort;
	}
	/**
	 * @author baojianming
	 *
	 *启动一个读取线程
	 */
	public class ReadThread extends Thread {
		@Override
		public void run() {
			super.run();
			while (true) {
				int size;
				try {
					byte[] buffer = new byte[64];
					Thread.sleep(100);
					if (mInputStream == null){
						return;
					}
					size = mInputStream.read(buffer);
					System.out.println("size---->"+size);
					if (size > 0) {
						onDataReceived(buffer, size);
					}
				} catch (IOException e) {
					e.printStackTrace();
					return;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 * 读取操作
	 * @param buffer
	 * @param size
	 */
	public  void onDataReceived(byte[] buffer, int size) {
//		synchronized (mByteReceivedBackSemaphore) {
			Log.e("onDataReceived", "onDataReceived");
			String resveData = UseFullMedth.bytes2HexString(buffer).substring(
					0, size * 2);
			String getcrcFromdata = UseFullMedth.getResaveDataCrc(buffer);
			System.out.println("接收到的数据：" + resveData);
			if(resveData.length()>4){
			DataCon dataCon = new DataCon(resveData);
			System.out.println(Integer.decode("0x"+dataCon.getFunno()));
			switch (Integer.decode("0x"+dataCon.getFunno())) {
			case Const.FUNNUM67:// 0x43
				System.out.println("功能码为：" + Const.FUNNUM67);
				break;
			case Const.FUNNUM06:// 0x06
				System.out.println("功能码为：" + Const.FUNNUM06);
				break;
			case Const.FUNNUM03:// 0x03
				System.out.println("功能码为：" + Const.FUNNUM03);
				break;
			case Const.FUNNUM88:// 0x58
				System.out.println("功能码为：" + Const.FUNNUM88);
				break;
			case Const.FUNNUM83://0x53
				String whichWarn=resveData.substring(4, 10);
				Intent intent=new Intent(BackgroundService.this,MyBroadCast.class);
				intent.setAction("receiveFun53");
				intent.putExtra("fun", whichWarn);
				sendBroadcast(intent);
				
//				if("51F530".equals(whichWarn)){
//					MediaPlayer player=MediaPlayer.create(this, R.raw.sound);
//					player.start();
//				}
//				if("555555".equals(whichWarn)){
//					MediaPlayer player=MediaPlayer.create(this, R.raw.sound);
//					player.start();
//				}
//				handler.sendEmptyMessage(0);
				System.out.println("功能码为：53");
				break;
			default:
				System.out.println("功能码不存在");
				
				break;
			}
			if (dataCon.getCrc().equals(getcrcFromdata)) {// 校验码正确
				System.out.println("数据校验正确！" + getcrcFromdata);
			}
			}
//			int i;

//			for (i = 0; i < size; i++) {
//				if ((buffer[i] == mBuffer1[i]) && (mByteReceivedBack == false)) {
//					mByteReceivedBack = true;
//					mByteReceivedBackSemaphore.notify();
//				} else {
//					mCorrupted++;
//				} 
//			}
//		}
	}

	/**
	 * 发送消息
	 * @param mBuffer
	 */
	public static void sendMsg(byte[] mBuffer) {
		if (mSerialPort != null) {
			mValueToSend = mBuffer;
			mSendingThread = SendingThread.getSendingThreadInstance();
			if(mSendingThread.isInterrupted()){
			mSendingThread.start();
			}else{
				mSendingThread.run();
			}
		}
	}
	/**
	 * @author baojianming
	 *启动一个发送线程
	 */
	public static  class SendingThread extends Thread {
		private SendingThread(){};
		public static synchronized SendingThread getSendingThreadInstance(){
			if(null==mSendingThread){
				mSendingThread=new SendingThread();
			}
			return mSendingThread;
		}
		@Override
		public void run() {
//			synchronized (mByteReceivedBackSemaphore) {
				mByteReceivedBack = false;
				try {
					if (mOutputStream != null) {
						mOutputStream.write(mValueToSend);
						System.out.println("start send data "+CRC16.getBufHexStr(mValueToSend));
					} else {
						return;
					}
				} catch (IOException e) {
					e.printStackTrace();
					return;
				}
//				mOutgoing++;
//				try {
//					mByteReceivedBackSemaphore.wait(1000);
//					if (mByteReceivedBack == true) {
//						mIncoming++;
//					} else {
//						mLost++;
//					}
//				} catch (InterruptedException e) {
//				}
//			}
		}
	}
}
