/*****************************************************************************
 * Copyright (C) 2015 ShenZhen HeShiDai Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为合时代控股有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 * ***************************************************************************/
package com.heshidai.cdzmerchant.business.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.heshidai.cdzmerchant.R;
import com.heshidai.cdzmerchant.app.HSDApplication;
import com.heshidai.cdzmerchant.base.BaseActivity;
import com.heshidai.cdzmerchant.business.manager.UserManager;
import com.heshidai.cdzmerchant.common.Constant;
import com.heshidai.cdzmerchant.common.SPManager;
import com.heshidai.cdzmerchant.entity.User;
import com.heshidai.cdzmerchant.event.UserEvent;
import com.heshidai.cdzmerchant.utils.CheckNet;
import com.heshidai.cdzmerchant.utils.DesUtil;
import com.heshidai.cdzmerchant.utils.DisplayUtil;
import com.heshidai.cdzmerchant.utils.InputMethod;
import com.heshidai.cdzmerchant.utils.PreferencesUtils;
import com.heshidai.cdzmerchant.utils.PromptManager;
import com.heshidai.cdzmerchant.utils.UserUtil;

/**
 * 登录模块
 * @author lixiangxiang
 *         on 2015/10/8
 */
public class LoginActivity extends BaseActivity {

    private User user;//用户对象
    private String merPassword;//登录密码
    private String merAccount;//登录账户
    private  int  firstPan=0;//键盘高度发生变化后记录标志
    private int width;//屏幕宽度
    private int height;//屏幕高度

    private EditText username;//用户框
    private EditText password;//密码框
    private TextView login;//登录按钮
    private ScrollView scrollView;//控制屏幕焦点变化时 上滚

    private Intent intent;

    private Handler handler = new Handler(new Handler.Callback(){

        @Override
        public boolean handleMessage(Message msg) {
            scrollView.scrollBy(width, height);
            return false;
        }
    });

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initLayout();
        initLogin();
        initData();
    }

    /**
     * 初始化登录
     */
    private void initLogin() {
        User user = (User) PreferencesUtils.readObject(LoginActivity.this, PreferencesUtils.PREFERENCE_NAME_DATA, SPManager.USER);
        if (null != user && !TextUtils.isEmpty(user.getMerName())) {
            username.setText(DesUtil.decrypt(user.getMerAccount()));
            password.setText(DesUtil.decrypt(user.getPassword()));
            if (CheckNet.checkNetwork(LoginActivity.this)) {
                PromptManager.showProgressDialogCommit(LoginActivity.this, true, false);
                //判断是否可以登录
                UserManager.loginUser(LoginActivity.this, DesUtil.decrypt(user.getMerAccount()), DesUtil.decrypt(user.getPassword()), user.getSign(), "1");//本地存储的密码是加密的
            }
        }
    }

    private void initData() {
        width = DisplayUtil.getWidth(LoginActivity.this);
        height = DisplayUtil.getHeight(LoginActivity.this);
    }

    /**
     * 退出登录执行的方法，用来设置用户名
     */
    @Override
    protected void onStart() {
        super.onStart();
        String um = (String) PreferencesUtils.readObject(LoginActivity.this, PreferencesUtils.PREFERENCE_NAME_DATA, SPManager.USERNAME);
        if (!TextUtils.isEmpty(um)) {
            username.setText(um);
            username.setSelection(um.length());
        }
    }

    private void initLayout() {
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        login = (TextView) findViewById(R.id.tv_login);
        login.setOnClickListener(this);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        scrollView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //比较Activity根布局与当前布局的大小
                int heightDiff = scrollView.getRootView().getHeight() - scrollView.getHeight();
                if (heightDiff > 100) {
                    if(0 == firstPan) {
                        firstPan = 1;
                        handler.sendEmptyMessageDelayed(0,200);
                    }
                    //大小超过100时，一般为显示虚拟键盘事件
                } else {
                    firstPan = 0;
                    //大小小于100时，为不显示虚拟键盘或虚拟键盘隐藏
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tv_login) {
            merAccount = username.getText().toString().trim();
            merPassword = password.getText().toString().trim();
            if (CheckNet.checkNetwork(LoginActivity.this)) {
                if (!TextUtils.isEmpty(merPassword) && !TextUtils.isEmpty(merAccount)) {
                    PromptManager.showProgressDialog(LoginActivity.this);
                    user = new User();
                    String sign = String.format("%s%s%s", merAccount, merPassword, Constant.MD5_KEY);
                    user.setSign(sign);
                    PreferencesUtils.saveObject(HSDApplication.getApplication(), PreferencesUtils.PREFERENCE_NAME_DATA, SPManager.USER, user);
                    UserManager.loginUser(LoginActivity.this, merAccount, merPassword, sign, "1");
                } else {
                    PromptManager.showToast(LoginActivity.this, LoginActivity.this.getString(R.string.password_empty));
                }
            } else {
                PromptManager.showToast(LoginActivity.this, LoginActivity.this.getString(R.string.network_fail));
            }
        }
    }

    public void onEventMainThread(UserEvent event) {
        PromptManager.hideProgressDialog();
        if (event.method == UserManager.LOGIN_TAG) {
            switch (event.code) {
                case 0:
                    User user = (User) event.obj;
                    if (!TextUtils.isEmpty(merPassword) && !TextUtils.isEmpty(merAccount)) {
                        user.setPassword(merPassword);
                        user.setMerAccount(merAccount);
                    }
                    UserUtil.saveUserInfo(user);
                    PreferencesUtils.saveObject(LoginActivity.this, PreferencesUtils.PREFERENCE_NAME_DATA, SPManager.USERNAME, merAccount);
                    //跳转到
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    LoginActivity.this.finish();
                    InputMethod.closeInputMethod(LoginActivity.this);
                    break;
                default:
                    PromptManager.showToast(LoginActivity.this, event.msg);
                    break;
            }
        }
    }
}
