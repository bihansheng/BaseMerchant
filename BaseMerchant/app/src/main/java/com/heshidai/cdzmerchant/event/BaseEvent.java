/**
 * **************************************************************************
 * Copyright (C) 2014 ShenZhen HeShiDai Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为合时代控股有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 * **************************************************************************
 */
package com.heshidai.cdzmerchant.event;
/**
 * 基于EventBus封装的Event基类，所有其他自定义Event全部继承此类。
 */
public class BaseEvent{
    /**
     * 发送event的方法Tag，用以区分事件
     */
    public int method;

    /**
     * 网络请求时code为网路请求的Response code，非网络请求时为本地定义的code
     */
    public int code;

    /**
     * 调用者class name，可用于接收者对不同对象调用者做区别对待。
     */
    public String caller = "unset";

    /**
     * 请求的参数
     */
    public Object param = null;

    /**
     * 用于提示的消息
     */
    public String msg;

    /**
     * 发送事件携带的对象内容
     */
    public Object obj;

    /**
     * 发送事件携带的对象内容，当event要传输两个对象时就需要用到此参数
     */
    public Object extraObj;

    /**
     * 发送事件携带的对象内容，当event要传输三个对象时就需要用到此参数
     */
    public Object thirdObj;

    /**
     * 只带一个对象的Event构造方法
     */
    public BaseEvent(int method, int code) {
        this.method = method;
        this.code = code;
    }

    public BaseEvent(BaseEvent event) {
        this.method = event.method;
        this.code = event.code;
        this.obj = event.obj;
        this.extraObj = event.extraObj;
        this.thirdObj = event.thirdObj;
        this.msg = event.msg;
        this.caller = event.caller;
        this.param = event.param;
    }
}
