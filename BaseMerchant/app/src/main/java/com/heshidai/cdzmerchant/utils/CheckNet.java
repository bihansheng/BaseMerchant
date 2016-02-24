/******************************************************************************
 * Copyright (C) 2014 ShenZhen HeShiDai Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为合时代控股有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.heshidai.cdzmerchant.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 通用环境检测工具
 * @author xiao di fa
 *
 */
public class CheckNet {
	
	/**
	 * 检测网络环境，无对话框
	 */
	public static boolean checkNetwork(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkinfo = manager.getActiveNetworkInfo();    
        if (networkinfo == null || !networkinfo.isAvailable()) {
        	return false;
        } else if (networkinfo.getType() == ConnectivityManager.TYPE_WIFI) {
        	return true;
        } else if (networkinfo.getType() == ConnectivityManager.TYPE_MOBILE) {
        	return true;
        }
        return false;
	}
}
