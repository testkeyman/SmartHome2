package com.eastedge.smarthome.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.eastedge.smarthome.domain.Customview;


public class CommentsUtil {
	// 应用程序的文件根路径
	public static final String FILE_PATH = "/data/data/com.eastedge.smarthome/files/";
	/**
	 * 格式化时间
	 * 
	 * @return
	 */
	public static  String getTime() {
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date(System.currentTimeMillis());
		String time = df.format(date);
		return time;
	}

	/**
	 * 写入对象
	 * @param aid  文件夹名称
	 * @param list 对象列表
	 */
	public static void writeCustomview(File file, ArrayList<Customview> list) {
		try{	
		ObjectOutputStream out = new ObjectOutputStream(
					new FileOutputStream(file));
			out.writeObject(list);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 读出对象
	 * @param aid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static ArrayList<Customview> readCustomview(File f) {
		ObjectInputStream in = null;
		try {
//			File f = new File(FILE_PATH + aid);

			if (f.exists()) {
				in = new ObjectInputStream(new FileInputStream(f));
				ArrayList<Customview> list = (ArrayList<Customview>) in
						.readObject();
				return list;
			}

		} catch (Exception e) {
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
		return null;
	}

}
