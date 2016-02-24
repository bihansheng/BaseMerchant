/******************************************************************************
 * Copyright (C) 2014 ShenZhen HeShiDai Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为合时代控股有限公司开发研制未经本公司正式书面同意，其他任何个人、团
 * 不得使用、复制修改或发布本软件.
 *****************************************************************************/
package com.heshidai.cdzmerchant.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * @author xiao di fa
 *
 */
public class InputMethod {

	/**
	 * @param activity Activity
	 */
	public static void closeInputMethod(Activity activity) {
		try {
			InputMethodManager manager = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
			if(manager.isActive()) {
				manager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param activity Activity
     * @param myView
	 */
	public static void closeInputMethod(Activity activity, View myView) {
		try {
			InputMethodManager manager = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
			manager.hideSoftInputFromWindow(myView.getWindowToken(), 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param activity Activity
	 * @param myView
	 */
	public static void showInputMethod(Activity activity, View myView) {
		try {
			InputMethodManager manager = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
			manager.showSoftInput(myView, InputMethodManager.SHOW_FORCED);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
