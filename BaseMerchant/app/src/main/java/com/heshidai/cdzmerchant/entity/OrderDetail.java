/**
 * **************************************************************************
 * Copyright (C) 2014 ShenZhen HeShiDai Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为合时代控股有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 * **************************************************************************
 */
package com.heshidai.cdzmerchant.entity;

import com.heshidai.cdzmerchant.common.Constant;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 类功能介绍。
 * 版权所有: 版权所有(C)2010-2014
 * 公　　司: 深圳合时代金融服务有限公司
 * @author 万坤
 * @date 2015-10-09
 */


public class OrderDetail implements Serializable{

    private String checkCode = Constant.DEFAULT_STRING;
    private int storeId;
    private String nickname = Constant.DEFAULT_STRING;
    private BigDecimal couponAmount = new BigDecimal(0);
    private BigDecimal payAmount= new BigDecimal(0);//实付金额
    private String phone;
    private String merChantName = Constant.DEFAULT_STRING;
    private int status;
    private int payType;
    private String payTime = Constant.DEFAULT_STRING;
    private String useTime= Constant.DEFAULT_STRING;
    private BigDecimal actualAmout= new BigDecimal(0);//实付金额
    private BigDecimal saveAmout= new BigDecimal(0);
    private  BigDecimal nodiscAmount =new BigDecimal(0);//不优惠金额
    private  BigDecimal  totalAmount = new BigDecimal(0);//订单总金额

    public BigDecimal getNodiscAmount() {
        return nodiscAmount;
    }

    public void setNodiscAmount(BigDecimal nodiscAmount) {
        this.nodiscAmount = nodiscAmount;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getCheckCode() {
        return checkCode;
    }

    public void setCheckCode(String checkCode) {
        this.checkCode = checkCode;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public BigDecimal getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(BigDecimal couponAmount) {
        this.couponAmount = couponAmount;
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMerChantName() {
        return merChantName;
    }

    public void setMerChantName(String merChantName) {
        this.merChantName = merChantName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getUseTime() {
        return useTime;
    }

    public void setUseTime(String useTime) {
        this.useTime = useTime;
    }

    public BigDecimal getActualAmout() {
        return actualAmout;
    }

    public void setActualAmout(BigDecimal actualAmout) {
        this.actualAmout = actualAmout;
    }

    public BigDecimal getSaveAmout() {
        return saveAmout;
    }

    public void setSaveAmout(BigDecimal saveAmout) {
        this.saveAmout = saveAmout;
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "checkCode='" + checkCode + '\'' +
                ", storeId=" + storeId +
                ", nickname='" + nickname + '\'' +
                ", couponAmount=" + couponAmount +
                ", payAmount=" + payAmount +
                ", phone='" + phone + '\'' +
                ", merChantName='" + merChantName + '\'' +
                ", status=" + status +
                ", payType=" + payType +
                ", payTime='" + payTime + '\'' +
                ", useTime='" + useTime + '\'' +
                ", actualAmout=" + actualAmout +
                ", saveAmout=" + saveAmout +
                ", nodiscAmount=" + nodiscAmount +
                ", totalAmount=" + totalAmount +
                '}';
    }
}
