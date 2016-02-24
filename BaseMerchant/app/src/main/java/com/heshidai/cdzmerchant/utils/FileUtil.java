/******************************************************************************
 * Copyright (C) 2014 ShenZhen HeShiDai Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为合时代控股有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.heshidai.cdzmerchant.utils;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;

import com.heshidai.cdzmerchant.common.Constant;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * 文件操作工具类
 * @author xiao di fa
 */
public class FileUtil {

	/**
     * 递归删除文件
	 * @param filePath 文件路径
	 */
	public static void deleteAllFile(String filePath) {
		try {
            if(!TextUtils.isEmpty(filePath)) {
                File file = new File(filePath);
                if (!file.exists()) {
                    return;
                }
                if (file.isFile()) {
                    file.delete();
                    return;
                }
                if (file.isDirectory()) {
                    File[] childFile = file.listFiles();
                    if (childFile == null || childFile.length == 0) {
                        return;
                    }
                    for (File tempFile : childFile) {
                        deleteAllFile(tempFile.getPath());
                    }
                }
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    /**
     * 计算SDCARD的剩余空间
     * @return 返回空间大小
     */
	@SuppressWarnings("deprecation")
	public static long checkFreeDiskSpace() {
		long freeSpace = 0l;
        try {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
                if(null != statFs) {
                    long blockSize = statFs.getBlockSize();
                    long availableBlocks = statFs.getAvailableBlocks();
                    freeSpace = availableBlocks * blockSize / 1024 / 1024;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
		return freeSpace;
	}

    /**
     * 创建文件夹目录
     * @param directory 文件夹路径
     */
	public static void createDirectory(String directory) {
		try {
            if(!TextUtils.isEmpty(directory)) {
                File file = new File(directory);
                if (!file.exists()) {
                    file.mkdirs();
                }
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    /**
     * 往SDCARD文件里面写内容
     * @param fileName 文件名称
     * @param content 内容
     * @param append true追加 false覆盖
     */
	public static void writeContentToSDCard(String fileName, String content, boolean append) {
		try {
            if(!TextUtils.isEmpty(fileName) && !TextUtils.isEmpty(content)) {
                File file = new File(fileName);
                if(!file.exists()) {
                    file.createNewFile();
                }
                FileOutputStream fos = new FileOutputStream(file, append);
                fos.write(content.getBytes("UTF-8"));
                fos.flush();
                fos.close();
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    /**
     * 读取SDCARD文件的内容
     * @param fileName 文件路径
     * @return 返回内容
     */
	public static String readFileInSDCard(String fileName) {
		try {
            if(!TextUtils.isEmpty(fileName)) {
                File file = new File(fileName);
                if (file.exists()) {
                    FileInputStream fis = new FileInputStream(file);
                    ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                    byte[] buffer = new byte[fis.available()];
                    int length = -1;
                    while ((length = fis.read(buffer)) != -1) {
                        outStream.write(buffer, 0, length);
                    }
                    outStream.close();
                    fis.close();
                    return outStream.toString();
                }
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
        return "";
	}

    /**
     * 创建文件目录
     * @param context Context
     */
	public static void createDirectory(Context context) {
        long space = checkFreeDiskSpace();
        // 判断SD卡空间若大于50Mb，则视为可以存储
        Constant.SDCARD_CAN_SAVE = space > Constant.SDCARD_MEMORY;
		if (Constant.SDCARD_CAN_SAVE) {
			// 配置文件目录放置在SD卡根目录
			Constant.FILE_ROOT_DIRECTORY = Environment.getExternalStorageDirectory().getPath() + File.separator + context.getPackageName();
		} else {
			// 内存中创建目录
			Constant.FILE_ROOT_DIRECTORY = context.getFilesDir().getPath();
		}
		// 日志
		Constant.LOG_DIRECTORY = Constant.FILE_ROOT_DIRECTORY + "/log/";
		createDirectory(Constant.LOG_DIRECTORY);
		// 异常
		Constant.EXCEPTION_DIRECTORY = Constant.FILE_ROOT_DIRECTORY + "/exception/";
		createDirectory(Constant.EXCEPTION_DIRECTORY);
		// 缓存
		Constant.CACHE_DIRECTORY = Constant.FILE_ROOT_DIRECTORY + "/cache/";
		createDirectory(Constant.CACHE_DIRECTORY);
		// 图片
		Constant.IMAGE_DIRECTORY = Constant.FILE_ROOT_DIRECTORY + "/image/";
		createDirectory(Constant.IMAGE_DIRECTORY);
		// 安装包
		Constant.INSTALL_DIRECTORY = Constant.FILE_ROOT_DIRECTORY + "/apk/";
		createDirectory(Constant.INSTALL_DIRECTORY);
		// 上传
		Constant.UPLOAD_DIRECTORY = Constant.FILE_ROOT_DIRECTORY + "/upload/";
		createDirectory(Constant.UPLOAD_DIRECTORY);
	}

}