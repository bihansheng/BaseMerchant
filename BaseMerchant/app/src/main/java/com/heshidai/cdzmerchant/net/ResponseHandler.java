/**
 * **************************************************************************
 * Copyright (C) 2014 ShenZhen HeShiDai Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为合时代控股有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 * **************************************************************************
 */
package com.heshidai.cdzmerchant.net;

import android.content.Context;

import com.google.gson.JsonSyntaxException;
import com.heshidai.cdzmerchant.R;
import com.heshidai.cdzmerchant.business.manager.CouponManager;
import com.heshidai.cdzmerchant.business.manager.CommonRequestManager;
import com.heshidai.cdzmerchant.business.manager.UserManager;
import com.heshidai.cdzmerchant.common.Constant;
import com.heshidai.cdzmerchant.event.BaseEvent;
import com.heshidai.cdzmerchant.event.CommonEvent;
import com.heshidai.cdzmerchant.event.CouponEvent;
import com.heshidai.cdzmerchant.event.UserEvent;

import org.json.JSONException;
import org.json.JSONObject;

import de.greenrobot.event.EventBus;

/**
 * 根据业务需求解析网络请求到的数据
 */
public class ResponseHandler implements INetRequest {
    //业务类型分类
    public static final int MODEL_COMMON = 0x01;
    public static final int MODEL_COUPON = 0x02;
    public static final int MODEL_USER = 0x04;
    private Context mContext;
    private BaseEvent event;
    private int mBusinessType;//业务逻辑分类，用于判断event类型的参数，便于数据接收

    /**
     * 构造函数
     *
     * @param context       上下文
     * @param mBusinessType 业务逻辑分类，用于设置enent类型的参数，便于数据接收
     */
    public ResponseHandler(Context context, int mBusinessType) {
        initHandler(context, mBusinessType);
    }

    private void initHandler(Context context, int mBusinessType) {
        this.mContext = context;
        event = new BaseEvent(CommonRequestManager.DEFAULT_TAG, Constant.EVENT_UNKNOWN_ERROR);
        this.mBusinessType = mBusinessType;
    }

    /**
     * 网络请求正确
     *
     * @param action  区分请求的标记
     * @param jsonObj JSON结果
     */
    @Override
    public void setAsyncJsonCallBack(int action, JSONObject jsonObj) {
        event.method = action;
        analyzeResult(jsonObj, action);
    }

    /**
     * 网络请求超时
     *
     * @param action 区分请求的标记
     */
    @Override
    public void setTimeoutError(int action) {
        event.method = action;
//        mBusinessType = MODEL_ERROR;
        event.msg = mContext.getResources().getString(R.string.loading_timeout);
        event.code = com.heshidai.cdzmerchant.common.Constant.EVENT_REQUEST_TIMEOUT;
        postEvent(action);
    }

    /**
     * 网络请求失败
     *
     * @param action     区分请求的标记
     * @param statusCode 状态码
     */
    @Override
    public void setNetWorkErrorCode(int action, int statusCode) {
        event.method = action;
//        mBusinessType = MODEL_ERROR;
        event.msg = mContext.getResources().getString(R.string.network_fail);
        event.code = Constant.EVENT_REQUEST_FAILED;
        postEvent(action);
    }



    /**
     * 初步解析结果数据，主要判返回数据是否为空、格式是否正确，
     */
    private void analyzeResult(JSONObject result, int action) {
        if (result == null) {
            event.code = com.heshidai.cdzmerchant.common.Constant.EVENT_REQUEST_FAILED;
            event.msg = mContext.getResources().getString(R.string.network_fail);
            postEvent(action);
            return;
        }
        try {
            event.code = Integer.parseInt(result.getString("code"));
            if (result.has("msg")) {
                event.msg = result.getString("msg");
            }
            if (result.has("data")) {
                String resultIndeed = result.getString("data");//这里的data可能为null
                processResultIndeed(resultIndeed, action, event);
            }
           postEvent(action);
        } catch (JSONException e) {
            event.code = com.heshidai.cdzmerchant.common.Constant.EVENT_JSON_EXCEPTION_ERROR;
            event.msg = mContext.getResources().getString(R.string.network_fail);
            postEvent(action);
            e.printStackTrace();
        } catch (JsonSyntaxException e) {
            event.code = com.heshidai.cdzmerchant.common.Constant.EVENT_JSON_EXCEPTION_ERROR;
            event.msg = mContext.getResources().getString(R.string.network_fail);
            postEvent(action);
            e.printStackTrace();
        }
    }
    /**
     * 将baseevent转化成跟业务相关的类型，便于数据的分类，提高识别效率和使代码清晰
     */
    private void postEvent(int action) {
        switch (mBusinessType) {
            case MODEL_COMMON:
                CommonEvent commonEvent = new CommonEvent(event);
                EventBus.getDefault().post(commonEvent);
                break;
            case MODEL_COUPON:
                CouponEvent couponEvent = new CouponEvent(event);
                EventBus.getDefault().post(couponEvent);
                break;
            case MODEL_USER:
                UserEvent userEvent = new UserEvent(event);
                EventBus.getDefault().post(userEvent);
                break;
            default:
                break;
        }
    }

    /**
     * 对得到的正确数据进行解析，返回类对象
     * 注意：因为有的请求返回的数据就是空，所以要注意处理data为null或者为空的情况
     */
    private void processResultIndeed(String result, int action, BaseEvent baseEvent) throws JSONException, JsonSyntaxException {
        //注意要处理data为null或者为空的情况
        switch (mBusinessType) {
            case MODEL_COMMON:
                break;
            case MODEL_COUPON:
                event=  CouponManager.couponResultIndeed(result, action, baseEvent);
                break;
            case MODEL_USER:
                event=  UserManager.userResultIndeed(result, action, baseEvent);
                break;
            default:
                break;
        }
    }

}
