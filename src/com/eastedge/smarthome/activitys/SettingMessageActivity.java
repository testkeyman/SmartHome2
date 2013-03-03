package com.eastedge.smarthome.activitys;

import com.eastedge.smarthome.R;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

/** 设置消息级别 */
public class SettingMessageActivity extends BaseActivity implements android.view.View.OnClickListener {
	private Button mButtonBack, mButtonConfirm;

	@Override
	protected void setView() {
		setContentView(R.layout.settings_message);

		mButtonBack = (Button) findViewById(R.id.main_btn_back);
		mButtonConfirm = (Button) findViewById(R.id.main_btn_confirm);

		mButtonBack.setOnClickListener(this);
		mButtonConfirm.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.main_btn_back:
			showModifyDialog();
			this.finish();
			break;
		case R.id.main_btn_confirm: //
			Toast.makeText(getApplicationContext(), "确认", 0).show();
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
				SettingMessageActivity.this);
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
