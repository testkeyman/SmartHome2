package com.eastedge.smarthome.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.eastedge.smarthome.R;

public class MessageAdapter extends BaseAdapter {
	private ArrayList<String> list;
	private Context mContext;

	public MessageAdapter(Context context, ArrayList<String> list) {
		this.mContext = context;
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
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.message_listviw_item, null);
			holder.type = (TextView) convertView
					.findViewById(R.id.message_tv_type);
			holder.address = (TextView) convertView
					.findViewById(R.id.message_tv_address);
			holder.level = (TextView) convertView
					.findViewById(R.id.message_tv_level);
			holder.time = (TextView) convertView
					.findViewById(R.id.message_tv_time);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.type.setText("1、报警" + list.get(position));
		holder.address.setText("客厅门磁" + list.get(position));
		holder.level.setText("级别：高" + list.get(position));
		holder.time.setText("2012/11/11 11:11" + list.get(position));
		return convertView;
	}

	static class ViewHolder {
		TextView type, address, level, time;
	}

}
