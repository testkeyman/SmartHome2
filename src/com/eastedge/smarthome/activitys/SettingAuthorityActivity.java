package com.eastedge.smarthome.activitys;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.eastedge.smarthome.R;

/** 设置权限界面 */
public class SettingAuthorityActivity extends BaseActivity implements android.view.View.OnClickListener {

	private EditText mEditTextOldPwd, mEditTextNewPwd, mEditTextConfirmPwd;
	private CheckBox mCheckBoxEdit, mCheckBoxDelete, mCheckBoxAdd;
	private Button mButtonBack, mButtonConfirm;

	@Override
	protected void setView() {
		setContentView(R.layout.settings_authority);
		// Button
		mButtonBack = (Button) findViewById(R.id.main_btn_back);
		mButtonConfirm = (Button) findViewById(R.id.main_btn_confirm);

		// EditText
		mEditTextOldPwd = (EditText) findViewById(R.id.settings_et_old_pwd);
		mEditTextNewPwd = (EditText) findViewById(R.id.settings_et_new_pwd);
		mEditTextConfirmPwd = (EditText) findViewById(R.id.settings_et_confirm_pwd);

		// CheckBox
		mCheckBoxEdit = (CheckBox) findViewById(R.id.settings_cb_edit);
		mCheckBoxDelete = (CheckBox) findViewById(R.id.settings_cb_delete);
		mCheckBoxAdd = (CheckBox) findViewById(R.id.settings_cb_add);

		mButtonBack.setOnClickListener(this);
		mButtonConfirm.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.main_btn_back:
			showModifyDialog();
			finish();
			break;
		case R.id.main_btn_confirm:
			Toast.makeText(getApplicationContext(), "确定", 0).show();
			break;

		default:
			break;
		}
	}

	@Override
	protected void initData() {

	}

	@Override
	public LinearLayout addGridView(int countPages) {
		return null;
	}

	/**
	 * 提示用户是否保存修改设置
	 */
	protected void showModifyDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				SettingAuthorityActivity.this);
		builder.create();
		builder.setTitle("是否保存当前设置？")
				.setPositiveButton("确定", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Toast.makeText(getApplicationContext(), "保存成功", 0)
								.show();
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
