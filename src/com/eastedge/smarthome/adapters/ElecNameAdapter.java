package com.eastedge.smarthome.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ElecNameAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<String>list;
	public ElecNameAdapter(Context context,ArrayList<String>list){
		this.context=context;
		this.list=list;
	}
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView text=new TextView(context);
		text.setTextColor(Color.BLACK);
		text.setTextSize(20);
		text.setText(list.get(position));
		return text;
	}

}
