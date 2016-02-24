/******************************************************************************
 * Copyright (C) 2014 ShenZhen HeShiDai Technology Co.,Ltd
 * All Rights Reserved.
 *****************************************************************************/
package com.heshidai.cdzmerchant.utils;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 通用日期工具类
 * @author xiao di fa
 */
public class DateUtil {

	/**
	 * 获取当前日期时间
	 * @return
	 */
	public static String getCurrentDate() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
		return format.format(new Date());
	}

	/**
	 * 获取当前自定义格式日期
	 * @return
	 */
	public static String getCurrentDate(String formatter) {
		SimpleDateFormat format = new SimpleDateFormat(formatter, Locale.getDefault());
		return format.format(new Date());
	}

	/**根据旧的日期格式获取新的日期格式时间
	 * @param oldFormat
	 * @param newFormat
	 * @param time
	 * @return
	 */
	public static String getTimeByTime(String oldFormat, String newFormat, String time) {
		String temp = time;
		if(!TextUtils.isEmpty(oldFormat) && !TextUtils.isEmpty(newFormat) && !TextUtils.isEmpty(time)) {
			SimpleDateFormat f1 = new SimpleDateFormat(oldFormat, Locale.getDefault());
			SimpleDateFormat f2 = new SimpleDateFormat(newFormat, Locale.getDefault());
			try {
				temp = f2.format(f1.parse(time));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return temp;
	}

	/**判断是否是今天,并且获取HH:mm
	 * @param time
	 * @return
	 */
	public static String getTimeByTime(String time) {
		if(time.contains(getCurrentDate("yyyy-MM-dd"))) {//如果是今天
			return time.substring(time.indexOf(" "), time.lastIndexOf(":"));
		}
		return getTimeByTime("yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss", time);
	}

	/**
	 * 根据Long型获取时间
	 * @param formatter
	 * @param time
	 * @return
	 */
	public static String getTimeByTime(String formatter, long time) {
		if(!TextUtils.isEmpty(formatter) && time > 0l) {
			SimpleDateFormat format = new SimpleDateFormat(formatter, Locale.getDefault());
			return format.format(time);
		}
		return "";
	}

	/**
	 * 获取时间的相差数
	 * @param beginTime
	 * @param endTime
	 * @param type 类型常量
	 * 0 - 秒
	 * 1 - 分钟
	 * 2 - 小时
	 * 3 - 天
	 * @return
	 */
	public static int getDifferenceTime(String beginTime, String endTime, int type) {
		try {
			SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
			long startT = inputFormat.parse(beginTime).getTime();
			long endT = inputFormat.parse(endTime).getTime();

			long second = (endT - startT) / (1000); // 共计秒数
			int mint = (int) second / 60; // 共计分钟数
			int hor = (int) second / 3600; // 共计小时数
			int day = hor / 24; // 共计天数

			switch (type) {
				case 0:
					return (int) second;
				case 1:
					return mint;
				case 2:
					return hor;
				case 3:
					return day;
				default:
					return 0;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return -1;
	}

	/**两个时间相差距离多少天多少小时多少分多少秒
	 * @param beginTime 格式：1990-01-01 12:00:00
	 * @param endTime 格式：2009-01-01 12:00:00
	 * @return long[] 返回值为：{天, 时, 分, 秒}
	 */
	public static long[] getDistanceTimes(String beginTime, String endTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
		Date one;
		Date two;
		long day = 0;
		long hour = 0;
		long min = 0;
		long sec = 0;
		try {
			one = sdf.parse(beginTime);
			two = sdf.parse(endTime);
			long time1 = one.getTime();
			long time2 = two.getTime();
			long diff;
			if (time1 < time2) {
				diff = time2 - time1;
			} else {
				diff = time1 - time2;
			}
			day = diff / (24 * 60 * 60 * 1000);
			hour = (diff / (60 * 60 * 1000) - day * 24);
			min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
			sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long[] times = { day, hour, min, sec };
		return times;
	}

	/**两个时间相差距离多少天
	 * @param beginTime 格式：1990-01-01
	 * @param endTime 格式：2009-01-01
	 * @return long 返回值为：天
	 */
	public static long getDistanceDays(String beginTime, String endTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
		Date one;
		Date two;
		long day = 0;
		try {
			one = sdf.parse(beginTime);
			two = sdf.parse(endTime);
			long time1 = one.getTime();
			long time2 = two.getTime();
			long diff;
			if (time1 < time2) {
				diff = time2 - time1;
			} else {
				diff = time1 - time2;
			}
			day = diff / (24 * 60 * 60 * 1000);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return day;
	}

	/**两个时间相差距离几个月
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public static int getDistanceMonths(String beginTime, String endTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
		int iMonth = 0;
		try {
			Calendar objCalendarDate1 = Calendar.getInstance();
			objCalendarDate1.setTime(sdf.parse(beginTime));
			Calendar objCalendarDate2 = Calendar.getInstance();
			objCalendarDate2.setTime(sdf.parse(endTime));
			if (objCalendarDate2.equals(objCalendarDate1)) {
				return 0;
			}
			if (objCalendarDate1.after(objCalendarDate2)) {
				Calendar temp = objCalendarDate1;
				objCalendarDate1 = objCalendarDate2;
				objCalendarDate2 = temp;
			}
			if (objCalendarDate2.get(Calendar.YEAR) > objCalendarDate1.get(Calendar.YEAR)) {
				iMonth = (objCalendarDate2.get(Calendar.YEAR) - objCalendarDate1.get(Calendar.YEAR)) * 12 + objCalendarDate2.get(Calendar.MONTH) - objCalendarDate1.get(Calendar.MONTH);
			} else {
				iMonth = objCalendarDate2.get(Calendar.MONTH) - objCalendarDate1.get(Calendar.MONTH);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return iMonth;
	}

	/**获取当年
	 * @return
	 */
	public static int getCurrentYear() {
		return Calendar.getInstance().get(Calendar.YEAR);
	}

	/**获取当月
	 * @return
	 */
	public static int getCurrentMonth() {
		return Calendar.getInstance().get(Calendar.MONTH) + 1;
	}

	/**获取当日
	 * @return
	 */
	public static int getCurrentDay() {
		return Calendar.getInstance().get(Calendar.DATE);
	}

	/**获取当时
	 * @return
	 */
	public static int getCurrentHour() {
		return Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
	}

	/**获取当分
	 * @return
	 */
	public static int getCurrentMinute() {
		return Calendar.getInstance().get(Calendar.MINUTE);
	}

	/**
	 * 获取当前日期是星期几
	 * @return 当前日期是星期几
	 */
	public static String getWeekOfDate() {
		String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0) {
			w = 0;
		}
		return weekDays[w];
	}

}
