/**
 * ***************************************************************************
 * Copyright (C) 2014 ShenZhen HeShiDai Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为合时代控股有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 * ***************************************************************************
 */
package com.heshidai.cdzmerchant.business.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.heshidai.cdzmerchant.R;
import com.heshidai.cdzmerchant.base.SimpleBaseAdapter;
import com.heshidai.cdzmerchant.utils.DisplayUtil;

import java.util.List;

/**
 *
 * @author lixiangxiang
 *         on 2015/10/21
 */
public class NumberAdapter extends BaseAdapter {

    private List<String> list ;

    private GridView gridView;

    private Context mContext;

    public NumberAdapter(Context context, List list,GridView gridview) {
        this.mContext=context;
        this.list=list;
        this.gridView =gridview;

    }

    @Override
    public int getCount() {
        if (list != null) {
            return list.size();
        } else {
            return 0;
        }
   }

    @Override
    public Object getItem(int position) {
        if (position >= list.size()) {
            return "";
        }
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == convertView) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_gridview_code, parent, false);
        }
        TextView textView = (TextView) convertView.findViewById(R.id.tv_every_code);
        if (position == list.size() - 1) {
            configHeight(convertView, 4, 2);
            textView.setTextColor(Color.WHITE);
            textView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.btn_shape_validate));
            textView.setWidth(DisplayUtil.dip2px(mContext, mContext.getResources().getDimension(R.dimen.validate_width)));
            textView.setHeight(DisplayUtil.dip2px(mContext, mContext.getResources().getDimension(R.dimen.validate_height)));

        } else {
            configHeight(convertView, 4, 1);
        }
        textView.setText(list.get(position));
        return convertView;
    }

    //动态设置每一个item 的宽高
    private void configHeight(View view, int y, int x) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = gridView.getHeight() / y;
        layoutParams.width = gridView.getWidth() / 3 * x;
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(layoutParams);
        view.setLayoutParams(params);
        this.notifyDataSetChanged();
    }
}
