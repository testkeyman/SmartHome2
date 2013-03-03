package com.eastedge.smarthome.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eastedge.smarthome.R;
import com.eastedge.smarthome.domain.Customview;

public class RoomSecondAdapter extends DataAdapter {
	public RoomSecondAdapter(Context context, ArrayList<Object> list) {
		super(context, list);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Customview customView=(Customview) listData.get(position);
		convertView = LayoutInflater.from(context).inflate(
				R.layout.main_scrollview_item, null);
		mTextView = (TextView) convertView.findViewById(R.id.main_tv_page);
		mTextView.setText(customView.getDeviceName());
		mTextView.setBackgroundResource(customView.getBgId());
		return convertView;
	}

}
