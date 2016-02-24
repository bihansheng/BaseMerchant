/******************************************************************************
 * Copyright (C) 2014 ShenZhen HeShiDai Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为合时代控股有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.heshidai.cdzmerchant.app;

import android.os.SystemClock;

import java.lang.Thread.UncaughtExceptionHandler;

/**
 * App异常崩溃处理器
 * @author xiao di fa
 */
public class HSDAppCrashHandler implements UncaughtExceptionHandler {

	private UncaughtExceptionHandler mDefaultHandler;
	private HSDApplication application;

	public HSDAppCrashHandler(HSDApplication instance) {
		this.mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
		this.application = instance;
	}

	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		if (!handleException(ex) && mDefaultHandler != null) {
			// 如果用户没有处理则让系统默认的异常处理器来处理
			mDefaultHandler.uncaughtException(thread, ex);
		} else {
			// Sleep一会后结束程序，显示提示信息
			SystemClock.sleep(2000);
//			HSDAppManager.getInstance().appExit(true);
		}
	}

    /**
     * 自定义处理异常
     * @param ex 异常
     * @return 如果处理了异常返回true，否则false
     */
	private boolean handleException(Throwable ex) {
		if (ex == null) {
			return true;
		}
//		// 保存错误报告文件
//		saveCrashInfoToFile(ex);
//		//异常日志上传到友盟服务器
//		MobclickAgent.reportError(application.getApplicationContext(), ex);
//        //程序异常终止上传数据
//        MobclickAgent.onKillProcess(application.getApplicationContext());
//		// 使用Toast来显示异常信息
//		new Thread() {
//			@Override
//			public void run() {
//				Looper.prepare();
//                PromptManager.showToast(application.getApplicationContext(), R.string.common_app_error);
//				Looper.loop();
//			}
//		}.start();
		// 打印异常信息到控制台
		ex.printStackTrace();
		return true;
	}

//	/**生成崩溃日志
//	 * @param ex 异常
//	 */
//	private void saveCrashInfoToFile(Throwable ex) {
//		StringWriter errors = new StringWriter();
//		ex.printStackTrace(new PrintWriter(errors));
//        String fileName = String.format("%s%s.txt", Constant.EXCEPTION_DIRECTORY, DateUtil.getCurrentDate(Constant.DATE_FORMAT2));
//        FileUtil.writeContentToSDCard(fileName, errors.toString(), true);
//	}

}