/**
 * **************************************************************************
 * Copyright (C) 2014 ShenZhen HeShiDai Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为合时代控股有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 * **************************************************************************
 */
package com.heshidai.cdzmerchant.business.fragment;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.heshidai.cdzmerchant.R;
import com.heshidai.cdzmerchant.app.HSDApplication;
import com.heshidai.cdzmerchant.base.BaseFragment;
import com.heshidai.cdzmerchant.business.adapter.HistoryAdapter;
import com.heshidai.cdzmerchant.business.main.MainActivity;
import com.heshidai.cdzmerchant.business.manager.CouponManager;
import com.heshidai.cdzmerchant.common.Constant;
import com.heshidai.cdzmerchant.common.SPManager;
import com.heshidai.cdzmerchant.entity.OrderDetail;
import com.heshidai.cdzmerchant.entity.OrderList;
import com.heshidai.cdzmerchant.entity.User;
import com.heshidai.cdzmerchant.event.CouponEvent;
import com.heshidai.cdzmerchant.utils.DesUtil;
import com.heshidai.cdzmerchant.utils.PreferencesUtils;
import com.heshidai.cdzmerchant.utils.log.HLog;
import com.heshidai.cdzmerchant.view.MyCalendar;
import com.heshidai.cdzmerchant.view.PullToRefreshList;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class HistoryFragment extends BaseFragment {
    @InjectView(R.id.toolbar)
    Toolbar mToolbar;
    @InjectView(R.id.tv_title)
    TextView mTvTitle;//标题
    @InjectView(R.id.mc_calendar)
    MyCalendar mMcCalendar;//自定义日历控件
    @InjectView(R.id.pull_refresh_list)
    PullToRefreshList mPullRefreshList;//下拉刷新控件
    @InjectView(R.id.history_total)
    TextView mHistoryTotal;//悬浮的统计
    @InjectView(R.id.history_total_box)
    RelativeLayout mHistoryTotalBox;//悬浮的统计view

    private List<OrderDetail> OrderDetails; //数据集合
    private HistoryAdapter mAdapter;
    private int total;//总数
    private MainActivity mainActivity;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onViewCreated(container, savedInstanceState);
        View allView = inflater.inflate(R.layout.fragment_history, container, false);
        ButterKnife.inject(this, allView);
        mainActivity = (MainActivity) getActivity();
        initView();//初始页面
        mPullRefreshList.getDataFormService(PullToRefreshList.CLICK_TO_REFRESH);//日历初始化后默认选择今天，所以初始时获取今天的数据
        return allView;
    }

    /**
     * 初始布局
     */
    private void initView() {
        //设置标题
        mTvTitle.setText(getResources().getString(R.string.menu_history));

        //设置下拉刷新监听事件
        mPullRefreshList.setRefreshListener(new PullToRefreshList.RefreshListener() {
            @Override
            public void requestNetworkMethod(int refreshMode, int pageNum) {//设置刷新时网络请求方法
                mHistoryTotalBox.setVisibility(View.GONE);
                User user = (User) PreferencesUtils.readObject(HSDApplication.getApplication(), PreferencesUtils.PREFERENCE_NAME_DATA, SPManager.USER);
                if (user == null || TextUtils.isEmpty(user.getMerId())) {
                    HLog.e(Constant.TAG, "user对象数据错误");
                    return;
                }
                CouponManager.getConsumeDetailList(mContext, DesUtil.decrypt(user.getMerId()), mMcCalendar.getTheDayOfSelected(), mMcCalendar.getTheDayOfSelected(), pageNum);
            }

            @Override
            public void onItemClick(int position, View view) {//设置item点击
                if (position - 1 != 0) {
                    //跳转到详情fragment
                    if (mainActivity != null) {
                        mainActivity.showDetail(OrderDetails.get(position - 1));
                    }
                }
            }
        });
        mPullRefreshList.getPullRefreshList().setOnScrollListener(new AbsListView.OnScrollListener() {//设置滚动时显示/隐藏悬浮view
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem == 0) {//当listView置顶时（firstVisibleItem=0）隐藏悬浮视图
                    mHistoryTotalBox.setVisibility(View.GONE);
                } else {
                    mHistoryTotalBox.setVisibility(View.VISIBLE);
                }
            }
        });

        //设置日历点击事件
        mMcCalendar.setOnItemClickLitener(new MyCalendar.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                mPullRefreshList.getDataFormService(PullToRefreshList.CLICK_TO_REFRESH);//请求网络
            }
        });


        // 设置标题栏  “今天”按钮
        mToolbar.inflateMenu(R.menu.menu_main);
        MenuItem item = mToolbar.getMenu().findItem(R.id.action_inbox);
        item.setActionView(R.layout.menu_item_today);
        item.getActionView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mMcCalendar.showToday()) { //跳转到今天
                    mPullRefreshList.getDataFormService(PullToRefreshList.CLICK_TO_REFRESH);
                }
            }
        });

    }

    /**
     * 开机启动相关数据请求
     */
    public void onEventMainThread(CouponEvent event) {
        switch (event.method) {
            case CouponManager.GET_CONSUME_DETAIL_LIST_TAG:
                mPullRefreshList.onRequestComplete(event.code == 0);//设置网络请求结束显示
                if (event.code == 0) {
                    initMerView((OrderList) event.obj);
                } else {
                    mHistoryTotalBox.setVisibility(View.GONE);
                }
                break;
            default:
                break;
        }
    }

    /**
     * 验证完成后，如果被选中的时间是今天，刷新数据
     *
     * @param event
     */
    public void onEventBackgroundThread(CouponEvent event) {
        if (event.method == CouponManager.UPDATA_TAG && mMcCalendar.isTodayIsSelectedDay()) {//如果被选中是今天，刷新数据
            mPullRefreshList.getDataFormService(PullToRefreshList.PULL_DOWN_TO_REFRESH);
        }

    }

    /**
     * 设置商家数据列表
     */
    private void initMerView(OrderList orderList) {
        List<OrderDetail> list = orderList.getCouponList();
        //初始化
        if (mAdapter == null) {
            mAdapter = new HistoryAdapter(mContext, list, total);
            mPullRefreshList.setAdapter(mAdapter);
            OrderDetails = new ArrayList<>();
        }
        //数据总数
        total = orderList.getTotal();
        mAdapter.setTotal(total);
        mHistoryTotal.setText(String.format(mContext.getResources().getString(R.string.history_total), total));//设置悬浮控件的数据

        //添加数据
        if (mPullRefreshList.isPullUp()) {//上拉加载添加数据
            OrderDetails.addAll(list);
            mAdapter.addAll(list);
        } else {//其他的替换数据
            OrderDetails.clear();
            if (list.size() > 0) {//注意OrderDetails中的第一个是totalView，所以OrderDetails的第一条数据时空数据
                list.add(0, new OrderDetail());
            }
            OrderDetails.addAll(list);
            mAdapter.replaceAll(list);
        }
        mPullRefreshList.setPullDownable(OrderDetails.size() != 0 && total > OrderDetails.size() - 1);//设置是否可以上拉加载
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}