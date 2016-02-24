/******************************************************************************
 * Copyright (C) 2015 ShenZhen HeShiDai Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为合时代控股有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.heshidai.cdzmerchant.jpush;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.heshidai.cdzmerchant.R;
import com.heshidai.cdzmerchant.app.HSDAppManager;
import com.heshidai.cdzmerchant.base.BaseActivity;
import com.heshidai.cdzmerchant.business.main.WelcomeActivity;
import com.heshidai.cdzmerchant.utils.DisplayUtil;

/**
 * @author lixiangxiang
 * @desc 推送消息详情对话框页面
 * @date 2015/12/25 15:41
 */
public class MsgDetailActivity extends BaseActivity {

    private TextView textTitle, textMessage;
    private Button btnConfirm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(80 * DisplayUtil.getWidth(MsgDetailActivity.this) / 100, LinearLayout.LayoutParams.WRAP_CONTENT);
        setContentView(LayoutInflater.from(MsgDetailActivity.this).inflate(R.layout.jpush_msg_detail, null), lp);
        init();
        initData();
    }

    /**
     * 初始化界面
     */
    private void init() {
        //标题
        textTitle = (TextView) findViewById(R.id.text_title);
        //内容
        textMessage = (TextView) findViewById(R.id.text_message);
        textMessage.setMovementMethod(new ScrollingMovementMethod());
        //确定
        btnConfirm = (Button) findViewById(R.id.btn_confirm);
        btnConfirm.setOnClickListener(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        //数据
        String title = getIntent().getStringExtra("title");
        String description = getIntent().getStringExtra("description");
        //标题
        textTitle.setText((TextUtils.isEmpty(title)) ? "" : title);
        //内容
        textMessage.setText((TextUtils.isEmpty(description)) ? "" : description);
    }

    /**
     * 退出或者进入启动页面
     */
    private void exit() {
        HSDAppManager hsdAppManager = HSDAppManager.getInstance();
        Activity activity = hsdAppManager.currentActivity();
        if(hsdAppManager.isOneActivity() && null != activity && !activity.isFinishing() && activity instanceof MsgDetailActivity) {
            Intent welcomeIntent = new Intent(MsgDetailActivity.this, WelcomeActivity.class);
            startActivity(welcomeIntent);
        }
        MsgDetailActivity.this.finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_confirm://确定
                exit();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
