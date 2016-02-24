/******************************************************************************
 * Copyright (C) 2016 ShenZhen HeShiDai Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为合时代控股有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.heshidai.cdzmerchant.business.main;

import android.content.Intent;
import android.os.Bundle;

import com.heshidai.cdzmerchant.R;
import com.heshidai.cdzmerchant.base.BaseActivity;
import com.heshidai.cdzmerchant.common.SPManager;
import com.heshidai.cdzmerchant.utils.PreferencesUtils;

import cn.jpush.android.api.JPushInterface;

/**
 * 欢迎界面
 * @author lixiangxiang
 */
public class WelcomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);//极光推送注册统计
    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }

    /**
     * 初始化界面
     */
    private void init() {
        //清除数据
        PreferencesUtils.putBoolean(WelcomeActivity.this, PreferencesUtils.PREFERENCE_NAME_CONFIG, SPManager.LOGIN_SUCCESS, false);
        startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
        WelcomeActivity.this.finish();
    }
}
