package com.eastedge.smarthome.activitys;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.View;
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

/** 设置版本界面 */
public class SettingVersionActivity extends BaseActivity implements Callback, android.view.View.OnClickListener {

	private PopupWindow versionPopupWindow = null;
	private VersionAdapter versionAdapter = null;
	private ArrayList<String> datas = new ArrayList<String>();
	private RelativeLayout versionParent;
	private int pwidth;
	private EditText mEditTextVersion, mEditTextInputVersion;
	private ImageView mImageViewSelect;

	private AlertDialog mDialogInputVersion;
	private View dialogView;
	private Button mButtonBack, mButtonConfirm, mButtonSave, mButtonRecover,
			mButtonConfirmSave, mButtonCancelSave;
	private ListView versionListView = null;
	private Handler handler;
	private boolean flag = false;

	@Override
	protected void setView() {
		setContentView(R.layout.settings_version);

		mButtonBack = (Button) findViewById(R.id.main_btn_back);
		mButtonConfirm = (Button) findViewById(R.id.main_btn_confirm);
		mButtonSave = (Button) findViewById(R.id.settings_btn_save);
		mButtonRecover = (Button) findViewById(R.id.settings_btn_recover);

		mButtonBack.setOnClickListener(this);
		mButtonConfirm.setOnClickListener(this);
		mButtonSave.setOnClickListener(this);
		mButtonRecover.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.setting_ib_version:
			if (flag) {
				popupWindwShowing();
			}
			break;
		case R.id.main_btn_back:
			this.finish();
			break;
		case R.id.main_btn_confirm: //
			Toast.makeText(getApplicationContext(), "确认", 0).show();
			break;
		case R.id.settings_btn_save: // 保存
			setIntentSkip(DialogSettingVersion.class);
			break;
		case R.id.settings_btn_recover:
			showFactoryResetDialog();
			break;
		case R.id.dialog_btn_confirm_save: // 设置版本名称确认按钮
			Toast.makeText(getApplicationContext(), "保存成功", 0).show();
			mDialogInputVersion.dismiss();
			break;
		case R.id.dialog_btn_cancel_save:
			mDialogInputVersion.dismiss();
			break;

		}
	}

	@Override
	protected void initData() {
		datas.add("版本1");
		datas.add("版本2");
		datas.add("版本3");
	}

	@Override
	public LinearLayout addGridView(int countPages) {
		return null;
	}

	/**
	 * 没有在onCreate方法中调用initWedget()，而是在onWindowFocusChanged方法中调用，
	 * 是因为initWedget()中需要获取PopupWindow浮动下拉框依附的组件宽度，在onCreate方法中是无法获取到该宽度的
	 */
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
		handler = new Handler(SettingVersionActivity.this);
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

	/**
	 * 提示用户是否要恢复出厂设置
	 */
	protected void showFactoryResetDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				SettingVersionActivity.this);
		builder.create();
		builder.setTitle("您确定要恢复出厂设置？")
				.setPositiveButton("确定", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Toast.makeText(getApplicationContext(), "恢复", 0).show();
					}
				}).setNegativeButton("取消", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		builder.show();
	}
}
