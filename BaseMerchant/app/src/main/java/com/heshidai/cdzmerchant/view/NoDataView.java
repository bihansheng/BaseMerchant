/******************************************************************************
 * Copyright (C) 2014 ShenZhen HeShiDai Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为合时代控股有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.heshidai.cdzmerchant.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.heshidai.cdzmerchant.R;


/**
 * 显示没有数据
 * 修改记录:修改历史记录，包括修改日期、修改者及修改内容
 * 版权所有:版权所有(C)2010-2014
 * 公　　司:深圳合时代金融服务有限公司
 *
 * @author 万坤
 * @version 1.0.0
 * @date 2015-06-26
 * @history 创建
 */
public class NoDataView extends RelativeLayout {

    /**
     * 文字
     */
    private TextView mTvText;
    /**
     * 图片
     */
    private ImageView mIVImage;
    private RelativeLayout mEmptyView;

    public NoDataView(Context context) {
        super(context);
        init(context, null);
    }

    public NoDataView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }



    /**
     * 初始化View
     */
    private void init(Context context,  AttributeSet attrs){
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.view_no_data, this, true);

        mTvText = (TextView) findViewById(R.id.tv_text);
        mIVImage= (ImageView) findViewById(R.id.tv_img);
        mEmptyView = (RelativeLayout) findViewById(R.id.empty_view);
        TypedArray array = context.obtainStyledAttributes(attrs,
                R.styleable.NoDataView);
        int nameRes = array.getResourceId(R.styleable.NoDataView_mText, -1);
        int imageRes = array.getResourceId(R.styleable.NoDataView_mImage,-1);
        int backColorRes = array.getResourceId(R.styleable.NoDataView_mBackColor,-1);

        if (nameRes != -1) {
            mTvText.setText(nameRes);
        }
        if (imageRes != -1) {
            mIVImage.setImageResource(imageRes);
        }
        if (backColorRes != -1) {
            mEmptyView.setBackgroundColor(getResources().getColor(backColorRes));
        }else{
            mEmptyView.setBackgroundColor(getResources().getColor(R.color.color_white));
        }
        array.recycle();
    }

    /**
     * 设文字和名称
     * @param name 名称
     * @param image 图片路径
     */
    public void setView(String name,int image){
        mTvText.setText(name);
        mIVImage.setImageResource(image);
    }

    /**
     * 设文字和名称
     * @param name 名称id
     * @param image 图片路径
     */
    public void setView(int name,int image){
        mTvText.setText(name);
        mIVImage.setImageResource(image);
    }
}
