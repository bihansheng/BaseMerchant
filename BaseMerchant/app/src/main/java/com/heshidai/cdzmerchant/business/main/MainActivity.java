/*****************************************************************************
 * Copyright (C) 2015 ShenZhen HeShiDai Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为合时代控股有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 * ***************************************************************************/
package com.heshidai.cdzmerchant.business.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;

import com.heshidai.cdzmerchant.R;
import com.heshidai.cdzmerchant.app.HSDAppManager;
import com.heshidai.cdzmerchant.base.BaseActivity;
import com.heshidai.cdzmerchant.business.callback.UpdateCallBack;
import com.heshidai.cdzmerchant.business.fragment.DetailFragment;
import com.heshidai.cdzmerchant.business.fragment.HistoryFragment;
import com.heshidai.cdzmerchant.business.fragment.ProvingFragment;
import com.heshidai.cdzmerchant.business.manager.UpdateManager;
import com.heshidai.cdzmerchant.entity.OrderDetail;
import com.heshidai.cdzmerchant.entity.Update;
import com.heshidai.cdzmerchant.utils.PromptManager;
import com.heshidai.cdzmerchant.view.MenuButton;

/**
 * 主页
 * @author 万坤
 */
public class MainActivity extends BaseActivity {
    private ProvingFragment mProvingFragment;//代金券验证Fragment
    private HistoryFragment mHistoryFragment;//历史记录Fragment
    private DetailFragment mDetailFragment;//历史记录Fragment
    private MenuButton mMenuMain,  mMenuUser;//底部切换按钮

    private long firsTime = 0;
    private int fragementId;
    private UpdateManager updateManager;
    
    private final  int DETAIL = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentViewWithoutToolBar(R.layout.activity_main);
        initLayout(savedInstanceState);
        checkUpdate();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        checkUpdate();
    }

    /**
     * 检查更新
     */
    private void checkUpdate() {
        updateManager = UpdateManager.getInstance();
        updateManager.init(MainActivity.this);
        updateManager.checkUpdate(false, new UpdateCallBack() {
            @Override
            public void setUpdateCallBack(boolean hasNewVersion, Update update) {
                if (update != null) {
                    updateManager.updateApkVersion(false, hasNewVersion, update);
                }
            }
        });
    }

    /**
     * 初始化布局
     */
    private void initLayout(Bundle savedInstanceState) {
        mMenuMain = (MenuButton) findViewById(R.id.proving_menu);
        mMenuMain.setOnClickListener(this);
        mMenuUser = (MenuButton) findViewById(R.id.history_menu);
        mMenuUser.setOnClickListener(this);

        if (savedInstanceState == null) {//如果没有记录位置，显示首页
            switchMenuStatus(R.id.proving_menu);
            switchContent(R.id.proving_menu);
        } else {//如果有记录位置，显示记录的位置
            int id = savedInstanceState.getInt("fragment_id");
            switchMenuStatus(id);
            switchContent(id);
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switchMenuStatus(id);
        switchContent(id);
    }

    /**
     * 切换底部菜单的选中状态
     *
     * @param id 选中的id
     */
    private void switchMenuStatus(int id) {
        switch (id) {
            case R.id.proving_menu:
                mMenuMain.setSelected(true);
                mMenuUser.setSelected(false);
                break;
            case R.id.history_menu:
                mMenuMain.setSelected(false);
                mMenuUser.setSelected(true);
                break;
        }
    }

    /**
     * 切换Fragment存放
     */
    public void switchContent(int id) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        hideAll(manager, transaction);
        switch (id) {
            case R.id.proving_menu:
                transaction.show(mProvingFragment);
                break;
            case R.id.history_menu:
                transaction.show(mHistoryFragment);
                break;
            case DETAIL:
                transaction.show(mDetailFragment);
                break;
        }
        transaction.commit();
        fragementId = id;
    }
    /**
     * 显示详情
     * @param orderDetail
     */
    public void showDetail(OrderDetail orderDetail){
        switchContent(DETAIL);
        mDetailFragment.setView(orderDetail);

    }

    /**
     * 关闭详情
     */
    public void  closeDetail() {
        //mDetailFragment = null;//这里为了每次进入fragment都会重置fragment，把它设置为空，激活创建方法
        switchContent(R.id.history_menu);
        switchMenuStatus(R.id.history_menu);
    }

    /**
     * 隐藏所有Fragment,如果已经被回收，先创建再隐藏
     */
    private void hideAll(FragmentManager manager, FragmentTransaction transaction) {
        mProvingFragment = (ProvingFragment) manager.findFragmentByTag("PROVING");
        if (mProvingFragment == null) {
            mProvingFragment = new ProvingFragment();
            transaction.add(R.id.content, mProvingFragment, "PROVING");
        }
        mHistoryFragment = (HistoryFragment) manager.findFragmentByTag("HISTORY");
        if (mHistoryFragment == null) {
            mHistoryFragment = new HistoryFragment();
            transaction.add(R.id.content, mHistoryFragment, "HISTORY");
        }
        if (mDetailFragment == null) {
            mDetailFragment = new DetailFragment();
            transaction.add(R.id.content, mDetailFragment, "DETAIL");
        }
        transaction.hide(mHistoryFragment);
        transaction.hide(mProvingFragment);
        transaction.hide(mDetailFragment);
    }

    /**
     * 连续点击弹出退出提示
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {//提示用户退出程序
            if ((System.currentTimeMillis() - firsTime) > 2000) {
                PromptManager.showToast(MainActivity.this, R.string.out_tips);
                firsTime = System.currentTimeMillis();
            } else {
                HSDAppManager.getInstance().appExit(true);
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
