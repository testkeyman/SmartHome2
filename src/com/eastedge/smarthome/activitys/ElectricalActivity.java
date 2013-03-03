package com.eastedge.smarthome.activitys;

import java.io.File;
import java.util.ArrayList;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.eastedge.smarthome.R;
import com.eastedge.smarthome.activitygroup.MainGroup;
import com.eastedge.smarthome.activitygroup.MainGroup.SwitchAdd;
import com.eastedge.smarthome.adapters.DataAdapter;
import com.eastedge.smarthome.adapters.RoomSecondAdapter;
import com.eastedge.smarthome.domain.Customview;
import com.eastedge.smarthome.domain.Ele;
import com.eastedge.smarthome.dragview.Configure;
import com.eastedge.smarthome.dragview.DragGridView;
import com.eastedge.smarthome.dragview.DragGridView.ChildPopupOnClick;
import com.eastedge.smarthome.dragview.DragGridView.SwitchPositionListener;
import com.eastedge.smarthome.util.CommentsUtil;
import com.eastedge.smarthome.util.DensityUtil;

/** 电器分类 */
public class ElectricalActivity extends BaseActivity{
	private String[] fileNames = null;
	private File file;
	private ArrayList<Customview> listViews = null;
	private File[] files = null;
	private String deviceName;
	private ArrayList<String> nameLists=new ArrayList<String>();
	protected boolean isInitMain() {
		return true;
	}

	@Override
	protected void setView() {
		setContentView(R.layout.activity_electrical);
		file = new File(CommentsUtil.FILE_PATH);
		
		if (file.exists() && file.isDirectory()) {
			fileNames = file.list();
			files = file.listFiles();
		}
//		01-21 10:25:53.495: I/dalvikvm(662): Wrote stack traces to '/data/anr/traces.txt'

	}

	@Override
	protected void initData() {
		if(null==files){
			files=new File[0];
		}
		int filesCount = files.length;
		int viewsCount = 0;
		for (int i = 0; i < filesCount; i++) {
			listViews = CommentsUtil.readCustomview(files[i]);
			viewsCount = listViews.size();
			for (int j = 0; j < viewsCount; j++) {
				Customview customView = listViews.get(j);
				deviceName=customView.getDeviceName();
				Log.e("ElecSecond",
						"device_name---->" + customView.getDeviceName());
				if(!nameLists.contains(deviceName)){
					listData.add(customView);
					nameLists.add(deviceName);
				}
				
			}
		}
	}

	public LinearLayout addGridView(int countPages) {
		linear = new LinearLayout(ElectricalActivity.this);
		gridView = new DragGridView(ElectricalActivity.this);
		// 家电控制窗口标识为3
		gridView.whichPageMove = DragGridView.HANDLE_ELECTRIC_CTR;
		// 获取当前页面
		curentPage = countPages;
		dataAdapter = new RoomSecondAdapter(this, lists.get(curentPage));
		gridView.setAdapter(dataAdapter);
		gridView.setNumColumns(NUM_COlUMNS);
		gridView.setHorizontalSpacing(DensityUtil.dip2px(this, 40));
		gridView.setVerticalSpacing(DensityUtil.dip2px(this, 8));
		// 点击gridView时，改变背景的
		// gridView.setSelector(R.drawable.grid_light);

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
		gridView.tellChildUpdate(new SwitchPositionListener() {

			public void post(int dragPosition, int dropPosition) {
				switch (gridView.whichPageMove) {
				case DragGridView.HANDLE_ELECTRIC_CTR:
					Log.e("Electricpost", "--->" + dragPosition + "---->"
							+ dropPosition);
					break;
				}
			}
		});
		MainGroup.getWhichAdd(new SwitchAdd() {

			@Override
			public void addChoice(int flag) {
				switch (flag) {
				case 1:
					break;
				}
			}
		});
		gridView.setSelector(R.drawable.folder_select_bg);
		// 跨屏拖动
		gridView.setOnItemChangeListener(new DragGridView.G_ItemChangeListener() {
			@Override
			public void change(int from, int to, int count) {
				if(to<lists.get(Configure.curentPage).size()){
				Customview toString = (Customview) lists.get(Configure.curentPage - count)
						.get(from);

				lists.get(Configure.curentPage - count).add(from,
						(Ele) lists.get(Configure.curentPage).get(to));
				lists.get(Configure.curentPage - count).remove(from + 1);
				lists.get(Configure.curentPage).add(to, toString);
				lists.get(Configure.curentPage).remove(to + 1);
				((DataAdapter) ((gridViews.get(Configure.curentPage))
						.getAdapter())).notifyDataSetChanged();
				}
				((DataAdapter) ((gridViews.get(Configure.curentPage - count))
						.getAdapter())).notifyDataSetChanged();
			}
		});
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Customview custom = (Customview) lists.get(Configure.curentPage).get(position);
				Intent intent = new Intent(ElectricalActivity.this,
				ElectricSecondActivity.class);
				intent.putExtra("device_name", custom.getDeviceName());
				startActivity(intent);
			}
		});
		gridView.setChildPopupOnClick(new ChildPopupOnClick() {

			public void childPopupOnClick(View v, int which, int item) {
				switch (which) {
				case DragGridView.HANDLE_ELECTRIC_CTR:
					Customview custom = (Customview) lists.get(Configure.curentPage).get(item);
					switch (v.getId()) {
					case Configure.EDIT:
						Intent intent = new Intent(ElectricalActivity.this,
								ElectricSecondActivity.class);
						intent.putExtra("device_name", custom.getDeviceName());
						startActivity(intent);
						break;
					case Configure.DELETE:
						v.setVisibility(View.GONE);
						Toast.makeText(ElectricalActivity.this, "2", 0).show();
						break;
					case Configure.RENAME:
						v.setVisibility(View.GONE);
						Toast.makeText(ElectricalActivity.this, "3", 0).show();
						break;
					case Configure.RECOLOR:
						Toast.makeText(ElectricalActivity.this, "4", 0).show();
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

//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		if (keyCode == KeyEvent.KEYCODE_BACK) {
//			BaseApplication.getInstance().exit(); // 退出当前应用程序
//			return true;
//		}
//		if (keyCode == KeyEvent.KEYCODE_MENU) {
//			setIntentSkip(DialogSetting.class);
//		}
//		return super.onKeyDown(keyCode, event);
//	}
	}


