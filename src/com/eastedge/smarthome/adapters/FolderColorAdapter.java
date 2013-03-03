package com.eastedge.smarthome.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.eastedge.smarthome.R;

public class FolderColorAdapter extends BaseAdapter {
	private Context context;
	public FolderColorAdapter(Context context){
		this.context=context;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 6;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView=new ImageView(context);
		imageView.setLayoutParams(new LayoutParams(60,60));
		if(0==position){
			imageView.setImageResource(R.color.red);
		}
		if(1==position){
			imageView.setImageResource(R.color.green);
		}
		if(2==position){
			imageView.setImageResource(R.color.blue);
		}
		if(3==position){
			imageView.setImageResource(R.color.red);
		}
		if(4==position){
			imageView.setImageResource(R.color.green);
		}
		if(5==position){
			imageView.setImageResource(R.color.blue);
		}
		return imageView;
	}

}
