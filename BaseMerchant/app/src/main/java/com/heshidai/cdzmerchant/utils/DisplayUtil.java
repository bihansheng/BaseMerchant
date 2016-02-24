/******************************************************************************
 * Copyright (C) 2014 ShenZhen HeShiDai Technology Co.,Ltd
 * All Rights Reserved.
 * 鏈蒋浠朵负鍚堟椂浠ｆ帶鑲℃湁闄愬叕鍙稿紑鍙戠爺鍒躲?鏈粡鏈叕鍙告寮忎功闈㈠悓鎰忥紝鍏朵粬浠讳綍涓汉銆佸洟浣?
 * 涓嶅緱浣跨敤銆佸鍒躲?淇敼鎴栧彂甯冩湰杞欢.
 *****************************************************************************/
package com.heshidai.cdzmerchant.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

/**动态获取屏幕宽高
 * @author xiao di fa
 */
public class DisplayUtil {

	/**获取屏幕宽度
	 * @param activity Activity
	 * @return 宽度
	 */
	public static int getWidth(Activity activity) {
		DisplayMetrics metrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		return metrics.widthPixels;
	}

	/**获取屏幕高度
	 * @param activity Activity
	 * @return 高度
	 */
	public static int getHeight(Activity activity) {
		DisplayMetrics metrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		return metrics.heightPixels;
	}

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 * @param context Context
	 * @param dpValue
	 * @return px
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 * @param context Context
	 * @param pxValue
	 * @return dip
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}


}
