package com.eastedge.smarthome.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.eastedge.smarthome.R;

public class SceneUsedAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<String> list = new ArrayList<String>();

	public SceneUsedAdapter(Context context, ArrayList<String> list) {
		super();
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
	public void exchange(int startPosition, int endPosition) {
		Object endObject = getItem(endPosition);
		Object startObject = getItem(startPosition);
		list.add(startPosition, (String) endObject);
		list.remove(startPosition + 1);
		list.add(endPosition, (String) startObject);
		list.remove(endPosition + 1);

		// setExchangePosition();
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.scene_listviw_item, null);
			holder = new ViewHolder();
			holder.address = (TextView) convertView
					.findViewById(R.id.scene_tv_address);
			holder.device = (TextView) convertView
					.findViewById(R.id.scene_tv_device);
			holder.delay = (TextView) convertView.findViewById(R.id.scene_tv_delay);
			holder.statu = (TextView) convertView.findViewById(R.id.scene_tv_statu);
//			holder.switchText = (ToggleButton) convertView
//					.findViewById(R.id.scene_switch);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.address.setText("客厅" + list.get(position));
		holder.device.setText("门磁" + list.get(position));
		//holder.switchText.isChecked();
		holder.delay.setText("延时 10 秒" + list.get(position));
		holder.statu.setText("无状态" + list.get(position));

		return convertView;
	}


	static class ViewHolder {
		// 地址，设备，时间属性，设备状态
		TextView address, device, delay, statu;
		ToggleButton switchText;
	}

}
