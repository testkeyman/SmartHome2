package com.eastedge.smarthome.activitys;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.eastedge.smarthome.R;

public class AccountSettingActivity extends BaseActivity implements android.view.View.OnClickListener {

	private AlertDialog mDialogInputVersion;
	private View dialogView;
	private Button mButtonBack, mButtonConfirm, mButtonSave, mButtonRecover,
			mButtonConfirmSave, mButtonCancelSave;
	private EditText mEditTextOldPwd, mEditTextNewPwd, mEditTextConfirmPwd,
			mEditTextVersion, mEditTextInputVersion;
	private CheckBox mCheckBoxEdit, mCheckBoxDelete, mCheckBoxAdd;

	// 下拉列表
	private ImageButton mImageButtonVersion;
	private ListView mListViewVersion;
	private ArrayList<String> versionItems;

	private LayoutInflater mInflater;
	private SharedPreferences sp;
	private Context mContext;
	private ViewPager mViewPager;
	private MyPagerAdapter mAdapter;

	// 滑动界面
	private List<View> mPagerViews;

	@Override
	protected void setView() {
		setContentView(R.layout.activity_account_settings);
		// Button
		mButtonBack = (Button) findViewById(R.id.main_btn_back);
		mButtonConfirm = (Button) findViewById(R.id.main_btn_confirm);
		mButtonBack.setOnClickListener(this);
		mButtonConfirm.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.main_btn_back:
			this.finish();
			break;
		case R.id.main_btn_confirm: //
			Toast.makeText(getApplicationContext(), "确认", 0).show();
			break;
		case R.id.settings_btn_save:
			inputVersionDialog();
			break;
		case R.id.settings_btn_recover:
			showFactoryResetDialog();
			break;
		case R.id.setting_ib_version:
			showListView();
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

	/**
	 * 初始化主体内容信息
	 */
	@Override
	protected void initData() {
		//
		mContext = this;
		mViewPager = (ViewPager) findViewById(R.id.viewpager);
		mAdapter = new MyPagerAdapter();
		mViewPager.setAdapter(mAdapter);

		// 加载viewPager视图
		mInflater = getLayoutInflater();
		mPagerViews = new ArrayList<View>();
//		mPagerViews.add(mInflater.inflate(R.layout.account_settings_authority,
//				null));
//		mPagerViews.add(mInflater.inflate(R.layout.account_settings_version,
//				null));
	}

	@Override
	public LinearLayout addGridView(int countPages) {
		return null;
	}

	/**
	 * 提示用户是否要恢复出厂设置
	 */
	protected void showFactoryResetDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				AccountSettingActivity.this);
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

	/**
	 * 输入保存版本号的对话框 保存成功或者失败都弹出土司提示用户
	 */
	protected void inputVersionDialog() {
		// TODO
		dialogView = View.inflate(this, R.layout.dialog_setting_version, null);
		mDialogInputVersion = new AlertDialog.Builder(this).create();
		mDialogInputVersion.setView(dialogView);
		mDialogInputVersion.show();

		mButtonConfirmSave = (Button) dialogView
				.findViewById(R.id.dialog_btn_confirm_save);
		mButtonCancelSave = (Button) dialogView
				.findViewById(R.id.dialog_btn_cancel_save);
		mEditTextInputVersion = (EditText) dialogView
				.findViewById(R.id.dialog_et_input_version);
		mButtonConfirmSave.setOnClickListener(this);
		mButtonCancelSave.setOnClickListener(this);
	}

	/**
	 * 是否显示listView,用于展示下拉菜单中的数据
	 */
	public void showListView() {
		if (mListViewVersion.getVisibility() == View.GONE) {
			mListViewVersion.setVisibility(View.VISIBLE);
			mListViewVersion.setDivider(null);
			mListViewVersion.setDividerHeight(0);
		} else {
			mListViewVersion.setVisibility(View.GONE);
		}
	}

	/**
	 * pager
	 * 
	 * @author wenshengping
	 * 
	 */
	class MyPagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return mPagerViews.size();
		}

		/**
		 * 必须将子页面的控件在在实例时设置监听事件才有效 在设置时还必须在对应的页面中 否则将找不到页面中的控件
		 */
		@Override
		public Object instantiateItem(View collection, int position) {
			((ViewPager) collection).addView(mPagerViews.get(position), 0);
			// 第一页数据
			if (position == 0) {
				// EditText
				setPwdAndPermission(collection);
			}
			// 第二页数据
			if (position == 1) {
				// TODO
				// 获取界面控件
				setVersion(collection);

				// 设置ListView的数据
				setListViewData();

			}

			return mPagerViews.get(position);
		}

		/**
		 * 设置下拉列表的数据，下拉列表以ListView形式展现
		 */
		protected void setListViewData() {
			// TODO
			versionItems = new ArrayList<String>();
			if (versionItems.size() == 0) {
				for (int i = 10000; i < 10003; i++) {
					versionItems.add(String.valueOf(i + 1));
				}
			}

//			mListViewVersion.setAdapter(new ArrayAdapter<String>(
//					AccountSettingActivity.this, R.layout.account_spinner_item,
//					R.id.setting_tv_version_name, versionItems));

			mListViewVersion.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					switch (position) {
					case 0:
						showListView();
						break;
					case 1:
						showListView();
						break;
					case 2:
						showListView();
						break;
					}
				}
			});
		}

		/**
		 * 设置版本界面
		 * 
		 * @param collection
		 */
		protected void setVersion(View collection) {
			mButtonSave = (Button) collection
					.findViewById(R.id.settings_btn_save);
			mButtonRecover = (Button) collection
					.findViewById(R.id.settings_btn_recover);
			mImageButtonVersion = (ImageButton) collection
					.findViewById(R.id.setting_ib_version);
			mEditTextVersion = (EditText) collection
					.findViewById(R.id.setting_et_version_name);
//			mListViewVersion = (ListView) collection
//					.findViewById(R.id.setting_lv_version);

			mButtonSave.setOnClickListener(AccountSettingActivity.this);
			mButtonRecover.setOnClickListener(AccountSettingActivity.this);
			mImageButtonVersion.setOnClickListener(AccountSettingActivity.this);
		}

		/**
		 * 设置密码和权限界面
		 * 
		 * @param collection
		 */
		protected void setPwdAndPermission(View collection) {
			mEditTextOldPwd = (EditText) collection
					.findViewById(R.id.settings_et_old_pwd);
			mEditTextNewPwd = (EditText) collection
					.findViewById(R.id.settings_et_new_pwd);
			mEditTextConfirmPwd = (EditText) collection
					.findViewById(R.id.settings_et_confirm_pwd);

			// CheckBox
			mCheckBoxEdit = (CheckBox) collection
					.findViewById(R.id.settings_cb_edit);
			mCheckBoxDelete = (CheckBox) collection
					.findViewById(R.id.settings_cb_delete);
			mCheckBoxAdd = (CheckBox) collection
					.findViewById(R.id.settings_cb_add);
		}

		@Override
		public void destroyItem(View collection, int position, Object view) {
			((ViewPager) collection).removeView(mPagerViews.get(position));
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == (object);
		}

		@Override
		public void finishUpdate(View arg0) {
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {
		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View arg0) {
		}

	}
}
