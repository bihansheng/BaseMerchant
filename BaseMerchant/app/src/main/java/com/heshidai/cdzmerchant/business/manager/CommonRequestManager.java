/**
 * **************************************************************************
 * Copyright (C) 2014 ShenZhen HeShiDai Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为合时代控股有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 * **************************************************************************
 */
package com.heshidai.cdzmerchant.business.manager;

import com.google.gson.JsonSyntaxException;
import com.heshidai.cdzmerchant.net.INetRequest;
import com.heshidai.cdzmerchant.net.NetworkRequest;
import com.heshidai.cdzmerchant.utils.GsonManager;

import org.json.JSONException;

/**
 * 联网请求管理类
 * 修改记录: // 修改历史记录，包括修改日期、修改者及修改内容
 * 版权所有: 版权所有(C)2010-2014
 * 公　　司: 深圳合时代金融服务有限公司
 *
 * @author 万坤
 * @version 1.0.0
 * @date 2015-05-25
 * @history
 */
public class CommonRequestManager {
    //网络请求标记
    public static final int DEFAULT_TAG = 0x000;
    //查询安卓版本
    public static final int DOWNLOAD_TAG = 0x001;
    //服务器路径
//    public static final String SERVER_ADDRESS = "http://183.62.205.226:8666/merchant";
    public static final String SERVER_ADDRESS ="http://merchant.heshidai.com";

    // 查询安卓版本
    //  public static final String GET_APK_VERSION = "http://192.168.1.246:8081/sc/appVersion/getAppVersion?type=1&category=1";
    public static final String GET_APK_VERSION = "https://sc.heshidai.com/appVersion/getAppVersion?type=1&category=1";

    /**
     * 将json装换为类对象
     *
     * @param result 求情的结果
     * @param clazz  对象类
     * @return object 如果失败返回null
     */
    public static Object resultToObject(String result, Class clazz) throws JSONException, JsonSyntaxException {
        return GsonManager.getInstance().fromJson(result, clazz);
    }

    /**
     * 查询安卓版本
     *
     * @param callBack
     */
    public static void getApkVersion(INetRequest callBack) {
        NetworkRequest.get(GET_APK_VERSION, DOWNLOAD_TAG, callBack);
    }

}
