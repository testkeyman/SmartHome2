package com.eastedge.smarthome.adapters;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.eastedge.smarthome.R;
/**
 * 获取版本信息
 */
public class VersionAdapter extends BaseAdapter {

	private ArrayList<String> list = new ArrayList<String>();
	private Activity activity = null;
	private Handler handler;

	public VersionAdapter(Activity activity, Handler handler,
			ArrayList<String> list) {
		this.activity = activity;
		this.handler = handler;
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(activity).inflate(
					R.layout.setting_version_option_item, null);
			holder.textView = (TextView) convertView
					.findViewById(R.id.item_text);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.textView.setText(list.get(position));
		holder.textView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Message msg = new Message();
				Bundle data = new Bundle();
				data.putInt("selIndex", position);
				msg.setData(data);
				msg.what = 1;
				handler.sendMessage(msg);
			}
		});

		return convertView;
	}

}

class ViewHolder {
	TextView textView;
}
