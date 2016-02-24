/******************************************************************************
 * Copyright (C) 2014 ShenZhen HeShiDai Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为合时代控股有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.heshidai.cdzmerchant.utils;

import android.content.Context;
import android.content.pm.PackageManager;

/**
 * 获取包的信息类
 * @author xiao di fa
 * 
 */
public class PackageUtil {

	/**
	 * 获取版本的名称
	 * @param context
	 * @return
	 */
	public static String getVersion(Context context) {
		try {
			PackageManager manager = context.getPackageManager();
			android.content.pm.PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
			return info.versionName;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 获取版本的code
	 * @param context
	 * @return
	 */
	public static int getCode(Context context) {
		try {
			PackageManager manager = context.getPackageManager();
			android.content.pm.PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
			return info.versionCode;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

}
