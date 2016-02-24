/**
 * **************************************************************************
 * Copyright (C) 2014 ShenZhen HeShiDai Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为合时代控股有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 * **************************************************************************
 */
package com.heshidai.cdzmerchant.business.manager;

import android.content.Context;

import com.google.gson.JsonSyntaxException;
import com.heshidai.cdzmerchant.entity.User;
import com.heshidai.cdzmerchant.event.BaseEvent;
import com.heshidai.cdzmerchant.net.NetworkRequest;
import com.heshidai.cdzmerchant.net.ResponseHandler;
import com.heshidai.cdzmerchant.utils.DesUtil;
import com.heshidai.cdzmerchant.utils.MD5Util;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户相关信息处理类
 * 修改记录: 修改历史记录，包括修改日期、修改者及修改内容
 * 版权所有: 版权所有(C)2010-2014
 * 公　　司: 深圳合时代金融服务有限公司
 *
 * @author 万坤
 * @version 1.0.0
 * @date 2015-10-27
 * @history
 */


public class UserManager {
    public static final int LOGIN_TAG = 0x201; //用户相关TAG
    public static final int LOGOUT_TAG = 0x203;   //退出登录
    public static final String SERVER_ADDRESS_LOGIN = CommonRequestManager.SERVER_ADDRESS + "/login.action";
    /**
     * 网络请求--登录用户
     */
    public static void loginUser(Context context, String username, String password, String sign, String loginMode) {
        Map<String, String> map = new HashMap<>();
        String str1 =DesUtil.encrypt(username);
        String str2 =DesUtil.encrypt(password);
        String str3 =MD5Util.toDigest(sign);
        map.put("username", DesUtil.encrypt(username));
        map.put("password", DesUtil.encrypt(password));
        map.put("sign", MD5Util.toDigest(sign));
        map.put("loginMode", loginMode);
        NetworkRequest.post(SERVER_ADDRESS_LOGIN, map, LOGIN_TAG, new ResponseHandler(context, ResponseHandler.MODEL_USER));
    }

    /**
     * 网络请求--退出登录
     */
    public static void logoutUser(Context context) {
        Map<String, String> map = new HashMap<>();
        NetworkRequest.post(CommonRequestManager.SERVER_ADDRESS + "/appLogout.action", map, LOGOUT_TAG, new ResponseHandler(context, ResponseHandler.MODEL_USER));
    }


    /**
     * 解析结果中真正数据--用户相关
     * 这样的好处是可以统一捕捉并处理数据错误的问题,如果返回的数据 不符合要求，可以在这里拦截，将code改成对应的errorCode即可
     */
    public static BaseEvent userResultIndeed(String result, int action, BaseEvent event) throws JSONException, JsonSyntaxException {
        switch (action) {
            case LOGIN_TAG://登录
                event.obj = CommonRequestManager.resultToObject(result, User.class);
                break;
            case LOGOUT_TAG://退出登录
                event.obj = CommonRequestManager.resultToObject(result, User.class);
                break;
            default:
                break;
        }
        return event;
    }




}
