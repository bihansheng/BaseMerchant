/**
 * **************************************************************************
 * Copyright (C) 2014 ShenZhen HeShiDai Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为合时代控股有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 * **************************************************************************
 */
package com.heshidai.cdzmerchant.app;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

import de.greenrobot.event.EventBus;

/**
 * 主服务类
 */
public class HSDService extends Service {

    private Runnable mRunnable;
    private Handler mHandler;

    @Override
    public void onCreate() {
        super.onCreate();
        mHandler = new Handler();
        EventBus.getDefault().register(this);  //注册EventBus
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        stopTiming();
        //取消注册EventBus
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    /**
     * 停止定时器
     */
    private void stopTiming() {
        if (null != mHandler && null != mRunnable) {
            mHandler.removeCallbacks(mRunnable);
        }
    }

}
