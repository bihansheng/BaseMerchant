/**
 * **************************************************************************
 * Copyright (C) 2014 ShenZhen HeShiDai Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为合时代控股有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 * **************************************************************************
 */
package com.heshidai.cdzmerchant.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.heshidai.cdzmerchant.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 自定义下拉刷新控件（主要集成了网络不可用事件，点击事件，暂无数据，上拉加载相关数据处理事件）
 * 修改记录: 修改历史记录，包括修改日期、修改者及修改内容
 * 版权所有: 版权所有(C)2010-2014
 * 公　　司: 深圳合时代金融服务有限公司
 *
 * @author 万坤
 * @version 1.0.0
 * @date 2015-10-26
 * @history
 */


public class PullToRefreshList extends RelativeLayout {
    //刷新模式 0 点击刷新  1 下拉刷新  2 上拉加载
    public static final int CLICK_TO_REFRESH = 0;
    public static final int PULL_DOWN_TO_REFRESH = 1;
    public static final int PULL_UP_TO_REFRESH = 2;
    private int refreshMode;
    private int pageNum = 1;
    private RefreshListener mRefreshListener;

    @InjectView(R.id.view_pull_refresh_list)
    PullToRefreshListView mPullRefreshList;//开源下拉刷新控件
    @InjectView(R.id.no_data)
    NoDataView mNoData;//没有数据
    @InjectView(R.id.loading)
    LoadingView mLoading;//加载动画
    @InjectView(R.id.network_unavailable)
    RelativeLayout mNetworkUnavailable;//网络连接失败提示

    public PullToRefreshList(Context context) {
        super(context);
        init(context, null);
    }

    public PullToRefreshList(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public PullToRefreshList(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PullToRefreshList(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    /**
     * 初始化View
     */
    private void init(Context context, AttributeSet attrs) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.view_pull_to_refresh, this, true);
        ButterKnife.inject(this);

        // 设置PullToRefresh ( Mode.BOTH：同时支持上拉下拉   Mode.PULL_FROM_START：只支持下拉Pulling Down  Mode.PULL_FROM_END：只支持上拉Pulling Up)
        mPullRefreshList.setMode(PullToRefreshBase.Mode.BOTH);
        mPullRefreshList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {  // 下拉Pulling Down
                refreshMode = PULL_DOWN_TO_REFRESH;
                getDataFormService(PULL_DOWN_TO_REFRESH);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) { // 上拉Pulling Up
                refreshMode = PULL_UP_TO_REFRESH;
                getDataFormService(PULL_UP_TO_REFRESH);
            }
        });
        mPullRefreshList.isScrollingWhileRefreshingEnabled();  // 设置加载时是否可以滚动
        mPullRefreshList.setScrollingWhileRefreshingEnabled(false);
        mPullRefreshList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mRefreshListener.onItemClick(position, view);
            }
        });
        //设置网络不可用时重新点击事件
        mNetworkUnavailable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPullRefreshList.setVisibility(View.GONE);
                mNetworkUnavailable.setVisibility(GONE);
                mLoading.setVisibility(View.VISIBLE);
                getDataFormService(refreshMode);
            }
        });

    }

    /**
     * 设置网络请求方法
     */
    public void getDataFormService(int mode) {
        refreshMode = mode;
        if (mode == CLICK_TO_REFRESH) {   //如果是点击加载，显示加载动画并隐藏列表
            mPullRefreshList.setVisibility(View.GONE);
            mLoading.setVisibility(View.VISIBLE);
        }
        if (mode != PULL_UP_TO_REFRESH) {
            pageNum = 1;// // 在这里设置PageNum ，防止多次请求PageNum混乱
        }
        mRefreshListener.requestNetworkMethod(mode,pageNum);
    }

    /**
     * 网络请求完成
     */
    public void onRequestComplete(boolean requestSuccess) {
        //加载完成，显示列表并隐藏加载动画
        mLoading.setVisibility(View.GONE);
        mPullRefreshList.onRefreshComplete();//结束刷新操作
        if (requestSuccess) {//请求成功,设置数据
            mPullRefreshList.setVisibility(View.VISIBLE);
            mNoData.setVisibility(View.VISIBLE);
            mPullRefreshList.setEmptyView(mNoData);
            mNetworkUnavailable.setVisibility(View.GONE);
        } else {//请求失败，显示网络错误提示
            mNetworkUnavailable.setVisibility(View.VISIBLE);
            mPullRefreshList.setVisibility(View.GONE);
        }
    }

    /**
     * 设置是否还可以加载更多
     */
    public void setPullDownable(boolean can) {
        if (can) {
            mPullRefreshList.setMode(PullToRefreshBase.Mode.BOTH);
            ++pageNum;// 在这里增加PageNum防止短时间内多次请求使PageNum混乱
        } else {
            mPullRefreshList.setMode(PullToRefreshBase.Mode.PULL_DOWN_TO_REFRESH);
        }
    }

    /**
     * 设置适配器
     */
    public void setAdapter(BaseAdapter adapter) {
        mPullRefreshList.setAdapter(adapter);
    }

    /**
     * 获取刷新控件
     */
    public PullToRefreshListView getPullRefreshList() {
        return mPullRefreshList;
    }

    /**
     * 设置请求模式
     */
    public void setRefreshMode(int refreshMode) {
        this.refreshMode = refreshMode;
    }
    /**
     * 获取当前请求模式
     */
    public int getRefreshMode() {
        return refreshMode;
    }

    /**
     * 判断刷新模式是否是上拉加载
     */
    public boolean isPullUp() {
        return refreshMode == PULL_UP_TO_REFRESH;
    }

    /**
     * 判断刷新模式是否是下拉刷新
     */
    public boolean isPullDown() {
        return refreshMode == PULL_DOWN_TO_REFRESH;
    }

    /**
     * 判断刷新模式是否是下拉刷新
     */
    public boolean isOnClick() {
        return refreshMode == CLICK_TO_REFRESH;
    }


    /**
     * 获取当前请求的页码
     */
    public int  getPageNum() {
        return pageNum;
    }

    /**
     * 设置监听接口
     */
    public void setRefreshListener(RefreshListener mRefreshListener) {
        this.mRefreshListener = mRefreshListener;
    }

    /**
     * ItemClick的回调接口
     */
    public interface RefreshListener {
        void requestNetworkMethod(int refreshMode,int pageNum);//网络请求方法

        void onItemClick(int position, View view);//item点击事件
    }

}
