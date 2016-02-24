/**
 * ***************************************************************************
 * Copyright (C) 2014 ShenZhen HeShiDai Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为合时代控股有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 * ***************************************************************************
 */
package com.heshidai.cdzmerchant.common;

/**
 * 系统常用配置
 *
 * @author xiao di fa
 */
public class Constant {
    public static final String TAG = "HSD_CDZM";

    /**
     * 是否打印日志
     */
    public static boolean IS_LOGCAT = false;
    /**
     * 是否打印文件
     */
    public static boolean IS_PRINT_FILE = false;
    /**
     * 最大的磁盘缓存大小---50M
     */
    public static final int MAX_DISK_CACHE = 50 * 1024 * 1024;
    /**
     * 网络异常
     */
    public static final int NO_CONNECTION_ERROR = 3100;
    /**
     * 普通请求超时时间
     */
    public static final int TIMEOUT = 30* 1000;

    /**
     * DES加密解密KEY
     */
    public static final String DES_KEY = "hsd_dev_app_merchant_3ds";

    /**
     * 网络错误码，默认
     */
    public static final int DEFAULT_ERROR_CODE = -1;
    /**
     * 网络错误码，网络异常和加载超时
     */
    public static final int NETWORK_ERROR_CODE = -2;
    /**
     * 网络错误码，加载超时
     */
    public static final int EVENT_REQUEST_TIMEOUT = -3;
    /**
     * 网络错误码  网络请求失败
     */
    public static final int EVENT_REQUEST_FAILED = -4;
    /**
     * 网络错误码  json解析失败
     */
    public static final int EVENT_JSON_EXCEPTION_ERROR = -5;
    /**
     * 网络错误码  未知错误
     */
    public static final int EVENT_UNKNOWN_ERROR = -6;

    /**
     * SD卡默认50M
     */
    public static final int SDCARD_MEMORY = 50;
    /**
     * SD卡是否可以存储
     */
    public static boolean SDCARD_CAN_SAVE = false;
    /**
     * 存储文件根目录
     */
    public static String FILE_ROOT_DIRECTORY;
    /**
     * 日志目录
     */
    public static String LOG_DIRECTORY;
    /**
     * 异常目录
     */
    public static String EXCEPTION_DIRECTORY;
    /**
     * 缓存目录
     */
    public static String CACHE_DIRECTORY;
    /**
     * 图片目录
     */
    public static String IMAGE_DIRECTORY;
    /**
     * 安装包目录
     */
    public static String INSTALL_DIRECTORY;
    /**
     * 上传目录
     */
    public static String UPLOAD_DIRECTORY;

    /**
     * 时间格式1
     */
    public static final String DATE_FORMAT1 = "yyyy-MM-dd HH:mm:ss";
    /**
     * 时间格式2
     */
    public static final String DATE_FORMAT2 = "yyyy-MM-dd HHmmss";
    /**
     * 时间格式3
     */
    public static final String DATE_FORMAT3 = "yyyy-MM-dd";
    /**
     * 时间格式4
     */
    public static final String DATE_FORMAT4 = "yyyy-MM-dd HH:mm";

    /**
     * 默认Double值--0.0
     */
    public static final Double DEFAULT_DOUBLE_ZERO = 0.0;
    /**
     * 默认int值(0)
     */
    public static final int DEFAULT_INT_ZERO = 0;
    /**
     * 默认int值(-1)
     */
    public static final int DEFAULT_INT_MINUS = -1;
    /**
     * 默认String值("")
     */
    public static final String DEFAULT_STRING = "";
    /**
     * 默认String值("-")
     */
    public static final String DEFAULT_EMPTY_DATA_STRING = "-";
    /**
     * 默认String值("-")
     */
    public static final String DEFAULT_DOUBLE_STRING = "0.00";



    /**
     * MD5 加密key
     */

    public static final String MD5_KEY ="hsd_dev_app_merchant_md5";

    /**
     * 验券历史 每页个数
     */
    public static final int PAGE_SIZE = 20;



}
