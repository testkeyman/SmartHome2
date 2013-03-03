package com.eastedge.smarthome.activitys;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.eastedge.smarthome.R;
import com.eastedge.smarthome.R.drawable;
import com.eastedge.smarthome.activitygroup.MainGroup;
import com.eastedge.smarthome.activitygroup.MainGroup.SwitchAdd;
import com.eastedge.smarthome.adapters.DataAdapter;
import com.eastedge.smarthome.adapters.LifeAdapter;
import com.eastedge.smarthome.dao.SceneDao;
import com.eastedge.smarthome.dao.SceneDao;
import com.eastedge.smarthome.domain.Customview;
import com.eastedge.smarthome.domain.Scene;
import com.eastedge.smarthome.domain.Scene;
import com.eastedge.smarthome.domain.Scene;
import com.eastedge.smarthome.dragview.Configure;
import com.eastedge.smarthome.dragview.DragGridView;
import com.eastedge.smarthome.dragview.DragGridView.ChildPopupOnClick;
import com.eastedge.smarthome.dragview.DragGridView.SwitchPositionListener;
import com.eastedge.smarthome.dragview.ScrollLayout;
import com.eastedge.smarthome.util.CommentsUtil;
import com.eastedge.smarthome.util.DensityUtil;

/** 生活情境 */
public class LifeSceneActivity extends BaseActivity implements android.view.View.OnClickListener {
	private int curentPage = 0;
	private SceneDao sceneDao;
	private final String TAG = "LifeSceneActivity";
	private ArrayList<Scene> sceneContainList=null;
	private Scene itemScene;
	@Override
	protected boolean isInitMain() {
		return true;
	}

	@Override
	protected void setView() {
		setContentView(R.layout.activity_life_scene);
	}
	@Override
	protected void initData() {
		sceneDao = new SceneDao(this);
		ArrayList<Scene> sceneList;
		sceneList = sceneDao.findAllBySceneCtrl();
		
		if (0 == sceneList.size()) {
			Scene r1 = new Scene(0, "常用", "life1", 1);
			Scene r2 = new Scene(1, "全关", "life1", 2);
			Scene r3 = new Scene(2, "外出", "life1", 3);
			Scene r4 = new Scene(3, "就餐", "life1", 4);
			Scene r5 = new Scene(4, "睡觉", "life1", 5);
			Scene r6 = new Scene(5, "预约", "life1", 6);
			Scene r7 = new Scene(6, "学习", "life1", 7);
			Scene r8 = new Scene(7, "娱乐", "life1", 8);
			Scene r9 = new Scene(8, "工作", "life1", 9);
			Scene r10 = new Scene(9, "冬季", "life1", 10);
			sceneList.add(r1);
			sceneList.add(r2);
			sceneList.add(r3);
			sceneList.add(r4);
			sceneList.add(r5);
			sceneList.add(r6);
			sceneList.add(r7);
			sceneList.add(r8);
			sceneList.add(r9);
			sceneList.add(r10);
			for (Scene scene : sceneList) {
				sceneDao.insertIntoSceneCtrl(scene);
			}
			progressDialog=new ProgressDialog(LifeSceneActivity.this);
			progressDialog.setTitle(R.string.progress_title);
			progressDialog.show();
			new Thread(){
				public void run() {
					for(int i=100;i<150;i++){
						Scene s=new Scene(i,i);
						sceneDao.insertIntoSceneContainer(s);
					}
					sceneContainList=sceneDao.findAllBySceneContainer();
					mHandler.sendEmptyMessage(0);
				};
			}.start();
			sceneList = sceneDao.findAllBySceneCtrl();
		}else{
			sceneContainList=sceneDao.findAllBySceneContainer();
		}
		int sceneSize = sceneList.size();
		for (int i = 0; i < sceneSize; i++) {
			listData.add(sceneList.get(i));
		}

	}

	@Override
	/**
	 * 添加新的GridView 全部数据的集合集lists.size()==countPage;
	 * 
	 * @param countPages
	 *            总共有多少个页面
	 * @return linear 新添加的页面
	 */
	public LinearLayout addGridView(final int countPages) {

		linear = new LinearLayout(LifeSceneActivity.this);
		gridView = new DragGridView(LifeSceneActivity.this);
		// 情景窗口标识为1
		gridView.whichPageMove = DragGridView.HANDLE_LIFE_SCENE;
		// 获取当前页面
		curentPage = countPages;

		dataAdapter = new LifeAdapter(this, lists.get(curentPage));
		// TODO

		gridView.setAdapter(dataAdapter);
		gridView.setNumColumns(NUM_COlUMNS);

		gridView.setHorizontalSpacing(DensityUtil.dip2px(this, 40));
		gridView.setVerticalSpacing(DensityUtil.dip2px(this, 8));

		// final String arrayAddItems = "aaaa";

//		gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view,
//					int position, long id) {
//				itemPosition = position;
//				Log.e("OnItem", "--->"
//						+ ((Configure.curentPage) * 8 + position));
//				Scene sce = sceneDao.findSceneByScePos((Configure.curentPage)
//						* 8 + position);
//				switch (sce.getScenePosition() - 1) {
//				case 0:
//					gridView.hideView();
//					setIntentSkip(SceneUsedActivity.class);
//					break;
//				case 1:
//					setIntentSkip(SceneNewActivity.class);
//					break;
//				default:
//					break;
//				}
				// 点击添加的item Home Furnishing
//				add("a", position);
//			}
//
//		});

		// 点击gridView时，改变背景的
		gridView.setSelector(R.drawable.life_select_bg);

		// TODO
		gridView.setPageListener(new DragGridView.G_PageListener() {
			@Override
			public void page(int cases, int page) {
				switch (cases) {
				case 0:// 滑动页面
					mScrollLayout.snapToScreen(page);
					setCurPage(page);
					new Handler().postDelayed(new Runnable() {
						@Override
						public void run() {
							Configure.isChangingPage = false;
						}
					}, 500);

					break;
				}
			}
		});
		// 进行数据交换保存到数据库
		gridView.tellChildUpdate(new SwitchPositionListener() {

			public void post(int dragPosition, int dropPosition) {
				switch (gridView.whichPageMove) {
				case DragGridView.HANDLE_LIFE_SCENE:
					updateTable(dragPosition, dropPosition);
					break;
				}
			}
		});

		// 跨屏交换位置
		gridView.setOnItemChangeListener(new DragGridView.G_ItemChangeListener() {
			@Override
			public void change(int from, int to, int count) {
				Scene fromScene = (Scene) lists.get(
						Configure.curentPage - count).get(from);
				if(to<lists.get(Configure.curentPage).size()){
					Scene toScene=(Scene)lists.get(Configure.curentPage).get(to);
				lists.get(Configure.curentPage - count).add(from,
						toScene);
				lists.get(Configure.curentPage - count).remove(from + 1);
				lists.get(Configure.curentPage).add(to, fromScene);
				lists.get(Configure.curentPage).remove(to + 1);
				Log.e(TAG, "---->"
						+ ((Configure.curentPage - count) * 8 + from) + "--->"
						+ ((Configure.curentPage) * 8 + to));
				updateTable((Configure.curentPage - count) * 8 + from,
						(Configure.curentPage) * 8 + to);
				((LifeAdapter) ((gridViews.get(Configure.curentPage))
						.getAdapter())).notifyDataSetChanged();
				Log.e("2222", "ddddd");
			}
				((LifeAdapter) ((gridViews.get(Configure.curentPage - count))
						.getAdapter())).notifyDataSetChanged();
			}

		});
		MainGroup.getWhichAdd(new SwitchAdd() {

			@Override
			public void addChoice(int flag) {
				if(isInitMain()){
				switch (flag) {
				case 1:
					Scene conScene=sceneContainList.get(0);
					sceneContainList.remove(0);
					Scene s=new Scene(conScene.getSceneId(),"Scene"+conScene.getScenePosition(),"life1",conScene.getScenePosition());
					sceneDao.deleteFromSceneContainer(conScene.getScenePosition());
					sceneDao.insertIntoSceneCtrl(s);
					addItem(curentPage, s);
					listData.add(s);
					
					break;
				}
			}
			}
		});

		gridView.setChildPopupOnClick(new ChildPopupOnClick() {

			@SuppressWarnings("deprecation")
			public void childPopupOnClick(View v, int which,final int item) {
				switch (which) {
				case DragGridView.HANDLE_LIFE_SCENE:
					itemScene=(Scene) lists.get(Configure.curentPage).get(item);
					Log.e("RoomControl","---->"+itemScene.getScenePosition());
					switch(v.getId()){
					case Configure.EDIT:
//						String fileName="life"+itemScene.getScenePosition();
//						File file=new File(CommentsUtil.FILE_PATH+fileName);
						Intent intent=new Intent(LifeSceneActivity.this,SceneUsedActivity.class);
						startActivity(intent);
						break;
					case Configure.DELETE:
						if(curentPage==Configure.curentPage){
							isFirstIn=false;
						}
						String delFileName="room"+itemScene.getScenePosition();
						File delFile=new File(CommentsUtil.FILE_PATH+delFileName);
						if(delFile.exists()){
							delFile.delete();
						}
						Scene conScene=new Scene(itemScene.getSceneId(),itemScene.getScenePosition());
						sceneDao.insertIntoSceneContainer(itemScene);
						sceneDao.deleteFromSceneByPos(itemScene.getScenePosition());
						sceneContainList.add(conScene);
						lists.get(Configure.curentPage).remove(item);
						listData.remove(0);
						adapterList.get(Configure.curentPage).notifyDataSetChanged();
						break;
					case Configure.RENAME:
						final EditText text=new EditText(LifeSceneActivity.this);
						text.setText(itemScene.getSceneName());
						alert=createBuilder(R.string.rename_title,text);
						alert.setButton("确定", new OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								String str=text.getText().toString().trim();
								itemScene.setSceneName(str);
								if(!"".equals(str)){
									//重命名更新数据库
									lists.get(Configure.curentPage).set(item, itemScene);
									sceneDao.updateFromSceneName(itemScene, str);
									adapterList.get(Configure.curentPage).notifyDataSetChanged();
									alert.dismiss();
								}else{
									Toast.makeText(LifeSceneActivity.this, "文件名不能为空", 2000).show();
								}
							}
							
						});
						alert.setButton2("取消", new OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								alert.dismiss();
							}
						});
						alert.show();
						break;
					case Configure.RECOLOR:
						View view= LayoutInflater.from(LifeSceneActivity.this).inflate(R.layout.alert_life_bg, null);
						alert=createBuilder(R.string.change_color, view);
						alert.setView(view);
						view.findViewById(R.id.color1).setOnClickListener(LifeSceneActivity.this);
						view.findViewById(R.id.color2).setOnClickListener(LifeSceneActivity.this);
						view.findViewById(R.id.color3).setOnClickListener(LifeSceneActivity.this);
						view.findViewById(R.id.color4).setOnClickListener(LifeSceneActivity.this);
						view.findViewById(R.id.color5).setOnClickListener(LifeSceneActivity.this);
						view.findViewById(R.id.color6).setOnClickListener(LifeSceneActivity.this);
						alert.setButton("取消", new OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								alert.dismiss();
							}
						});
						alert.show();
						break;
			
					default:
						break;
					}
					break;
					}
				}
			
		});
		gridViews.add(gridView);
		linear.addView(gridViews.get(countPages), linearParam);
		return linear;
	}

	/**
	 * 根据位置更新数据库
	 * 
	 * @param from
	 *            开始位置
	 * @param to
	 *            结束位置
	 */
	private void updateTable(int from, int to) {
		Scene s1 = sceneDao.findSceneByScePos(from);
		Scene s2 = sceneDao.findSceneByScePos(to);
		sceneDao.updateScene(s1, s2);
		sceneDao.updateScene(s2, s1);
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		if (keyCode == KeyEvent.KEYCODE_BACK) {
//			BaseApplication.getInstance().exit(); // 退出当前应用程序
//			return true;
//		}
//		if (keyCode == KeyEvent.KEYCODE_MENU) {
//			setIntentSkip(DialogSetting.class);
//		}
		return super.onKeyDown(keyCode, event);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()){
		case R.id.color1:
			updateSceneColor(sceneDao,"life1",itemScene);
			break;
		case R.id.color2:
			updateSceneColor(sceneDao,"life2",itemScene);
			break;
		case R.id.color3:
			updateSceneColor(sceneDao,"life3",itemScene);
			break;
		case R.id.color4:
			updateSceneColor(sceneDao,"life4",itemScene);
			break;
		case R.id.color5:
			updateSceneColor(sceneDao,"life5",itemScene);
			break;
		case R.id.color6:
			updateSceneColor(sceneDao,"life6",itemScene);
			break;
		}
		alert.dismiss();
		adapterList.get(Configure.curentPage).notifyDataSetChanged();
	}
	/**
	 * 更新情景颜色
	 * @param roomDao
	 * 
	 * @param str
	 * @param scene
	 */
	private  void updateSceneColor(SceneDao roomDao,String str,Scene scene){
		roomDao.updateSceneColor(scene, str);
		scene.setSceneBg(str);
	}

}
