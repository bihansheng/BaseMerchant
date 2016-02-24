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
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.heshidai.cdzmerchant.R;
import com.heshidai.cdzmerchant.base.BaseFragment;
import com.heshidai.cdzmerchant.business.main.MainActivity;
import com.heshidai.cdzmerchant.entity.OrderDetail;
import com.heshidai.cdzmerchant.utils.StringUtil;

import java.math.BigDecimal;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 类功能介绍。
 * 修改记录: 修改历史记录，包括修改日期、修改者及修改内容
 * 版权所有: 版权所有(C)2010-2014
 * 公　　司: 深圳合时代金融服务有限公司
 *
 * @author 万坤
 * @version 1.0.0
 * @date 2015-10-15
 * @history
 */


public class DetailFragment extends BaseFragment {
    @InjectView(R.id.tv_name)
    TextView mTvName;
    @InjectView(R.id.tv_check_code)
    TextView mTvCheckCode;
    @InjectView(R.id.tv_couponAmount)
    TextView mTvCouponAmount;
    @InjectView(R.id.tv_payAmount)
    TextView mTvPayAmount;
    @InjectView(R.id.tv_phone)
    TextView mTvPhone;
    @InjectView(R.id.tv_pay_time)
    TextView mTvPayTime;
    @InjectView(R.id.tv_use_time)
    TextView mTvUseTime;
    @InjectView(R.id.tv_title)
    TextView mTvTitle;
    @InjectView(R.id.toolbar)
    Toolbar mToolbar;
    @InjectView(R.id.tv_no_couponAmount)
    TextView tvNoCouponAmount;
    @InjectView(R.id.tv_totalAmount)
    TextView tvTotalAmount;

    public OrderDetail order;
    MainActivity mainActivity;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onViewCreated(container, savedInstanceState);
        View allView = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.inject(this, allView);
        mainActivity = (MainActivity) getActivity();
        setupToolbar();
        return allView;
    }


    public void setView(OrderDetail order) {
        //设置内容
        if (order != null) {//判断数据是否为空
            mTvName.setText(String.format(getResources().getString(R.string.order_detail_name), order.getNickname()));//用户昵称
            mTvCheckCode.setText(StringUtil.spanString(getResources().getString(R.string.order_detail_check_code), order.getCheckCode(), getResources().getColor(R.color.color_88), getResources().getColor(R.color.color_33), true));//验证码
            mTvCouponAmount.setText(StringUtil.spanString(getResources().getString(R.string.order_detail_couponAmount), StringUtil.fenToYuan(String.valueOf(order.getCouponAmount())) + "元", getResources().getColor(R.color.color_88), getResources().getColor(R.color.color_orange)));//代金券金额
            mTvPayAmount.setText(String.format(getResources().getString(R.string.order_detail_payAmount), StringUtil.fenToYuan(String.valueOf(order.getActualAmout().add(order.getNodiscAmount())))));//支付金额
            mTvPhone.setText(String.format(getResources().getString(R.string.order_detail_phone), TextUtils.isEmpty(order.getPhone()) ? "" : order.getPhone()));//手机号码
            mTvPayTime.setText(String.format(getResources().getString(R.string.order_detail_pay_time), TextUtils.isEmpty(order.getPayTime()) ? "" : order.getPayTime()));//购买时间
            mTvUseTime.setText(String.format(getResources().getString(R.string.order_detail_use_time), TextUtils.isEmpty(order.getUseTime()) ? "" : order.getUseTime()));//消费时间
            tvTotalAmount.setText(StringUtil.spanString(getResources().getString(R.string.order_detail_totalAmount),
                    (StringUtil.fenToYuan(String.valueOf(order.getTotalAmount())) == null ? 0 : StringUtil.fenToYuan(String.valueOf(order.getTotalAmount()))) + "元", getResources().getColor(R.color.color_88), getResources().getColor(R.color.color_orange)));
            tvNoCouponAmount.setText(StringUtil.spanString(getResources().getString(R.string.order_no_couponAmount),
                    (StringUtil.fenToYuan(String.valueOf(order.getNodiscAmount())) == null ? 0 : StringUtil.fenToYuan(String.valueOf(order.getNodiscAmount()))) + "元", getResources().getColor(R.color.color_88), getResources().getColor(R.color.color_orange)));
        }
    }

    /**
     * 设置标题栏
     */
    protected void setupToolbar() {
        if (mToolbar != null) {
            //设置标题
            mTvTitle.setText(getResources().getString(R.string.title_detail));
            mToolbar.setTitle("");
            mToolbar.setNavigationIcon(R.drawable.btn_selector_back);// 左边  抽屉栏图标,这里将这个改为返回按钮
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mainActivity != null) {
                        mainActivity.closeDetail();//跳转到historyFragment
                    }
                }
            });
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
