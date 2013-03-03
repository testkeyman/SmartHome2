package com.eastedge.smarthome.domain;

import com.eastedge.smarthome.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

public class NewElecBean extends Customview {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public View getBeanView(Context context) {
		View layoutInflater = LayoutInflater.from(context).inflate(
				R.layout.add_new_elec, null);
		
		return layoutInflater;
	}

}
