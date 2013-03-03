package com.eastedge.smarthome.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.eastedge.smarthome.R;
import com.eastedge.smarthome.domain.Customview;
import com.eastedge.smarthome.domain.Lightbean;
import com.eastedge.smarthome.domain.WarningDeviceBean;
import com.eastedge.smarthome.service.BackgroundService;
import com.eastedge.smarthome.util.CRC16;
import com.eastedge.smarthome.util.Const;

public class ElectricControlAdapter extends DataAdapter {
	public ElectricControlAdapter(Context context, ArrayList<Object> list) {
		super(context, list);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final Customview customview = (Customview) listData.get(position);
		convertView = customview.getBeanView(context);
		return convertView;
	}
}
