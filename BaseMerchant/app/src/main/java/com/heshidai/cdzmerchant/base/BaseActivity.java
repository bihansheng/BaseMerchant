/**
 * ***************************************************************************
 * Copyright (C) 2014 ShenZhen HeShiDai Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为合时代控股有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 * ***************************************************************************
 */
package com.heshidai.cdzmerchant.base;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.heshidai.cdzmerchant.R;
import com.heshidai.cdzmerchant.app.HSDAppManager;
import com.heshidai.cdzmerchant.app.HSDApplication;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;
import de.greenrobot.event.EventBus;


/**
 * Activity父类
 *
 * @author 万坤
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    @Optional
    @InjectView(R.id.toolbar)
    Toolbar toolbar;//标题栏
    @Optional
    @InjectView(R.id.tv_title)
    TextView mTvTitle;//标题

    public HSDApplication mApplication;
    public Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        HSDAppManager.getInstance().addActivity(BaseActivity.this);//activity栈管理
        mApplication = HSDApplication.getApplication();
        EventBus.getDefault().register(this);  //注册EventBus
    }

    /**
     * 设置布局文件，并设置标题
     * @param layoutResID
     */
    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.inject(this);//设置ButterKnife
        setupToolbar();////填充默认标题
    }

    /**
     * 设置布局文件，不设置标题
     * @param layoutResId
     */
    public void setContentViewWithoutToolBar(int layoutResId) {
        super.setContentView(layoutResId);
        ButterKnife.inject(this);//设置ButterKnife
    }

    /**
     * 设置标题栏（这里将菜单按钮改为了返回按钮）
     */
    protected void setupToolbar() {
        if (toolbar != null) {
            toolbar.setTitle("");
            setSupportActionBar(toolbar);
            toolbar.setNavigationIcon(R.drawable.btn_selector_back);// 左边  抽屉栏图标,这里将这个改为返回按钮
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

    /**
     * 隐藏右边返回按钮
     */
    public void displayBack() {
        if (toolbar != null) {
            toolbar.setNavigationIcon(null);
        }
    }
//    /**
//     * 设置标题栏  右边 按钮（根据具体页面继承方法添加）
//     */
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        inboxMenuItem = menu.findItem(R.id.action_inbox);
//        inboxMenuItem.setActionView(R.layout.menu_item_view);//默认右边的图标是白色空白的
//        return true;
//    }

    /**
     * 获取标题栏控件
     * @return
     */
    public Toolbar getToolbar() {
        return toolbar;
    }


    /**
     * 接收eventBus事件，（这里是为了防止子类中没有接收方法而报错，子类不需要继承该方法）
     * @param event
     */
    public void onEventMainThread(String event) { }


    /**
     * 点击EditText以外的任何区域隐藏键盘
     *
     * @param event 用户事件
     * @return 处理结果
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            // 获得当前得到焦点的View，一般情况下就是EditText（特殊情况就是轨迹求或者实体案件会移动焦点）
            View v = getCurrentFocus();
            if (isShouldHideInput(v, event)) {
                hideSoftInput(v.getWindowToken());
            }
        }
        return super.onTouchEvent(event);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时没必要隐藏
     *
     * @param v     视图
     * @param event 事件
     * @return 是否需要隐藏键盘
     */
    private boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationOnScreen(l);
            int left = l[0], top = l[1], bottom = top + v.getMeasuredHeight(), right = left + v.getMeasuredWidth();
            return !(event.getX() >= left && event.getX() <= right && event.getY() >= top && event.getY() <= bottom);
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 隐藏键盘
     */
    private void hideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    protected void onDestroy() {
        //取消注册EventBus
        EventBus.getDefault().unregister(this);
        HSDAppManager.getInstance().finishActivity(BaseActivity.this);
        super.onDestroy();
    }


}
