package com.eastedge.smarthome.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eastedge.smarthome.R;
import com.eastedge.smarthome.domain.Ele;
import com.eastedge.smarthome.domain.Room;

public class ElectricalAdapter extends DataAdapter {

	public ElectricalAdapter(Context context, ArrayList<Object> list) {
		super(context, list);
		this.context = context;
		this.listData = list;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = LayoutInflater.from(context).inflate(R.layout.main_scrollview_item, null);
		mTextView = (TextView) convertView.findViewById(R.id.main_tv_page);
		Ele ele=(Ele) listData.get(position);
		mTextView.setText(ele.getEleClassifyName());
		if(("room_control1").equals(ele.getEleClassifyBg())){
			mTextView.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.room_control1_bg));
		} else if(("room_control2").equals(ele.getEleClassifyBg())){
			mTextView.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.room_control2_bg));
		}
		else if(("room_control3").equals(ele.getEleClassifyBg())){
			mTextView.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.room_control3_bg));
		}else if(("room_control4").equals(ele.getEleClassifyBg())){
			mTextView.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.room_control4_bg));
		}
		else if(("room_control5").equals(ele.getEleClassifyBg())){
			mTextView.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.room_control5_bg));
		}
		else if(("room_control6").equals(ele.getEleClassifyBg())){
			mTextView.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.room_control6_bg));
		}
		return convertView;
	}
}
