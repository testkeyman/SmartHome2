package com.eastedge.smarthome.activitys;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.eastedge.smarthome.R;
import com.eastedge.smarthome.adapters.VersionAdapter;

/** 编辑版本设置 */
public class DialogSettingVersion extends Activity implements Callback,
		OnClickListener {

	private PopupWindow versionPopupWindow = null;
	private VersionAdapter versionAdapter = null;
	private ArrayList<String> datas = new ArrayList<String>();
	private RelativeLayout versionParent;
	private int pwidth;
	private EditText mEditTextVersion, mEditTextInputVersion;
	private ImageView mImageViewSelect;

	private Button mButtonConfirmSave, mButtonCancelSave;
	private ListView versionListView = null;
	private Handler handler;
	private boolean flag = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_setting_version);
		initDatas();

		mButtonConfirmSave = (Button) findViewById(R.id.dialog_btn_confirm_save);
		mButtonCancelSave = (Button) findViewById(R.id.dialog_btn_cancel_save);
		mEditTextInputVersion = (EditText) findViewById(R.id.dialog_et_input_version);
		mButtonConfirmSave.setOnClickListener(this);
		mButtonCancelSave.setOnClickListener(this);
	}

	protected void initDatas() {
		datas.add("版本1");
		datas.add("版本2");
		datas.add("版本3");
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.setting_ib_version:
			if (flag) {
				popupWindwShowing();
			}
			break;
		case R.id.dialog_btn_confirm_save: // 设置版本名称确认按钮
			Toast.makeText(getApplicationContext(), "保存成功", 0).show();
			this.finish();
			break;
		case R.id.dialog_btn_cancel_save:
			this.finish();
			break;

		}
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		while (!flag) {
			initWedget();
			flag = true;
		}

	}

	/**
	 * 初始化界面控件
	 */
	private void initWedget() {
		handler = new Handler(DialogSettingVersion.this);
		versionParent = (RelativeLayout) findViewById(R.id.setting_version_parent);
		mEditTextVersion = (EditText) findViewById(R.id.setting_et_version_name);
		mImageViewSelect = (ImageView) findViewById(R.id.setting_ib_version);
		int width = versionParent.getWidth();
		pwidth = width;
		mEditTextVersion.setText(datas.get(0));
		mImageViewSelect.setOnClickListener(this);

		initPopuWindow();

	}

	/**
	 * 初始化PopupWindow
	 */
	private void initPopuWindow() {

		// PopupWindow浮动下拉框布局
		View versionWindow = (View) this.getLayoutInflater().inflate(
				R.layout.setting_version_options, null);
		versionListView = (ListView) versionWindow
				.findViewById(R.id.setting_list_version);

		// 设置自定义Adapter
		versionAdapter = new VersionAdapter(this, handler, datas);
		versionListView.setAdapter(versionAdapter);

		versionPopupWindow = new PopupWindow(versionWindow, pwidth,
				LayoutParams.WRAP_CONTENT, true);

		versionPopupWindow.setOutsideTouchable(true);
		versionPopupWindow.setBackgroundDrawable(new BitmapDrawable());
	}

	/**
	 * 显示PopupWindow窗口
	 */
	public void popupWindwShowing() {
		versionPopupWindow.showAsDropDown(versionParent, 0, -3);
	}

	/**
	 * PopupWindow消失
	 */
	public void dismiss() {
		versionPopupWindow.dismiss();
	}

	/**
	 * 处理Hander消息
	 */
	@Override
	public boolean handleMessage(Message message) {
		Bundle data = message.getData();
		switch (message.what) {
		case 1:
			// 选中下拉项，下拉框消失
			int selIndex = data.getInt("selIndex");
			mEditTextVersion.setText(datas.get(selIndex));
			dismiss();
			break;
		}
		return false;
	}
}
