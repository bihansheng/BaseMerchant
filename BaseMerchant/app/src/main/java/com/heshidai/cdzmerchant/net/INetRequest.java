/******************************************************************************
 * Copyright (C) 2014 ShenZhen HeShiDai Co.,Ltd
 * All Rights Reserved.
 * 本软件为合时代控股有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.heshidai.cdzmerchant.net;

import org.json.JSONObject;

/**
 * 网络请求回调接口
 * @author xiao di fa
        */
public interface INetRequest {

    /**
     * 异步网络请求结果
     * @param action 区分标记
     * @param jsonObj JSON结果
     */
    void setAsyncJsonCallBack(int action, JSONObject jsonObj);

    /**
     * 网络超时结果
     * @param action 区分标记
     */
    void setTimeoutError(int action);

    /**
     * 网络错误码
     * @param action 区分标记
     * @param statusCode 状态码
     */
    void setNetWorkErrorCode(int action, int statusCode);

}