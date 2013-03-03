package com.eastedge.smarthome.activitys;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.eastedge.smarthome.R;
import com.eastedge.smarthome.adapters.PopupAdapter;
import com.eastedge.smarthome.adapters.SceneUsedAdapter;
import com.eastedge.smarthome.domain.Customview;
import com.eastedge.smarthome.dragview.DragListView;
import com.eastedge.smarthome.util.CommentsUtil;

/** 具体的情景界面 */
public class SceneUsedActivity extends BaseActivity implements
		OnItemLongClickListener, OnItemClickListener, OnLongClickListener,
		OnClickListener {

	// 不同的事件显示不同的 popupWindow上的数据, 整条长按，添加延时设置，修改延时设置，删除状态
	private static String[] longClickItem = { "添加状态", "添加属性", "删除", "剪切", "复制" };
	private static String[] addDelay = { "延时", "定时", "开关" };
	private static String[] modifyDelay = { "删除", "编辑" };
	private static String[] deleteStatu = { "删除" };

	// 情景名称，智能联动，返回，添加按钮
	private Button mButtonName, mButtonAuto, mButtonBack, mButtonAdd;
	// 粘贴，设备名称，延时设置，状态
	protected TextView mTextPaste, mTextDevice, mTextDelay, mTextStatu;
	private LinearLayout mLinearLayout;
	private static ArrayList<String> list;
	private static DragListView dragListView;
	private static SceneUsedAdapter sceneAdapter;
	// popupWindow
	private GridView mGridView;
	private PopupWindow mPopupWindow;
	private View popupLayout;
	private PopupAdapter popupAdapter;

	// 粘贴
	protected View pasteView;
	protected AlertDialog mDialogPaste;

	@Override
	protected void setView() {
		setContentView(R.layout.activity_scene_used);

		mButtonName = (Button) findViewById(R.id.scene_btn_name);
		mButtonAuto = (Button) findViewById(R.id.scene_btn_auto);
		mButtonBack = (Button) findViewById(R.id.scene_btn_back);
		mButtonAdd = (Button) findViewById(R.id.scene_btn_add);
		mLinearLayout = (LinearLayout) findViewById(R.id.scene_ll_drag);

		dragListView = (DragListView) findViewById(R.id.scene_listview);
		mTextDevice = (TextView) findViewById(R.id.scene_tv_device);
		mTextDelay = (TextView) findViewById(R.id.scene_tv_delay);
		mTextStatu = (TextView) findViewById(R.id.scene_tv_statu);

		mButtonName.setOnClickListener(this);
		mButtonAuto.setOnClickListener(this);
		mButtonBack.setOnClickListener(this);
		mButtonAdd.setOnClickListener(this);
		mLinearLayout.setOnLongClickListener(this);

		// mTextDevice.setOnClickListener(this);
		// mTextDelay.setOnClickListener(this);
		// mTextStatu.setOnClickListener(this);
		dragListView.setOnItemLongClickListener(this);
		dragListView.setOnItemClickListener(this);
		initPopup(5);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.dialog_tv_paste:
			Toast.makeText(this, "粘贴", 0).show();
			mDialogPaste.dismiss();
			break;
		case R.id.scene_btn_back:
			finish();

			break;
		case R.id.scene_btn_add:// 添加按钮
			Intent intent = new Intent(this, AddNewDeviceActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}

	public static Handler MyHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			/**
			 * 0：取消添加
			 * 
			 * 其他：设备id 添加到原有集合中，并保存
			 */
			if (msg.what != 0) {
				list.add(msg.what + "");
				File file = new File(CommentsUtil.FILE_PATH
						+ "SceneUsedActivity");
				if (!file.exists()) {
					file.getParentFile().mkdirs();
					try {
						file.createNewFile();
					} catch (IOException e) {
						e.printStackTrace();
					}

					writeCustomview(file, list);
					Toast.makeText(context, "添加成功", 0).show();
				}
				list = null;
				list = readCustomview(file);
				sceneAdapter = new SceneUsedAdapter(context, list);
				dragListView.setAdapter(sceneAdapter);
			}
			super.handleMessage(msg);
		}
	};

	/**
	 * 写入对象
	 * 
	 * @param aid
	 *            文件夹名称
	 * @param list
	 *            对象列表
	 */
	public static void writeCustomview(File file, ArrayList<String> list) {
		try {
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
	 * 
	 * @param aid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static ArrayList<String> readCustomview(File f) {
		ObjectInputStream in = null;
		try {
			// File f = new File(FILE_PATH + aid);

			if (f.exists()) {
				in = new ObjectInputStream(new FileInputStream(f));
				ArrayList<String> list = (ArrayList<String>) in.readObject();
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

	@Override
	protected void initData() {
		// list = new ArrayList<String>();
		// for (int i = 0; i < 3; i++) {
		// list.add(i + "");
		// }
		File file = new File(CommentsUtil.FILE_PATH + "SceneUsedActivity");
		list = readCustomview(file);
		sceneAdapter = new SceneUsedAdapter(context, list);
		dragListView.setAdapter(sceneAdapter);
	}

	@Override
	public LinearLayout addGridView(int countPages) {
		return null;
	}

	/* 初始化一个弹窗 */
	private void initPopWindow(View view) {
		mPopupWindow = new PopupWindow(popupLayout, LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		ColorDrawable dw = new ColorDrawable(0x00);
		mPopupWindow.setBackgroundDrawable(dw);
		mPopupWindow.setFocusable(true);
		mPopupWindow.setOutsideTouchable(true);
		mPopupWindow.showAsDropDown(view);
		Log.e("show", "-----show");
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		initPopWindow(view);
		return false;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

	}

	/** 界面长按 */
	@Override
	public boolean onLongClick(View v) {
		if (v.getId() == R.id.scene_ll_drag) {
			showPasteView();
		}
		return false;
	}

	/** 粘贴界面 */
	protected void showPasteView() {
		pasteView = View.inflate(this, R.layout.dialog_paste, null);
		mDialogPaste = new AlertDialog.Builder(this).create();
		mDialogPaste.setView(pasteView);
		mDialogPaste.show();
		mTextPaste = (TextView) pasteView.findViewById(R.id.dialog_tv_paste);
		mTextPaste.setOnClickListener(this);
	}

	/** 设置popWindow中的数据 */
	protected void initPopup(int columns) {
		popupLayout = View.inflate(this, R.layout.scene_popup_window, null);
		mGridView = (GridView) popupLayout.findViewById(R.id.scene_grid_pop);
		mGridView.setNumColumns(columns);
		if (columns == 5) {
			columns5();
		} else if (columns == 3) {
			columns3();
		} else if (columns == 2) {
			columns2();
		} else if (columns == 1) {
			columns1();
		}

	}

	protected void columns1() {
		popupAdapter = new PopupAdapter(this, deleteStatu);
		mGridView.setAdapter(popupAdapter);
		mGridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				switch (position) {
				case 0:
					list.remove(position);
					sceneAdapter.notifyDataSetChanged();
					break;
				}
			}
		});
	}

	protected void columns2() {
		popupAdapter = new PopupAdapter(this, modifyDelay);
		mGridView.setAdapter(popupAdapter);
		mGridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				switch (position) {
				case 0:

					break;
				case 1:

					break;

				}
			}
		});
	}

	protected void columns3() {
		popupAdapter = new PopupAdapter(this, addDelay);
		mGridView.setAdapter(popupAdapter);
		mGridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				switch (position) {
				case 0:

					break;
				case 1:

					break;
				case 2:

					break;

				}
			}
		});
	}

	protected void columns5() {
		popupAdapter = new PopupAdapter(this, longClickItem);
		mGridView.setAdapter(popupAdapter);
		mGridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				switch (position) {
				case 0:

					break;
				case 1:

					break;
				case 2:

					break;
				case 3:

					break;
				case 4:

					break;
				}
			}
		});
	}

}
