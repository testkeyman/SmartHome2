package com.eastedge.smarthome.dao;

import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.eastedge.smarthome.db.SmartHomeDBOpenHelper;

public class ExistsElecDao {
	public SmartHomeDBOpenHelper dbHelper;
	public ExistsElecDao(Context context) {
		dbHelper = new SmartHomeDBOpenHelper(context);
	}
	/***********
	 * 的容器中进行添加
	 * @param r
	 */
	public void insertIntoElecName(int id,String r){
		SQLiteDatabase db=dbHelper.getWritableDatabase();
		if (db.isOpen()) {
			ContentValues values=new ContentValues();
			values.put(SmartHomeDBOpenHelper._ID, id);
			values.put(SmartHomeDBOpenHelper.FOLDER_NAME, r);
			
			db.insert(SmartHomeDBOpenHelper.ELEC_NAME, null, values);
			db.close();
		}
	}
	/**
	 * 删除 存在电器容器表中的元素
	 * 
	 * @param id
	 */
	public void deleteFromRoomContainer(int pos) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		if (db.isOpen()) {
			db.execSQL("delete from "+SmartHomeDBOpenHelper.ELEC_NAME+ " where "+SmartHomeDBOpenHelper.FOLDER_POS+" = ?",
					new Object[] { pos });
			db.close();
		}
	}
	/**
	 * 判断是否是第一次添加容器
	 * @return
	 */
	public boolean ifFirstInitContainer(){
		boolean ifFirst=true;
		SQLiteDatabase db=dbHelper.getWritableDatabase();
		if(db.isOpen()){
			Cursor cursor=db.rawQuery("select * from "+SmartHomeDBOpenHelper.ELEC_NAME,null);
			if(cursor.getCount()>3){
				ifFirst=false;
			}
			db.close();
		}
		return ifFirst;
	}

	/**
	 * 查询存在电器表中的所有 信息
	 * 
	 * @return
	 */
	public ArrayList<String> findAllElecName() {
		ArrayList<String> elecNames = null;
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		if (db.isOpen()) {
			elecNames = new ArrayList<String>();
			Cursor cursor = db.rawQuery("select * from  "+SmartHomeDBOpenHelper.ELEC_NAME, null);
			while (cursor.moveToNext()) {
				elecNames.add(cursor.getString(1));
			}
			cursor.close();
			db.close();
		}
		return elecNames;
	}
}
