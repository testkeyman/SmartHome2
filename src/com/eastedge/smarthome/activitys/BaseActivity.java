package com.eastedge.smarthome.activitys;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eastedge.smarthome.R;
import com.eastedge.smarthome.adapters.DataAdapter;
import com.eastedge.smarthome.adapters.ElectricalAdapter;
import com.eastedge.smarthome.adapters.LifeAdapter;
import com.eastedge.smarthome.adapters.RoomAdapter;
import com.eastedge.smarthome.dragview.Configure;
import com.eastedge.smarthome.dragview.DragGridView;
import com.eastedge.smarthome.dragview.ScrollLayout;
import com.eastedge.smarthome.util.DensityUtil;

/**
 * Activity基类
 * */
public abstract class BaseActivity extends Activity {

	/** 每一页显示item的数量 */
	public static final int PAGE_SIZE = 8;
	/** 每一行显示item的数量 */
	public static final int NUM_COlUMNS = 4;

	/** 更新时间 */
	protected static final int UPDATETIME = 0;
	protected static Context context;
	/** GridView. */
	protected LinearLayout linear;
	protected LinearLayout.LayoutParams linearParam;
	protected DragGridView gridView;
	protected ScrollLayout mScrollLayout;
	/**判断是否是首次进入*/
	boolean isFirstIn=true;
	// 主界面的按钮，以及文本
	protected TextView mTextPage;
	
	// 当前位置和页码
	protected int itemPosition = 0;
	protected int curentPage = 0;
	//跳转前的当前页
	protected int beforePage=0;
	//跳转前的总页数
	protected int countPages=0;
	// 设置颜色选择
	protected final static int LIFE = 1, ROOM = 2, ELE = 3, MESSAGE = 4;
	public static int defaultIndex = 1;
	AlertDialog alert;
	/** adapter */
	protected DataAdapter dataAdapter;
	protected ElectricalAdapter electricalAdapter;
	protected RoomAdapter roomAdapter;
	protected LifeAdapter lifeAdapter;
	ArrayList<DataAdapter> adapterList=new ArrayList<DataAdapter>();
	// gridViews的数据集，用于存储gridView
	ArrayList<DragGridView> gridViews = new ArrayList<DragGridView>();
	// 全部数据的集合集lists.size()==countPage;
	ArrayList<ArrayList<Object>> lists = null;
	protected Handler mHandler;
	protected ProgressDialog progressDialog;
	// 每一页的数据
	ArrayList<Object> listData = new ArrayList<Object>();
	
	/** 设置界面的布局，以及查找控件，设置监听等 */
	protected abstract void setView();

	/** 子类的单击事件 */
//	protected abstract void onClickEvent(View v);
//	android.view.WindowManager$BadTokenException: Unable to add window -- token null is not for an application

	/** 初始化数据 */
	protected abstract void initData();
	/**拖动操作后的效果*/
//	protected abstract void dragOver();
	/**
	 * 添加新的GridView 全部数据的集合集lists.size()==countPage;
	 * 
	 * @param countPages
	 *            总共有多少个页面
	 * @return linear 新添加的页面
	 */
	public abstract LinearLayout addGridView(int countPages);

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		context = getApplicationContext();
//		BaseApplication.getInstance().addActivity(this);
		// 初始化所有信息参数，配置
		init();
		mHandler=new Handler(){
			public void handleMessage(android.os.Message msg) {
				switch (msg.what){
					case 0:
						progressDialog.dismiss();
						break;
				}
			};
		};
		Log.e("BaseActivity","--->onCreate");
	}
	

	/**
	 * 初始化
	 */
	protected void init() {

		// 设置子界面的视图
		setView();
		// 初始化数据
		initData();
		// 是否添加主界面的布局
		if (isInitMain()) {
			initScrollView();
			// 初始化页面布局的参数
			initConfigure();
			// 初始化GridView
			initGridView();
			// 页面改变时
			changePage();

		}
//		mHandler.sendEmptyMessageDelayed(0, 200);
		
	}

	/**
	 * 传入activity.class 实现跳转
	 * 
	 * @param clazz
	 */
	protected void setIntentSkip(Class<?> clazz) {
		Intent intent = new Intent();
		intent.setClass(getApplicationContext(), clazz);
		startActivity(intent);

	}

	/**
	 * 加载主界面的视图 一级分类和二级分类通过此方法加载视图
	 */
	protected boolean isInitMain() {
		return false;
	};

	/**
	 * 初始化GridView中的信息
	 */
	protected void initGridView() {
		for (int i = 0; i < Configure.countPages; i++) {
			mScrollLayout.addView(addGridView(i),new ViewGroup.LayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)));
			adapterList.add(dataAdapter);
		}
	}

	/**
	 * 页面发生改变时，修改当前界面的编号
	 */
	protected void changePage() {
		mScrollLayout.setPageListener(new ScrollLayout.PageListener() {
			@Override
			public void page(int page) {		
				setCurPage(page);
			}
		});
	}
	

	protected void initScrollView() {
		mScrollLayout = (ScrollLayout) findViewById(R.id.views);
		mTextPage = (TextView) findViewById(R.id.tv_page);
		mTextPage.setText("1");
		Configure.init(BaseActivity.this);
		linearParam = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT);
		linearParam.topMargin=DensityUtil.dip2px(context, 20);
		linearParam.rightMargin = DensityUtil.dip2px(context, 50);
		linearParam.leftMargin = DensityUtil.dip2px(context, 10);
		if (gridView != null) {
			mScrollLayout.removeAllViews();
		}
	}
	/**
	 * 初始化页面参数
	 */
	public void initConfigure() {
		// 根据数据量设置 页码数，每页显示8条数据
		Configure.countPages = (int) Math.ceil(listData.size()
				/ (float) PAGE_SIZE);
		if(listData.size()==0){
			Configure.countPages=1;
		}
		lists = new ArrayList<ArrayList<Object>>();
		for (int i = 0; i < Configure.countPages; i++) {
			lists.add(new ArrayList<Object>());
			for (int j = PAGE_SIZE * i; j < (PAGE_SIZE * (i + 1) > listData
					.size() ? listData.size() : PAGE_SIZE * (i + 1)); j++)
				lists.get(i).add(listData.get(j));
		}
		countPages=Configure.countPages;
		
	}

	
	/**
	 * 添加的item
	 * 
	 * @param item
	 * @param arrayAddItems
	 *            添加array中的item
	 * @param position
	 *            添加array中位置
	 */
	public void addItem(int curentPage, Object arrayAddItems) {
		int dataCount=lists.get(curentPage).size();
//		int sumData=listData.size();
		int position=dataCount%8;
		if(isFirstIn&&dataCount==8){
//			isFirstIn=false;
			lists.add(new ArrayList<Object>());
			mScrollLayout.addView(addGridView(Configure.countPages));
			Configure.countPages++;
			adapterList.add(dataAdapter);
			Log.e("---->",Configure.countPages+"----"+curentPage);
			curentPage++;
		}
		
		lists.get(curentPage).add(position, arrayAddItems);
		isFirstIn=true;
		mScrollLayout.snapToScreen(Configure.countPages-1);
		// 刷新界面
		((DataAdapter) ((gridViews.get(curentPage)).getAdapter()))
				.notifyDataSetChanged();
	}

	/**
	 * 设置当前页
	 * @param page
	 */
	public void setCurPage(int page) {
		mTextPage.setText((page + 1) + "");
		Configure.curentPage = page;
	}
	/**
	 * 创建弹框
	 * @param view
	 * @return
	 */
	public AlertDialog createBuilder(int id,View view){
		alert=new Builder(this).create();
		alert.setTitle(id);
		alert.setView(view);
		return alert;
	}
	@Override
	protected void onResume() {
		Configure.countPages=countPages;
		Configure.curentPage=beforePage;
		Log.e("-------","Base--->"+countPages+"----"+beforePage);
		super.onResume();
	}
	@Override
	protected void onPause() {
		countPages=Configure.countPages;
		beforePage=Configure.curentPage;
		Log.e("-------","--->"+countPages+"----"+beforePage);
		super.onPause();
	}
	
	

}
