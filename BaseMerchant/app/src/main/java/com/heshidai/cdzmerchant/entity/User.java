/**
 * ***************************************************************************
 * Copyright (C) 2014 ShenZhen HeShiDai Technology Co.,Ltd
 * All Rights Reserved.
 * ***************************************************************************
 */
package com.heshidai.cdzmerchant.entity;

import java.io.Serializable;

/**
 * 用户实体类
 *
 * @author lixiangxiang
 *         on 2015/10/12
 */
public class User implements Serializable {

    private String merId;//用户id
    private String merName;//店名
    private String token;//令牌
    private String password;//密码
    private String sign;//校验参数
    private String merAccount;//用户账户
    private int isStore;//是否总店


    public String getMerId() {
        return merId;
    }

    public void setMerId(String merId) {
        this.merId = merId;
    }

    public String getMerName() {
        return merName;
    }

    public void setMerName(String merName) {
        this.merName = merName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getMerAccount() {
        return merAccount;
    }

    public void setMerAccount(String merAccount) {
        this.merAccount = merAccount;
    }

    public int getIsStore() {
        return isStore;
    }

    public void setIsStore(int isStore) {
        this.isStore = isStore;
    }
}
