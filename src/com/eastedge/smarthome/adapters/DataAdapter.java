package com.eastedge.smarthome.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class DataAdapter extends BaseAdapter {

	protected Context context;
	protected ArrayList<Object> listData;
	protected TextView mTextView;
	protected SharedPreferences sp;
	public static final String TAG="DataAdapter";
	public DataAdapter(Context context, ArrayList<Object> list) {
		this.context = context;
		this.listData = list;

	}

	@Override
	public int getCount() {
		return listData.size();
	}

	@Override
	public Object getItem(int position) {
		return listData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	/**
	 *交换位置
	 * @param startPosition
	 * @param endPosition
	 */
	public void exchange(int startPosition, int endPosition) {
		Object endObject = getItem(endPosition);
		Object startObject = getItem(startPosition);
		listData.add(startPosition,  endObject);
		listData.remove(startPosition + 1);
		listData.add(endPosition,  startObject);
		listData.remove(endPosition + 1);
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return convertView;
	}

}
