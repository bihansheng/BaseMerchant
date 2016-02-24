/******************************************************************************
 * Copyright (C) 2016 ShenZhen HeShiDai Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为合时代控股有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.heshidai.cdzmerchant.jpush;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.heshidai.cdzmerchant.app.HSDAppManager;
import com.heshidai.cdzmerchant.app.HSDApplication;
import com.heshidai.cdzmerchant.business.main.LoginActivity;
import com.heshidai.cdzmerchant.business.main.WebViewActivity;
import com.heshidai.cdzmerchant.common.SPManager;
import com.heshidai.cdzmerchant.utils.PreferencesUtils;

import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;

/**
 * @author lixiangxiang
 * @desc 极光推送工具类
 * @date 2015/12/31 10:46
 */
public class JpushUtil {

    /**
     * 设置别名
     * @param userId
     */
    public  static void setAlias(String userId){
        JPushInterface.setAliasAndTags(HSDApplication.getApplication(), userId, null);
    }

    /**
     * 取消别名
     */
    public static void cancelAlias(){
        JPushInterface.setAliasAndTags(HSDApplication.getApplication(), "", null);
    }

    /**
     * 极光推送跳转页面
     * @param context 上下文
     * @param bundle 数据
     */
    public static void skipActivity(Context context, Bundle bundle) {
        try {
            if(null != bundle) {
                if(!HSDAppManager.getInstance().isAppRunning()) {//如果APP没有运行清除数据
                    PreferencesUtils.putBoolean(HSDApplication.getApplication(), PreferencesUtils.PREFERENCE_NAME_CONFIG, SPManager.LOGIN_SUCCESS, false);
                }
                Intent skipIntent;
                JSONObject jsonObject = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                String msgType = jsonObject.optString("msgType");
                if ("6".equals(msgType)) {//资讯url
                    String noticeUrl = jsonObject.getString("noticeUrl");
                    skipIntent = new Intent(context, WebViewActivity.class);
                    skipIntent.putExtra("url", noticeUrl == null ? "" : noticeUrl);
                } else if ("7".equals(msgType)) {//纯文本信息
                    String title = jsonObject.getString("title");
                    String description = jsonObject.getString("description");
                    skipIntent = new Intent(context, MsgDetailActivity.class);
                    skipIntent.putExtra("title", title == null ? "" : title);
                    skipIntent.putExtra("description", description == null ? "" : description);
                } else {//版本更新之类的推送，直接跳转到首页
                    skipIntent = new Intent(context, LoginActivity.class);
                }
                skipIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(skipIntent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
