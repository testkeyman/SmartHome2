package com.eastedge.smarthome.db;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 创建智能家居的数据库和表
 * 数据库smart_home
 * 表：scene,room,file,ele,message
 * @author wsp
 *
 */
public class SmartHomeDBOpenHelper extends SQLiteOpenHelper {

	public SmartHomeDBOpenHelper(Context context) {
		super(context, "smart_home.db", null,1);
	}
	//生活场景库
	public final static String  LIFE_SCENE_TABLE_NAME = "lifeScene";
	//房间控制库
	public final static String  ROOM_CON_TABLE_NAME = "room_control";
	//电器控制库
	public final static String  ELEC_CON_TABLE_NAME = "ele_control";
	//生活场景标志id
	public final static String _ID="_id";
	//文件夹名字
	public final static String FOLDER_NAME="life_content";
	//文件夹 背景
	public final static String FOLDER_BG="life_bg";
	//对应 位置
	public final static String FOLDER_POS="life_pos";
	//房间控制的容器
	public final static String ROOM_CONTAINER_TABLE="room_container";
	//情景控制容器
	public final static String SCENE_CONTAINER_TABLE="scene_container";
	//电器控制容器
	public final static String ELEC_CONTAINER_TABLE="electric_container";
	public final static String ELEC_PID="electric_pid";
	public final static String ELEC_NAME="electric_name";
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE IF NOT EXISTS "+LIFE_SCENE_TABLE_NAME+"("+_ID+" INTEGER PRIMARY KEY , "+FOLDER_NAME+" varchar(20), "+FOLDER_BG+" varchar(20), "+FOLDER_POS+" INTEGER)");
//		// 房间—— 房间ID，房间名称，房间内所拥有的电器ID
		db.execSQL("CREATE TABLE IF NOT EXISTS "+ROOM_CON_TABLE_NAME+"("+_ID+" INTEGER PRIMARY KEY , "+FOLDER_NAME+" varchar(20), "+FOLDER_BG+" varchar(20), "+FOLDER_POS+" INTEGER)");
		db.execSQL("CREATE TABLE IF NOT EXISTS "+ELEC_CON_TABLE_NAME+"("+_ID+" INTEGER PRIMARY KEY , "+FOLDER_NAME+" varchar(20), "+FOLDER_BG+" varchar(20), "+FOLDER_POS+" INTEGER)");
		db.execSQL("CREATE TABLE IF NOT EXISTS "+ROOM_CONTAINER_TABLE+"("+_ID+" INTEGER PRIMARY KEY ,"+FOLDER_POS+" INTEGER)");
		db.execSQL("CREATE TABLE IF NOT EXISTS "+SCENE_CONTAINER_TABLE+"("+_ID+" INTEGER PRIMARY KEY ,"+FOLDER_POS+" INTEGER)");
		db.execSQL("CREATE TABLE IF NOT EXISTS "+ELEC_CONTAINER_TABLE+"("+_ID+" INTEGER PRIMARY KEY ,"+FOLDER_POS+" INTEGER)");
		db.execSQL("CREATE TABLE IF NOT EXISTS "+ELEC_PID +"( "+_ID+" INTEGER PRIMARY KEY)");
		db.execSQL("CREATE TABLE IF NOT EXISTS "+ELEC_NAME +"( "+_ID+" INTEGER PRIMARY KEY ,"+FOLDER_NAME+" varchar(20))");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("drop table IF EXISTS "+LIFE_SCENE_TABLE_NAME);
		db.execSQL("drop table IF EXISTS "+ROOM_CON_TABLE_NAME);
		db.execSQL("drop table IF EXISTS "+ROOM_CONTAINER_TABLE);
		db.execSQL("drop table IF EXISTS "+SCENE_CONTAINER_TABLE);
		db.execSQL("drop table IF EXISTS "+ELEC_CON_TABLE_NAME);
		db.execSQL("drop table IF EXISTS "+ELEC_CONTAINER_TABLE);
		db.execSQL("drop table IF EXISTS "+ELEC_PID);
		db.execSQL("drop table IF EXISTS "+ELEC_NAME);
		onCreate(db);
	}

}
