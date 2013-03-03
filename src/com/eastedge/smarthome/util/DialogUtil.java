package com.eastedge.smarthome.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;

/**
 * 消息框
 * 
 */
public class DialogUtil {

	public static void showInfoDialog(Context context, String message) {
		showInfoDialog(context, message, "取消", null);
	}

	public static void showInfoDialog(Context context, String message,
			String positiveStr, DialogInterface.OnClickListener onClickListener) {
		AlertDialog.Builder localBuilder = new AlertDialog.Builder(context);
		localBuilder.setMessage(message);
		if (onClickListener == null)
			onClickListener = new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {

				}
			};
		localBuilder.setPositiveButton(positiveStr, onClickListener);
		localBuilder.show();
	}

	public static void showInfoDialog(Context context, String message,
			String titleStr, String positiveStr, String negativeStr,
			DialogInterface.OnClickListener onClickListener) {

		AlertDialog.Builder localBuilder = new AlertDialog.Builder(context);
		localBuilder.setTitle(titleStr);
		localBuilder.setMessage(message);
		if (onClickListener == null)
			onClickListener = new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {

				}
			};
		localBuilder.setPositiveButton(positiveStr, onClickListener);
		localBuilder.setNegativeButton(negativeStr, onClickListener);
		localBuilder.show();
	}

	public static void showInfoDialog(Context context, String message,
			String titleStr, String positiveStr, String negativeStr,
			DialogInterface.OnClickListener onClickListenerPos,
			DialogInterface.OnClickListener onClickListenerNeg) {

		AlertDialog.Builder localBuilder = new AlertDialog.Builder(context);
		localBuilder.setTitle(titleStr);
		localBuilder.setMessage(message);
		if (onClickListenerNeg == null) {
			onClickListenerNeg = new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {

				}
			};
		}
		localBuilder.setPositiveButton(positiveStr, onClickListenerPos);
		localBuilder.setNegativeButton(negativeStr, onClickListenerNeg);
		localBuilder.show();
	}

	public static void showListInfoDialog(Context context, String[] message,
			String titleStr, DialogInterface.OnClickListener onClickListenerPos) {
		new AlertDialog.Builder(context).setTitle(titleStr)
				.setItems(message, onClickListenerPos).show();
	}

}
