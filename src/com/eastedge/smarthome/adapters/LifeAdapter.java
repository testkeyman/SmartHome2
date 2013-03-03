package com.eastedge.smarthome.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eastedge.smarthome.R;
import com.eastedge.smarthome.domain.Scene;

public class LifeAdapter extends DataAdapter {

	public LifeAdapter(Context context, ArrayList<Object> list) {
		super(context, list);
		this.context = context;
		this.listData = list;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = LayoutInflater.from(context).inflate(R.layout.main_scrollview_item, null);
		mTextView = (TextView) convertView.findViewById(R.id.main_tv_page);
		
		Scene scene=(Scene) listData.get(position);
			mTextView.setText(scene.getSceneName());
			if(("life1").equals(scene.getSceneBg())){
				mTextView.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.life1_bg));
			} else if(("life2").equals(scene.getSceneBg())){
				mTextView.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.life2_bg));
			}
			else if(("life3").equals(scene.getSceneBg())){
				mTextView.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.life3_bg));
			}else if(("life4").equals(scene.getSceneBg())){
				mTextView.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.life4_bg));
			}
			else if(("life5").equals(scene.getSceneBg())){
				mTextView.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.life5_bg));
			}
			else if(("life6").equals(scene.getSceneBg())){
				mTextView.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.life6_bg));
			}
		
		return convertView;
	}

}
