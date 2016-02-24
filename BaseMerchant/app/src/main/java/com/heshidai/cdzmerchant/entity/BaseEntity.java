/******************************************************************************
 * Copyright (C) 2014 ShenZhen HeShiDai Technology Co.,Ltd
 * All Rights Reserved.
 *****************************************************************************/
package com.heshidai.cdzmerchant.entity;


import com.heshidai.cdzmerchant.common.Constant;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * 网络请求数据基本类型
 * @author xiao di fa
 */
public class BaseEntity implements Serializable {
    /**
     * 返回码
     */
    private int code= Constant.DEFAULT_ERROR_CODE;
    /**
     * 返回描述
     */
    private  String msg;

    /**
     * 返回数据
     */
    private JSONObject data =new JSONObject();

    @Override
    public String toString() {
        return "BaseEntity{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public JSONObject getData() {
        return data;
    }

    public void setData(JSONObject data) {
        this.data = data;
    }
}
