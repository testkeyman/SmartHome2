package com.eastedge.smarthome.dao;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.eastedge.smarthome.db.SmartHomeDBOpenHelper;

public class ElecPidDao {
	public SmartHomeDBOpenHelper dbHelper;
	public SQLiteDatabase db;
	public ElecPidDao(Context context){
		dbHelper=new SmartHomeDBOpenHelper(context);
	}
	public boolean isFirstInsert(){
		boolean isFirst=true;
		db=dbHelper.getWritableDatabase();
		Cursor cursor=db.rawQuery("select * from "+SmartHomeDBOpenHelper.ELEC_PID, null);
		if(db.isOpen()&&cursor.getCount()>5){
			isFirst=false;
		}
		db.close();
		return isFirst;
	}
	public void insertElecPid(int pid){
		db=dbHelper.getWritableDatabase();
		db.execSQL("insert into "+ SmartHomeDBOpenHelper.ELEC_PID +" values ("+pid+");");
		db.close();
	}
	public void deleteElecPid(int pid){
		db=dbHelper.getWritableDatabase();
		db.execSQL("delete from "+SmartHomeDBOpenHelper.ELEC_PID +" where "+SmartHomeDBOpenHelper._ID +" ="+pid);
	}
	public ArrayList<Integer> findAllElecId(){
		ArrayList<Integer> list=new ArrayList<Integer>();
		db=dbHelper.getWritableDatabase();
		if(db.isOpen()){
			Cursor cursor=db.rawQuery("select * from "+SmartHomeDBOpenHelper.ELEC_PID, null);
			while(cursor.moveToNext()){
				list.add(cursor.getInt(0));
			}
		}
		db.close();
		return list;
	}
}
