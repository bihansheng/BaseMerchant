/*
 * Copyright (C) 2014-2020,Qiniu Tech. Co., Ltd.
 * Author: Tony Dylan
 * Date: 2014-12-5
 * Description: 基于EventBus框架的通用工具消息事件类
 * Others:
 */

package com.heshidai.cdzmerchant.event;

public class UserEvent extends BaseEvent {

    public UserEvent(int method, int code) {
        super(method, code);
    }

    public UserEvent(BaseEvent event) {
        super(event);
    }

}
