/******************************************************************************
 * Copyright (C) 2014 ShenZhen HeShiDai Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为合时代控股有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.heshidai.cdzmerchant.utils.log;

import android.util.Log;

import com.heshidai.cdzmerchant.common.Constant;


/**
 * 统一的日志工具类
 * @author xiao di fa
 */
public class HLog {

	public static void i(String tag, String msg) {
        if (Constant.IS_LOGCAT) {
            Log.i(tag, msg);
        }
        if (Constant.IS_PRINT_FILE) {
            HLogToFileQueue.getInstance().writeLog(tag, msg);
        }
    }

	public static void e(String tag, String msg) {
		if (Constant.IS_LOGCAT) {
			Log.e(tag, msg);
		}
		if (Constant.IS_PRINT_FILE) {
			HLogToFileQueue.getInstance().writeLog(tag, msg);
		}
	}

	public static void d(String tag, String msg) {
		if (Constant.IS_LOGCAT) {
			Log.d(tag, msg);
		}
        if (Constant.IS_PRINT_FILE) {
            HLogToFileQueue.getInstance().writeLog(tag, msg);
        }
	}
}
