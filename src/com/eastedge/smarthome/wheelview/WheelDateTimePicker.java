package com.eastedge.smarthome.wheelview;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import android.view.View;

/**
 * 日期时间选择器
 * 
 */
public class WheelDateTimePicker {

	private View view;
	private WheelView wv_year;
	private WheelView wv_month;
	private WheelView wv_day;
	private WheelView wv_hour;
	private WheelView wv_minute;
	private WheelView wv_second;
	private int textSize = 30;
	private static int START_YEAR = 1990, END_YEAR = 2100;

	public WheelDateTimePicker(View view) {
		super();

		this.view = view;
		setView(view);
	}

	public View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = view;
	}

	public static int getSTART_YEAR() {
		return START_YEAR;
	}

	public static void setSTART_YEAR(int sTART_YEAR) {
		START_YEAR = sTART_YEAR;
	}

	public static int getEND_YEAR() {
		return END_YEAR;
	}

	public static void setEND_YEAR(int eND_YEAR) {
		END_YEAR = eND_YEAR;
	}

	/**
	 * @Description: TODO 弹出日期选择器
	 */
	public void initDatePicker(int layout_year, int layout_month, int layout_day) {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DATE);

		// 添加大小月月份并将其转换为list,方便之后的判断
		String[] months_big = { "1", "3", "5", "7", "8", "10", "12" };
		String[] months_little = { "4", "6", "9", "11" };

		final List<String> list_big = Arrays.asList(months_big);
		final List<String> list_little = Arrays.asList(months_little);

		// 年
		wv_year = (WheelView) view.findViewById(layout_year);
		wv_year.setAdapter(new NumericWheelAdapter(START_YEAR, END_YEAR));// 设置年的显示数量
		wv_year.setCyclic(true);// 可循环滚动
		wv_year.setLabel("年");// 添加文字
		wv_year.setCurrentItem(year - START_YEAR);// 初始化时显示的数量

		// 月
		wv_month = (WheelView) view.findViewById(layout_month);
		wv_month.setAdapter(new NumericWheelAdapter(1, 12));
		wv_month.setCyclic(true);
		wv_month.setLabel("月");
		wv_month.setCurrentItem(month);

		// 日
		wv_day = (WheelView) view.findViewById(layout_day);
		wv_day.setCyclic(true);
		// 判断大小月及是否闰年,用来确定天数
		if (list_big.contains(String.valueOf(month + 1))) {
			wv_day.setAdapter(new NumericWheelAdapter(1, 31));
		} else if (list_little.contains(String.valueOf(month + 1))) {
			wv_day.setAdapter(new NumericWheelAdapter(1, 30));
		} else {
			// 闰年
			if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)
				wv_day.setAdapter(new NumericWheelAdapter(1, 29));
			else
				wv_day.setAdapter(new NumericWheelAdapter(1, 28));
		}
		wv_day.setLabel("日");
		wv_day.setCurrentItem(day - 1);

		// 添加年监听
		OnWheelChangedListener wheelListener_year = new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				int year_num = newValue + START_YEAR;
				// 判断大小月及是否闰年,用来确定天数
				if (list_big
						.contains(String.valueOf(wv_month.getCurrentItem() + 1))) {
					wv_day.setAdapter(new NumericWheelAdapter(1, 31));
				} else if (list_little.contains(String.valueOf(wv_month
						.getCurrentItem() + 1))) {
					wv_day.setAdapter(new NumericWheelAdapter(1, 30));
				} else {
					if ((year_num % 4 == 0 && year_num % 100 != 0)
							|| year_num % 400 == 0)
						wv_day.setAdapter(new NumericWheelAdapter(1, 29));
					else
						wv_day.setAdapter(new NumericWheelAdapter(1, 28));
				}
			}
		};
		// 添加月监听
		OnWheelChangedListener wheelListener_month = new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				int month_num = newValue + 1;
				// 判断大小月及是否闰年,用来确定天数
				if (list_big.contains(String.valueOf(month_num))) {
					wv_day.setAdapter(new NumericWheelAdapter(1, 31));
				} else if (list_little.contains(String.valueOf(month_num))) {
					wv_day.setAdapter(new NumericWheelAdapter(1, 30));
				} else {
					if (((wv_year.getCurrentItem() + START_YEAR) % 4 == 0 && (wv_year
							.getCurrentItem() + START_YEAR) % 100 != 0)
							|| (wv_year.getCurrentItem() + START_YEAR) % 400 == 0)
						wv_day.setAdapter(new NumericWheelAdapter(1, 29));
					else
						wv_day.setAdapter(new NumericWheelAdapter(1, 28));
				}
			}
		};
		wv_year.addChangingListener(wheelListener_year);
		wv_month.addChangingListener(wheelListener_month);

		wv_day.TEXT_SIZE = textSize;
		wv_month.TEXT_SIZE = textSize;
		wv_year.TEXT_SIZE = textSize;

	}

	/**
	 * @Description: TODO 弹出时间选择器
	 */
	public void initTimePicker(int layout_hour, int layout_minutes,
			int layout_secend) {
		Calendar calendar = Calendar.getInstance();
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MONTH);
		int second = calendar.get(Calendar.DATE);

		// 时
		wv_hour = (WheelView) view.findViewById(layout_hour);
		wv_hour.setAdapter(new NumericWheelAdapter(00, 24));
		wv_hour.setCyclic(true);// 可循环滚动
		wv_hour.setLabel("小时");

		// 分
		wv_minute = (WheelView) view.findViewById(layout_minutes);
		wv_minute.setAdapter(new NumericWheelAdapter(00, 60));
		wv_minute.setCyclic(true);
		wv_minute.setLabel("分钟");

		// 秒
		wv_second = (WheelView) view.findViewById(layout_secend);
		wv_second.setAdapter(new NumericWheelAdapter(00, 60));
		wv_second.setCyclic(true);
		wv_second.setLabel("秒钟");

		wv_hour.TEXT_SIZE = textSize;
		wv_minute.TEXT_SIZE = textSize;
		wv_second.TEXT_SIZE = textSize;

	}

	public String getDate(int layout_year, int layout_month, int layout_day) {
		StringBuffer sb = new StringBuffer();
		sb.append((wv_year.getCurrentItem() + START_YEAR)).append("-")
				.append((wv_month.getCurrentItem() + 1)).append("-")
				.append((wv_day.getCurrentItem() + 1));
		return sb.toString();
	}

	public String getTime(int layout_hour, int layout_minute, int layout_second) {
		StringBuffer sb = new StringBuffer();
		sb.append((wv_hour.getCurrentItem())).append("-")
				.append((wv_minute.getCurrentItem())).append("-")
				.append((wv_second.getCurrentItem()));
		return sb.toString();
	}
}
