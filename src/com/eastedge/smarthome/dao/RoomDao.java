package com.eastedge.smarthome.dao;

import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.eastedge.smarthome.db.SmartHomeDBOpenHelper;
import com.eastedge.smarthome.domain.Room;

public class RoomDao {
	public static final String TAG = "RoomDao";
	public SmartHomeDBOpenHelper dbHelper;
	public RoomDao(Context context) {
		dbHelper = new SmartHomeDBOpenHelper(context);
	}

	/**********************************************************************************
	 * 房间控制 添加一条数据
	 * 
	 * @param id
	 * @param position
	 * @param name
	 * @param bg
	 */
	public void insertIntoRoomCtrl(Room r) {
		// boolean result = find(name);
		// if (result)
		// return;
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		if (db.isOpen()) {
			ContentValues values=new ContentValues();
			values.put(SmartHomeDBOpenHelper._ID, r.getRoomCtrlId());
			values.put(SmartHomeDBOpenHelper.FOLDER_NAME, r.getRoomCtrlName());
			values.put(SmartHomeDBOpenHelper.FOLDER_BG, r.getRoomCtrlBg());
			values.put(SmartHomeDBOpenHelper.FOLDER_POS,r.getRoomCtrlPosition());
			long row=db.insert(SmartHomeDBOpenHelper.ROOM_CON_TABLE_NAME, null, values);
			Log.e(TAG+"insert-->","-->"+row);
			db.close();
		}
	}
	/***********
	 * 房间的容器中进行添加
	 * @param r
	 */
	public void insertIntoRoomContainer(Room r){
		SQLiteDatabase db=dbHelper.getWritableDatabase();
		if (db.isOpen()) {
			ContentValues values=new ContentValues();
			values.put(SmartHomeDBOpenHelper._ID, r.getRoomCtrlId());
			values.put(SmartHomeDBOpenHelper.FOLDER_POS,r.getRoomCtrlPosition());
			long row=db.insert(SmartHomeDBOpenHelper.ROOM_CONTAINER_TABLE, null, values);
			Log.e(TAG+"insert-->","-->"+row);
			db.close();
		}
	}
	/**
	 * 删除 房间控制容器表中的元素
	 * 
	 * @param id
	 */
	public void deleteFromRoomContainer(int pos) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		if (db.isOpen()) {
			db.execSQL("delete from "+SmartHomeDBOpenHelper.ROOM_CONTAINER_TABLE+ " where "+SmartHomeDBOpenHelper.FOLDER_POS+" = ?",
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
			Cursor cursor=db.rawQuery("select * from "+SmartHomeDBOpenHelper.ROOM_CONTAINER_TABLE,null);
			if(cursor.getCount()>3){
				ifFirst=false;
			}
			db.close();
		}
		Log.e("ifFirstInitContainer","------");
		return ifFirst;
	}

	/**
	 * 删除 房间控制表中的元素
	 * 
	 * @param id
	 */
	public void deleteFromRoomCtrl(int pos) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		if (db.isOpen()) {
			db.execSQL("delete from "+SmartHomeDBOpenHelper.ROOM_CON_TABLE_NAME+ " where "+SmartHomeDBOpenHelper.FOLDER_POS+" = ?",
					new Object[] { pos});
			db.close();
		}
	}

	/**
	 * 删除房间控制表
	 */
	public void deleteTable() {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		if (db.isOpen()) {
			db.execSQL("drop table "+SmartHomeDBOpenHelper.ROOM_CON_TABLE_NAME);
			db.close();
		}
	}

	/**
	 * 房间控制表 重命名 
	 * 
	 * @param r
	 */
	public void updateFromRoomCtrl(Room r,String newName) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		if (db.isOpen()) {
			db.execSQL(
					"update "+SmartHomeDBOpenHelper.ROOM_CON_TABLE_NAME+" set "+SmartHomeDBOpenHelper.FOLDER_NAME+" = ? where "+SmartHomeDBOpenHelper.FOLDER_POS+" = ?",
					new Object[] { newName, r.getRoomCtrlPosition() });
			db.close();
		}
	}
	/**
	 * 房间控制表改背景
	 * 
	 * @param r
	 */
	public void updateRoomColor(Room r,String bgName) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		if (db.isOpen()) {
			db.execSQL(
					"update "+SmartHomeDBOpenHelper.ROOM_CON_TABLE_NAME+" set "+SmartHomeDBOpenHelper.FOLDER_BG+" = ? where "+SmartHomeDBOpenHelper.FOLDER_POS+" = ?",
					new Object[] { bgName, r.getRoomCtrlPosition() });
			db.close();
		}
	}
	/**
	 * 查询房间控制容器表中的所有 房间信息
	 * 
	 * @return
	 */
	public ArrayList<Room> findAllByRoomContainer() {
		ArrayList<Room> rooms = null;
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		if (db.isOpen()) {
			rooms = new ArrayList<Room>();
			Cursor cursor = db.rawQuery("select * from  "+SmartHomeDBOpenHelper.ROOM_CONTAINER_TABLE, null);
			while (cursor.moveToNext()) {
				Room r = new Room(cursor.getInt(0), cursor.getInt(1));
				rooms.add(r);
			}
			cursor.close();
			db.close();
		}
		return rooms;
	}

	/**
	 * 查询房间控制表中的所有 房间信息
	 * 
	 * @return
	 */
	public ArrayList<Room> findAllByRoomCtrl() {
		ArrayList<Room> rooms = null;
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		if (db.isOpen()) {
			rooms = new ArrayList<Room>();
			Cursor cursor = db.rawQuery("select * from  "+SmartHomeDBOpenHelper.ROOM_CON_TABLE_NAME, null);
			while (cursor.moveToNext()) {
				Room r = new Room(cursor.getInt(0), cursor.getInt(3),
						cursor.getString(1), cursor.getString(2));
				rooms.add(r);
			}
			cursor.close();
			db.close();
		}
		return rooms;
	}
	

	/********************************************
	 * 房间表 查询房间内的电器 查询某个房间内所有的电器
	 */
	public Room findByRoomId(int pos) {
		Room room = null;
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		if (db.isOpen()) {
			String sql = "select * from "+SmartHomeDBOpenHelper.ROOM_CON_TABLE_NAME+" where "+SmartHomeDBOpenHelper.FOLDER_POS+"="+pos;
			Cursor c = db.rawQuery(sql, null);
			while (c.moveToNext()) {
				room= new Room(c.getInt(0),c.getInt(3),c.getString(1),c.getString(2));
			}
			c.close();
			db.close();
		}
		return room;
	}
	/**
	 * 更新数据库将room2上数据用room1代替
	 * @param s1 
	 * @param s2
	 */
	public void updateRoom(Room r1, Room r2) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		if (db.isOpen()) {
			ContentValues values=new ContentValues();    
//			values.put(SmartHomeDBOpenHelper._ID, r1.getRoomCtrlId());
			values.put(SmartHomeDBOpenHelper.FOLDER_POS, r1.getRoomCtrlPosition());
			values.put( SmartHomeDBOpenHelper.FOLDER_NAME, r1.getRoomCtrlName());
			values.put(SmartHomeDBOpenHelper.FOLDER_BG, r1.getRoomCtrlBg());
			db.update(SmartHomeDBOpenHelper.ROOM_CON_TABLE_NAME, values, SmartHomeDBOpenHelper._ID+"=?", new String[]{Integer.toString(r2.getRoomCtrlId())});
		}
		db.close();
	}

}
