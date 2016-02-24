/**
 * **************************************************************************
 * Copyright (C) 2014 ShenZhen HeShiDai Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为合时代控股有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 * **************************************************************************
 */
package com.heshidai.cdzmerchant.entity;

import java.util.List;

/**
 * 类功能介绍。
 * 版权所有: 版权所有(C)2010-2014
 * 公　　司: 深圳合时代金融服务有限公司
 * @author 万坤
 * @date 2015-10-09
 */


public class OrderList {
    /**
     * total : 15
     * couponList : [{"checkCode":"678****533","storeId":1,"nickname":"aaa","couponAmount":10,"payAmount":8,"phone":"132****0001","merChantName":"俏江南深圳南山分店","status":1111,"payType":1,"payTime":"2015-09-2317:36:03","useTime":"2015-09-23 17:37:56","actualAmout":8,"saveAmout":0}]
     */

    private int total;
    private List<OrderDetail> couponList;

    public void setTotal(int total) {
        this.total = total;
    }

    public void setCouponList(List<OrderDetail> couponList) {
        this.couponList = couponList;
    }

    public int getTotal() {
        return total;
    }

    public List<OrderDetail> getCouponList() {
        return couponList;
    }
}
