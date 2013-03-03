package com.eastedge.smarthome.dao;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.eastedge.smarthome.db.SmartHomeDBOpenHelper;
import com.eastedge.smarthome.domain.Scene;
import com.eastedge.smarthome.domain.Scene;

public class SceneDao {
	public static final String TAG = "SceneDao";
	private SmartHomeDBOpenHelper dbHelper;
	SQLiteDatabase db;

	public SceneDao(Context context) {
		dbHelper = new SmartHomeDBOpenHelper(context);
		
	}

	/**********************************************************************************
	 * 情景控制 添加数据
	 * 
	 * @param id
	 * @param position
	 * @param name
	 * @param bg
	 */
	public void insertIntoSceneCtrl(Scene s) {
		db = dbHelper.getWritableDatabase();
		if (db.isOpen()) {

			db.execSQL(
					"insert into "
							+ SmartHomeDBOpenHelper.LIFE_SCENE_TABLE_NAME
							+ " (" + SmartHomeDBOpenHelper._ID + ","
							+ SmartHomeDBOpenHelper.FOLDER_NAME + ","
							+ SmartHomeDBOpenHelper.FOLDER_BG + ","
							+ SmartHomeDBOpenHelper.FOLDER_POS
							+ ") values (?,?,?,?)",
					new Object[] { s.getSceneId(), s.getSceneName(),
							s.getSceneBg(), s.getScenePosition() });
		}
		closeSceneDao();
	}
	/***********
	 * 情景的容器中进行添加
	 * @param r
	 */
	public void insertIntoSceneContainer(Scene s){
		db=dbHelper.getWritableDatabase();
		if (db.isOpen()) {
			ContentValues values=new ContentValues();
			values.put(SmartHomeDBOpenHelper._ID, s.getSceneId());
			values.put(SmartHomeDBOpenHelper.FOLDER_POS,s.getScenePosition());
			long row=db.insert(SmartHomeDBOpenHelper.SCENE_CONTAINER_TABLE, null, values);
			Log.e(TAG+"insert-->","-->"+row);
			db.close();
		}
	}

	/**
	 * 房间控制表 重命名 
	 * 
	 * @param s
	 */
	public void updateFromSceneName(Scene s,String newName) {
		db = dbHelper.getWritableDatabase();
		if (db.isOpen()) {
			db.execSQL(
					"update "+SmartHomeDBOpenHelper.LIFE_SCENE_TABLE_NAME+" set "+SmartHomeDBOpenHelper.FOLDER_NAME+" = ? where "+SmartHomeDBOpenHelper.FOLDER_POS+" = ?",
					new Object[] { newName, s.getScenePosition() });
			db.close();
		}
	}
	/**
	 * 房间控制表改背景
	 * 
	 * @param s
	 */
	public void updateSceneColor(Scene s,String bgName) {
		db = dbHelper.getWritableDatabase();
		if (db.isOpen()) {
			db.execSQL(
					"update "+SmartHomeDBOpenHelper.LIFE_SCENE_TABLE_NAME+" set "+SmartHomeDBOpenHelper.FOLDER_BG+" = ? where "+SmartHomeDBOpenHelper.FOLDER_POS+" = ?",
					new Object[] { bgName, s.getScenePosition() });
			db.close();
		}
	}
	/**
	 * 删除 情景容器表中的元素
	 * 
	 * @param id
	 */
	public void deleteFromSceneContainer(int pos) {
		db = dbHelper.getWritableDatabase();
		if (db.isOpen()) {
			db.execSQL("delete from "+SmartHomeDBOpenHelper.SCENE_CONTAINER_TABLE+ " where "+SmartHomeDBOpenHelper.FOLDER_POS+" = ?",
					new Object[] { pos });
			db.close();
		}
	}
	/**
	 * 查询情景容器表中的所有 房间信息
	 * 
	 * @return
	 */
	public ArrayList<Scene> findAllBySceneContainer() {
		ArrayList<Scene> scenes = null;
		db = dbHelper.getReadableDatabase();
		if (db.isOpen()) {
			scenes = new ArrayList<Scene>();
			Cursor cursor = db.rawQuery("select * from  "+SmartHomeDBOpenHelper.SCENE_CONTAINER_TABLE, null);
			while (cursor.moveToNext()) {
				Scene r = new Scene(cursor.getInt(0), cursor.getInt(1));
				scenes.add(r);
			}
			cursor.close();
			db.close();
		}
		return scenes;
	}
	/**
	 * 判断是否是第一次添加容器
	 * @return
	 */
	public boolean ifFirstInitContainer(){
		boolean ifFirst=true;
		db=dbHelper.getWritableDatabase();
		if(db.isOpen()){
			Cursor cursor=db.rawQuery("select * from "+SmartHomeDBOpenHelper.SCENE_CONTAINER_TABLE,null);
			if(cursor.getCount()>3){
				ifFirst=false;
			}
			db.close();
		}
		Log.e("ifFirstInitContainer","------");
		return ifFirst;
	}

	/**
	 * 查询所有情景
	 * 
	 * @return
	 */
	public ArrayList<Scene> findAllBySceneCtrl() {
		ArrayList<Scene> scenes = null;
		db = dbHelper.getWritableDatabase();
		if (db.isOpen()) {
			scenes = new ArrayList<Scene>();
			Cursor cursor = db.rawQuery("select * from "
					+ SmartHomeDBOpenHelper.LIFE_SCENE_TABLE_NAME + ";", null);

			while (cursor.moveToNext()) {
				Scene r = new Scene(cursor.getInt(0), cursor.getString(1),
						cursor.getString(2), cursor.getInt(3));
				scenes.add(r);
			}
			cursor.close();
			closeSceneDao();
		}
		return scenes;
	}

	/**
	 * 按名字查询单个场景
	 * 
	 * @param sceneName
	 * @return
	 */
	public Scene findSceneBySceName(String sceneName) {
		db = dbHelper.getWritableDatabase();
		Scene scene = null;
		if (db.isOpen()) {
			Cursor cursor = db.rawQuery("select * from "
					+ SmartHomeDBOpenHelper.LIFE_SCENE_TABLE_NAME + " where "
					+ SmartHomeDBOpenHelper.FOLDER_NAME + "=?",
					new String[] { sceneName });
			if (cursor.getCount() != 0) {
				while (cursor.moveToNext()) {
					scene = new Scene(cursor.getInt(0), cursor.getString(1),
							cursor.getString(2), cursor.getInt(3));
				}
			}
			cursor.close();
			closeSceneDao();
		}
		return scene;
	}

	/**
	 * 根据索引查找对应场景
	 * 
	 * @param pos
	 * @return
	 */
	public Scene findSceneByScePos(int pos) {
		Scene scene = null;
		db = dbHelper.getWritableDatabase();
		if (db.isOpen()) {
			String str="select * from "
				+ SmartHomeDBOpenHelper.LIFE_SCENE_TABLE_NAME + " where "
				+ SmartHomeDBOpenHelper._ID + " = "+pos;
			Cursor cursor = db.rawQuery(str,
					null);
			Log.e(TAG+"findSceneByScePos","--->"+str);
			if (cursor.getCount() != 0) {
				while (cursor.moveToNext()) {
					scene = new Scene(cursor.getInt(0), cursor.getString(1),
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
	public void updateScene(Scene s1, Scene s2) {
		db = dbHelper.getWritableDatabase();
		if (db.isOpen()) {
			ContentValues values=new ContentValues();
			values.put( SmartHomeDBOpenHelper.FOLDER_NAME, s1.getSceneName());
			values.put(SmartHomeDBOpenHelper.FOLDER_BG, s1.getSceneBg());
			values.put(SmartHomeDBOpenHelper.FOLDER_POS, s1.getScenePosition());
			db.update(SmartHomeDBOpenHelper.LIFE_SCENE_TABLE_NAME, values, SmartHomeDBOpenHelper._ID+"=?", new String[]{Integer.toString(s2.getSceneId())});
		}
		closeSceneDao();
	}
	public void deleteFromSceneByPos(int pos){
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		if (db.isOpen()) {
			db.execSQL("delete from "+SmartHomeDBOpenHelper.LIFE_SCENE_TABLE_NAME+ " where "+SmartHomeDBOpenHelper.FOLDER_POS+" = ?",
					new Object[] { pos});
			db.close();
		}
	}
	

	/**
	 * 关闭数据库
	 */
	public void closeSceneDao() {
		if (null != dbHelper)
			dbHelper.close();
	}
}
