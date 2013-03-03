package com.eastedge.smarthome.activitys;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.eastedge.smarthome.R;
import com.eastedge.smarthome.adapters.MessageAdapter;

/** 消息提示 */
public class MessagePromptActivity extends BaseActivity implements
		OnItemClickListener, OnItemLongClickListener ,OnClickListener{
	private Button mButtonBack, mButtonSearch;
	private ListView mListView;
	private MessageAdapter messageAdapter;
	static final String[] mItems = { "标记已读", "删除消息" };

	@Override
	protected void setView() {
		setContentView(R.layout.activity_message_prompt);
//		mTextMessage = (TextView) findViewById(R.id.main_tv_message);
		mButtonBack = (Button) findViewById(R.id.main_btn_back);
		mButtonSearch = (Button) findViewById(R.id.main_btn_search);
		mListView = (ListView) findViewById(R.id.message_listview);

		mButtonBack.setOnClickListener(this);
		mButtonSearch.setOnClickListener(this);
		mListView.setOnItemClickListener(this);
		mListView.setOnItemLongClickListener(this);
//		mTextMessage.setBackgroundResource(R.drawable.pressed);
	}

	

	@Override
	protected void initData() {
	
		ArrayList<String> data = new ArrayList<String>();
		for (int i = 0; i < 20; i++) {
			data.add(i + "");
		}

		messageAdapter = new MessageAdapter(context, data);
		mListView.setAdapter(messageAdapter);
	}

	@Override
	public LinearLayout addGridView(int countPages) {
		return null;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Toast.makeText(getApplicationContext(), "消息" + position, 0).show();
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				MessagePromptActivity.this);
		builder.setItems(mItems, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// 点击后弹出窗口选择了第几项
				switch (which) {
				case 0:
					Toast.makeText(getApplicationContext(), "已读", 0).show();
					break;
				case 1:
					Toast.makeText(getApplicationContext(), "删除", 0).show();
					break;
				}

			}
		});
		builder.create().show();
		return false;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.main_btn_back:
			this.finish();
			break;
		case R.id.main_btn_search:
			setIntentSkip(DialogSearch.class);
			break;
		}
	}


}
