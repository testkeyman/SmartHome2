package com.eastedge.smarthome.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.eastedge.smarthome.R;

public class PopupAdapter extends BaseAdapter {
	private Context ct;
	private ArrayList<String> list;
	private String[] items;

	public PopupAdapter(Context ct, String[] items) {
		this.ct = ct;
		this.items = items;

	}

	public int getCount() {
		return items.length;
	}

	public Object getItem(int position) {
		return items[position];
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater factory = LayoutInflater.from(ct);
		View v = (View) factory.inflate(R.layout.popup_grid_item, null);
		TextView tv = (TextView) v.findViewById(R.id.text);
		tv.setText(items[position]);
		return v;
	}
}
