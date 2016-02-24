/**
 * **************************************************************************
 * Copyright (C) 2014 ShenZhen HeShiDai Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为合时代控股有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 * **************************************************************************
 */
package com.heshidai.cdzmerchant.business.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.heshidai.cdzmerchant.R;
import com.heshidai.cdzmerchant.base.SimpleBaseAdapter;
import com.heshidai.cdzmerchant.entity.OrderDetail;
import com.heshidai.cdzmerchant.utils.StringUtil;

import java.util.List;

/**
 * 类功能介绍。
 * 修改记录: 修改历史记录，包括修改日期、修改者及修改内容
 * 版权所有: 版权所有(C)2010-2014
 * 公　　司: 深圳合时代金融服务有限公司
 *
 * @author 万坤
 * @version 1.0.0
 * @date 2015-10-09
 * @history
 */
public class HistoryAdapter extends SimpleBaseAdapter {
    private Context mContext;
    private List<OrderDetail> data;
    public int total;

    public HistoryAdapter(Context context, List<OrderDetail> data,int total) {
        super(context, data);
        this.data = data;
        this.mContext = context;
        this.total = total;

    }

    @Override
    public int getItemResource() {
        return R.layout.item_history;
    }

    @Override
    public View getItemView(int position, View convertView, ViewHolder holder) {
        if (position == 0) {//合计验券
            convertView = View.inflate(mContext, R.layout.item_history_total, null);
            convertView.setLayoutParams(new  android.widget.AbsListView.LayoutParams(android.widget.AbsListView.LayoutParams.MATCH_PARENT, (int) mContext.getResources().getDimension(R.dimen.item_history_total_height)));//注意这里控件的高度要和悬浮框高度一致
           TextView tvTotal = (TextView) convertView.findViewById(R.id.history_total);
            tvTotal.setText(String.format( mContext.getResources().getString(R.string.history_total),total));
            convertView.setTag(null);
        } else {//验券记录
            OrderDetail order = (OrderDetail) getItem(position);
            TextView tvCode = (TextView) holder.getView(R.id.tv_code);//券号
            TextView tvAmount = (TextView) holder.getView(R.id.tv_amount);//订单总金额
            TextView tvTime = (TextView) holder.getView(R.id.tv_time);//消费时间
            tvCode.setText(order.getCheckCode());
            tvAmount.setText(String.format(mContext.getResources().getString(R.string.order_amount),  StringUtil.fenToYuan(String.valueOf(order.getTotalAmount()))));
            tvTime.setText((TextUtils.isEmpty(order.getUseTime())?"":order.getUseTime()));
        }
        return convertView;
    }

    /**
     * 设置总数
     * @param t
     */
    public void setTotal(int t) {
        total = t;
    }
}
