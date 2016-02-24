/**
 * **************************************************************************
 * Copyright (C) 2014 ShenZhen HeShiDai Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为合时代控股有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 * **************************************************************************
 */
package com.heshidai.cdzmerchant.entity;


import com.heshidai.cdzmerchant.net.INetRequest;

import java.util.Map;

/**
 * 网络请求参数
 * 修改记录: 修改历史记录，包括修改日期、修改者及修改内容
 * 版权所有: 版权所有(C)2010-2014
 * 公　　司: 深圳合时代金融服务有限公司
 *
 * @author 万坤
 * @version 1.0.0
 * @date 2015-07-21
 * @history
 */


public class RequestParams {

    /**
     * 网络请求方法
     */
    int  method;
    /**
     * 请求路径
     */
    String url;
    /**
     * 请求标记
     */
    int actionTag;
    /**
     * 网络标记
     */
    int tag;
    /**
     * 回调方法
     */
    INetRequest callBack;
    /**
     * 如果是psot请求，请求参数
     */
    Map<String, String> params;


    public RequestParams(int method, String url, int actionTag, int tag, INetRequest callBack, Map<String, String> params) {
        this.method = method;
        this.url = url;
        this.actionTag = actionTag;
        this.tag = tag;
        this.callBack = callBack;
        this.params = params;
    }

    public int getMethod() {
        return method;
    }

    public void setMethod(int method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getActionTag() {
        return actionTag;
    }

    public void setActionTag(int actionTag) {
        this.actionTag = actionTag;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public INetRequest getCallBack() {
        return callBack;
    }

    public void setCallBack(INetRequest callBack) {
        this.callBack = callBack;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }
}
