/******************************************************************************
 * Copyright (C) 2014 ShenZhen HeShiDai Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为合时代控股有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.heshidai.cdzmerchant.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.heshidai.cdzmerchant.R;
import com.heshidai.cdzmerchant.utils.MyAnimations;


/**
 * gif图片播放
 * 修改记录:修改历史记录，包括修改日期、修改者及修改内容
 * 版权所有:版权所有(C)2010-2014
 * 公　　司:深圳合时代金融服务有限公司
 *
 * @author 万坤
 * @version 1.0.0
 * @date 2015-04-29
 * @history 创建
 */
public class LoadingView extends LinearLayout {
    /**
     * menu的文字
     */
    private TextView mTvMenu;
    /**
     * menu的图片
     */
    private ImageView mIVMenu;

    public LoadingView(Context context) {
        super(context);
        init(context, null);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    /**
     * 初始化View
     */
    private void init(Context context, AttributeSet attrs) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.view_loading, this, true);
        mTvMenu = (TextView) findViewById(R.id.tv_text);
        mIVMenu= (ImageView) findViewById(R.id.iv_image);
        MyAnimations.setRotateAnimation(mIVMenu);
    }

    /**
     * 停止动画
     */
    public void stopAnimations(){
        MyAnimations.setAnimiDown(mIVMenu);
    }

}
