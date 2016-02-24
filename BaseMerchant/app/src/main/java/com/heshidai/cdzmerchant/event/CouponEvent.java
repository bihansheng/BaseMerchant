/*
 * Copyright (C) 2014-2020,Qiniu Tech. Co., Ltd.
 * Author: Tony Dylan
 * Date: 2014-12-5
 * Description: 基于EventBus框架的通用工具消息事件类
 * Others:
 */

package com.heshidai.cdzmerchant.event;

public class CouponEvent extends BaseEvent {

    public CouponEvent(int method, int code) {
        super(method, code);
    }

    public CouponEvent(BaseEvent event) {
        super(event);
    }

}
