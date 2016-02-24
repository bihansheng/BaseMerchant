/**
 * ***************************************************************************
 * Copyright (C) 2014 ShenZhen HeShiDai Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为合时代控股有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 * ***************************************************************************
 */
package com.heshidai.cdzmerchant.entity;

import java.io.Serializable;

/**
 * 验证券号
 * @author lixiangxiang
 *         on 2015/10/12
 */
public class AOrderRecordEntity implements Serializable {
    public String checkCode;
    public String statusDesc;
    public Long couponAmount;//代金券金额
    public String phone;
    public String merName;
    public String merId;
    public Long nodiscAmount;//不优惠金额
    public Long totalAmount;//订单总金额
}
