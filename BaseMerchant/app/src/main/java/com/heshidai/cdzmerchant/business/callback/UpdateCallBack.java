/******************************************************************************
 * Copyright (C) 2014 ShenZhen HeShiDai Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为合时代控股有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.heshidai.cdzmerchant.business.callback;


import com.heshidai.cdzmerchant.entity.Update;

/**
 * 检查更新回调
 * @author xiao di fa
 */
public interface UpdateCallBack {

    /**
     * 回调是否有更新
     * @param hasNewVersion
     * @param update
     */
    void setUpdateCallBack(boolean hasNewVersion, Update update);
}
