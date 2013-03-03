package com.eastedge.smarthome.activitys;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.eastedge.smarthome.R;

public class DialogSetting extends Activity implements OnClickListener {
	// 设置以及输入密码Dialog
	protected View passwordView;
	protected AlertDialog mDialogInputPwd;
	protected Button mButtondialogConfirm, mButtondialogCancel;
	protected TextView mTextSetAuthority, mTextSetVersion, mTextSetMessage;
	/** 用户标识跳转的界面 */
	private static String command = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		initView();
	}

	protected void initView() {
		setContentView(R.layout.dialog_settings);
		mTextSetAuthority = (TextView) findViewById(R.id.dialog_tv_set_authority);
		mTextSetVersion = (TextView) findViewById(R.id.dialog_tv_set_version);
		mTextSetMessage = (TextView) findViewById(R.id.dialog_tv_set_message);
		mTextSetAuthority.setOnClickListener(this);
		mTextSetVersion.setOnClickListener(this);
		mTextSetMessage.setOnClickListener(this);

	}

	protected void setIntentSkip(Class<?> clazz) {
		Intent intent = new Intent();
		intent.setClass(getApplicationContext(), clazz);
		startActivity(intent);
		mDialogInputPwd.dismiss();
		this.finish();

	}

	/**
	 * 输入密码对话框.
	 */
	protected void showDialogInputPwd() {
		passwordView = View.inflate(this, R.layout.dialog_input_pwd, null);
		mDialogInputPwd = new AlertDialog.Builder(this).create();
		mDialogInputPwd.setView(passwordView);
		mDialogInputPwd.show();

		mButtondialogConfirm = (Button) passwordView
				.findViewById(R.id.dialog_btn_confirm);
		mButtondialogCancel = (Button) passwordView
				.findViewById(R.id.dialog_btn_cancel);
		mButtondialogConfirm.setOnClickListener(this);
		mButtondialogCancel.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.dialog_tv_set_authority:
			command = "authority";
			showDialogInputPwd();
			break;
		case R.id.dialog_tv_set_version:
			command = "version";
			showDialogInputPwd();
			break;
		case R.id.dialog_tv_set_message:
			command = "message";
			showDialogInputPwd();
			break;
		case R.id.dialog_btn_confirm:
			if (command == "authority") {
				setIntentSkip(SettingAuthorityActivity.class);
			} else if (command == "version") {
				setIntentSkip(SettingVersionActivity.class);
			} else if (command == "message") {
				setIntentSkip(SettingMessageActivity.class);
			}
			break;
		case R.id.dialog_btn_cancel:
			mDialogInputPwd.dismiss();
			this.finish();
			break;
		}

	}
}
