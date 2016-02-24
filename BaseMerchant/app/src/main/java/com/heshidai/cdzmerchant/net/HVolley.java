/******************************************************************************
 * Copyright (C) 2014 ShenZhen HeShiDai Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为合时代控股有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.heshidai.cdzmerchant.net;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.heshidai.cdzmerchant.app.HSDApplication;
import com.heshidai.cdzmerchant.common.Constant;
import com.heshidai.cdzmerchant.utils.StringUtil;

/**
 * Volley通用工具类
 */
public class HVolley {

    private static final String TAG = "HVolley";
    private static HVolley instance;
    private RequestQueue mRequestQueue;

    private HVolley() {
        if (mRequestQueue == null) {
            //设置网络请求超时
            DefaultRetryPolicy.DEFAULT_TIMEOUT_MS = Constant.TIMEOUT;
            mRequestQueue = Volley.newRequestQueue(HSDApplication.getApplication().getApplicationContext());
        }
    }

    public static synchronized HVolley getInstance() {
        if (instance == null) {
            instance = new HVolley();
        }
        return instance;
    }

    /**
     * 添加到请求堆栈中
     *
     * @param request
     * @param actionTag
     * @param tag
     * @param netCallBack
     * @param <T>
     */
    public <T> void addToRequestQueue(Request<T> request, String actionTag, String tag, INetRequest netCallBack) {
        request.setTag(StringUtil.isEmptyOrNull(tag) ? TAG : tag);
        if (netCallBack != null) {
            request.setmNetCallBack(netCallBack);
            request.setmAction(actionTag);
        }
//        DefaultRetryPolicy policy = new DefaultRetryPolicy(Constant.TIMEOUT, 0, 1.0f);
//        request.setRetryPolicy(policy);
        if (mRequestQueue != null) {
            mRequestQueue.add(request);
        }
    }

    /**
     * 取消本次网络请求
     *
     * @param tag
     */
    public void cancelPendingRequests(String tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(StringUtil.isEmptyOrNull(tag) ? TAG : tag);
        }
    }

}
