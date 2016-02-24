/******************************************************************************
 * Copyright (C) 2014 ShenZhen HeShiDai Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为合时代控股有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.heshidai.cdzmerchant.utils.log;


import com.heshidai.cdzmerchant.common.Constant;
import com.heshidai.cdzmerchant.utils.DateUtil;
import com.heshidai.cdzmerchant.utils.FileUtil;

import java.util.Vector;

/**
 * 打印日志到文件
 * @author xiao di fa
 */
public class HLogToFileQueue implements Runnable {

	private static HLogToFileQueue instance = null;
	public Vector<LogItem> msgQueen = new Vector<>();
    private boolean flag;

	private HLogToFileQueue() {
		flag = true;
		new Thread(this).start();
	}

	public synchronized static HLogToFileQueue getInstance() {
		if (null == instance) {
			instance = new HLogToFileQueue();
		}
		return instance;
	}

    @Override
	public void run() {
		while (flag) {
			synchronized (this) {
				try {
					if (msgQueen.isEmpty()) {
						Thread.sleep(200);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (msgQueen != null && !msgQueen.isEmpty()) {
                LogItem item = msgQueen.firstElement();
				String fileName = String.format("%s%s.txt", Constant.LOG_DIRECTORY, item.time);
				StringBuffer sb = new StringBuffer();
				sb.append("**********************************").append("\n");
				sb.append(DateUtil.getCurrentDate()).append("  ").append(item.tag).append("\n");
				sb.append(item.text).append("\n");
				sb.append("**********************************").append("\n");
				FileUtil.writeContentToSDCard(fileName, sb.toString(), true);
				msgQueen.remove(item);
			}
		}
	}

	class LogItem {
		public String time = null;
		public String tag = null;
		public String text = null;

        LogItem(String time, String tag, String text) {
			this.time = time;
			this.tag = tag;
			this.text = text;
		}
	}

	/**
     * 写日志，最终会自动在每行前加上当前时间
	 * @param tag 标签
	 * @param text 内容
	 */
	public void writeLog(String tag, String text) {
        LogItem logItem = new LogItem(DateUtil.getCurrentDate(Constant.DATE_FORMAT3), tag, text);
		getInstance().msgQueen.add(logItem);
	}

    /**
     * 停止日志文件输出到SDCARD
     */
	public void stopHLogToFile() {
		flag = false;
	}
}
