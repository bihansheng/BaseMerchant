/**
 * ***************************************************************************
 * Copyright (C) 2014 ShenZhen HeShiDai Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为合时代控股有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 * ***************************************************************************
 */
package com.heshidai.cdzmerchant.business.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.heshidai.cdzmerchant.R;
import com.heshidai.cdzmerchant.entity.AOrderRecordEntity;
import com.heshidai.cdzmerchant.utils.StringUtil;

/**
 * 验证弹出的对话框
 * @author lixiangxiang
 *         on 2015/10/12
 */
public class ValidateDialog  extends Dialog {

    private AOrderRecordEntity recordEntity;//对话框设置的对象
    private OnCustomDialogListener customDialogListener;//确定取消的回调接口

    private TextView tv_validate;//验证码
    private TextView tv_mobile;//手机号
    private TextView tv_money;//订单总额
    private TextView tvDiscountMoney;//代金券金额
    private TextView tvnNoMoney;//不优惠金额
    private Context context;


    public ValidateDialog(Context context, int theme) {
        super(context, theme);
        this.context =context;
    }

    /**
     * 确定取消的回调
     */
    public interface OnCustomDialogListener{
         void onCancelListener();
         void onPositiveListener();
    }

    public void setCustomDialogListener(AOrderRecordEntity recordEntity,OnCustomDialogListener customDialogListener) {
        this.customDialogListener = customDialogListener;
        this.recordEntity = recordEntity;
        initData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_tip);
        initView();
    }

    private void initData() {
        tv_validate.setText(recordEntity.checkCode);
        tv_money.setText(String.format("%s%s", StringUtil.fenToYuan(recordEntity.totalAmount),context.getResources().getString(R.string.dialog_yuan)));
        tv_mobile.setText(recordEntity.phone);
        tvnNoMoney.setText(String.format("%s%s", StringUtil.fenToYuan(recordEntity.nodiscAmount),context.getResources().getString(R.string.dialog_yuan)));
        tvDiscountMoney.setText(String.format("%s%s", StringUtil.fenToYuan(recordEntity.couponAmount),context.getResources().getString(R.string.dialog_yuan)));

    }

    private void initView() {
        tv_validate = (TextView)findViewById(R.id.tv_validate);
        tv_mobile = (TextView)findViewById(R.id.tv_mobile);
        tv_money = (TextView)findViewById(R.id.tv_money);
        tvnNoMoney = (TextView)findViewById(R.id.tv_no_money);
        tvDiscountMoney = (TextView)findViewById(R.id.tv_discount_money);
        TextView tv_positive = (TextView)findViewById(R.id.tv_positive);
        TextView tv_cancel = (TextView)findViewById(R.id.tv_cancel);
        tv_positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialogListener.onPositiveListener();
            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialogListener.onCancelListener();
            }
        });
    }
}
