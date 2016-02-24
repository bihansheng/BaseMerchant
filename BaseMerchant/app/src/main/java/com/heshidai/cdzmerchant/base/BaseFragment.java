/**
 * **************************************************************************
 * Copyright (C) 2014 ShenZhen HeShiDai Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为合时代控股有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 * **************************************************************************
 */
package com.heshidai.cdzmerchant.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.heshidai.cdzmerchant.app.HSDApplication;

import de.greenrobot.event.EventBus;

/**
 * Fragment基类
 * @author 万坤
 */
public abstract class BaseFragment extends Fragment implements View.OnClickListener {
    public HSDApplication mApplication;
    public Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() != null) {
            mContext = getActivity();
        }
        mApplication = HSDApplication.getApplication();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    /**
     * 注意：在fragment中eventBus的注册需要在onCreateView方法之后即onActivityCreated进行，防止view空指针
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        super.onActivityCreated(savedInstanceState);
    }

    public void onEventMainThread(String event) {
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this); //取消注册EventBus
        super.onDestroy();
    }
}
