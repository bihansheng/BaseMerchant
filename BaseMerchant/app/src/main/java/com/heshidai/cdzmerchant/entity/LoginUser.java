/**
 * **************************************************************************
 * Copyright (C) 2014 ShenZhen HeShiDai Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为合时代控股有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 * **************************************************************************
 */
package com.heshidai.cdzmerchant.entity;

/**
 * 用于在token失效时获取登录结果数据（）
 * 修改记录: 修改历史记录，包括修改日期、修改者及修改内容
 * 版权所有: 版权所有(C)2010-2014
 * 公　　司: 深圳合时代金融服务有限公司
 *
 * @author 万坤
 * @version 1.0.0
 * @date 2015-10-12
 * @history
 */


public class LoginUser {
//    code	String	否	成功：0，失败：1
//    msg	String	否	返回结果描述
//
//    data	Object	是	如果code返回1为空
//    --merId	int	是	商家id
//    --merName	String	是	商家名称
//    --token	String	是	令牌

    public int code;
    public String msg;
    public User data;


}
