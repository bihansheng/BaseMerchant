/******************************************************************************
 * Copyright (C) 2014 ShenZhen HeShiDai Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为合时代控股有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.heshidai.cdzmerchant.utils;

import com.google.gson.Gson;

/**
 *  GsonManager
 *  修改记录:修改历史记录，包括修改日期、修改者及修改内容
 *  版权所有:版权所有(C)2010-2014
 *  公　　司:深圳合时代金融服务有限公司
 *  @version 1.0.0
 *  @date 2015-06-1
 *  @author 万坤
 *  @history 创建
 *
 */
public class GsonManager {

    /**
     * GsonManager实例
     */
    private static Gson instance;

    public static Gson getInstance() {
        if (instance == null) {
            synchronized (GsonManager.class) {
                if (instance == null) {
                    instance = new Gson();
                }
            }
        }
        return instance;
    }
}
