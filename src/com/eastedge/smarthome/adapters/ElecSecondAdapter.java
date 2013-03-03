package com.eastedge.smarthome.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eastedge.smarthome.R;
import com.eastedge.smarthome.domain.Customview;

public class ElecSecondAdapter extends DataAdapter {
	public ElecSecondAdapter(Context context, ArrayList<Object> list) {
		super(context, list);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		String roomName=(String) listData.get(position);
		convertView = LayoutInflater.from(context).inflate(
				R.layout.main_scrollview_item, null);
		mTextView = (TextView) convertView.findViewById(R.id.main_tv_page);
		mTextView.setBackgroundResource(R.color.blue);
		mTextView.setText(roomName);
		return convertView;
	}

}
