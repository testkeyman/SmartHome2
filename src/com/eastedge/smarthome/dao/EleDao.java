package com.eastedge.smarthome.dao;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.eastedge.smarthome.db.SmartHomeDBOpenHelper;
import com.eastedge.smarthome.domain.Ele;

public class EleDao {
	private SmartHomeDBOpenHelper dbHelper;
	SQLiteDatabase db;
	public EleDao(Context context){
		dbHelper=new SmartHomeDBOpenHelper(context);
	}
	/**********************************************************************************
	 * 电器 添加数据
	 * 
	 * @param id
	 * @param position
	 * @param name
	 * @param bg
	 */
	public void insertIntoEleCtrl(Ele s) {
		db = dbHelper.getWritableDatabase();
		if (db.isOpen()) {

			db.execSQL(
					"insert into "
							+ SmartHomeDBOpenHelper.ELEC_CON_TABLE_NAME
							+ " (" + SmartHomeDBOpenHelper._ID + ","
							+ SmartHomeDBOpenHelper.FOLDER_NAME + ","
							+ SmartHomeDBOpenHelper.FOLDER_BG + ","
							+ SmartHomeDBOpenHelper.FOLDER_POS
							+ ") values (?,?,?,?)",
					new Object[] { s.getEleClassifyId(), s.getEleClassifyName(),
							s.getEleClassifyBg(), s.getEleClassifyPosition() });
		}
		closeSceneDao();
	}
	/**
	 * 电器表改背景
	 * 
	 * @param s
	 */
	public void updateEleColor(Ele s,String bgName) {
		db = dbHelper.getWritableDatabase();
		if (db.isOpen()) {
			db.execSQL(
					"update "+SmartHomeDBOpenHelper.ELEC_CON_TABLE_NAME+" set "+SmartHomeDBOpenHelper.FOLDER_BG+" = ? where "+SmartHomeDBOpenHelper.FOLDER_POS+" = ?",
					new Object[] { bgName, s.getEleClassifyPosition() });
			db.close();
		}
	}
	
	/**
	 * 查询所有情景
	 * 
	 * @return
	 */
	public ArrayList<Ele> findAllByEleCtrl() {
		ArrayList<Ele> eles = null;
		db = dbHelper.getWritableDatabase();
		if (db.isOpen()) {
			eles = new ArrayList<Ele>();
			Cursor cursor = db.rawQuery("select * from "
					+ SmartHomeDBOpenHelper.ELEC_CON_TABLE_NAME + ";", null);

			while (cursor.moveToNext()) {
				Ele r = new Ele(cursor.getInt(0), cursor.getString(1),
						cursor.getString(2), cursor.getInt(3));
				eles.add(r);
			}
			cursor.close();
			closeSceneDao();
		}
		return eles;
	}

	/**
	 * 按名字查询单个场景
	 * 
	 * @param eleName
	 * @return
	 */
	public Ele findEleByEleName(String eleName) {
		db = dbHelper.getWritableDatabase();
		Ele ele = null;
		if (db.isOpen()) {
			Cursor cursor = db.rawQuery("select * from "
					+ SmartHomeDBOpenHelper.ELEC_CON_TABLE_NAME + " where "
					+ SmartHomeDBOpenHelper.FOLDER_NAME + "=?",
					new String[] { eleName });
			if (cursor.getCount() != 0) {
				while (cursor.moveToNext()) {
					ele = new Ele(cursor.getInt(0), cursor.getString(1),
							cursor.getString(2), cursor.getInt(3));
				}
			}
			cursor.close();
			closeSceneDao();
		}
		return ele;
	}

	/**
	 * 根据索引查找对应场景
	 * 
	 * @param pos
	 * @return
	 */
	public Ele findSceneByElePos(int pos) {
		Ele scene = null;
		db = dbHelper.getWritableDatabase();
		if (db.isOpen()) {
			String str="select * from "
				+ SmartHomeDBOpenHelper.ELEC_CON_TABLE_NAME + " where "
				+ SmartHomeDBOpenHelper._ID + " = "+pos;
			Cursor cursor = db.rawQuery(str,
					null);
			if (cursor.getCount() != 0) {
				while (cursor.moveToNext()) {
					scene = new Ele(cursor.getInt(0), cursor.getString(1),
							cursor.getString(2), cursor.getInt(3));
				}
			}
			cursor.close();
			closeSceneDao();
		}
		return scene;
	}

	/**
	 * 更新数据库将s1上数据用s2代替
	 * @param s1 
	 * @param s2
	 */
	public void updateEle(Ele s1, Ele s2) {
		db = dbHelper.getWritableDatabase();
		if (db.isOpen()) {
			ContentValues values=new ContentValues();
			values.put( SmartHomeDBOpenHelper.FOLDER_NAME, s1.getEleClassifyName());
			values.put(SmartHomeDBOpenHelper.FOLDER_BG, s1.getEleClassifyBg());
			values.put(SmartHomeDBOpenHelper.FOLDER_POS, s1.getEleClassifyPosition());
			db.update(SmartHomeDBOpenHelper.ELEC_CON_TABLE_NAME, values, SmartHomeDBOpenHelper._ID+"=?", new String[]{Integer.toString(s2.getEleClassifyId())});
		}
		closeSceneDao();
	}
	/**
	 * 关闭数据库
	 */
	public void closeSceneDao() {
		if (null != dbHelper)
			dbHelper.close();
	}
}
