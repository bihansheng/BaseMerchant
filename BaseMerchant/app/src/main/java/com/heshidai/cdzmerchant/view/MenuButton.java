/******************************************************************************
 * Copyright (C) 2014 ShenZhen HeShiDai Technology Co.,Ltd
 * All Rights Reserved.
 *****************************************************************************/
package com.heshidai.cdzmerchant.view;


import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.heshidai.cdzmerchant.R;
/**
 * 首页页面切换按钮
 * 修改记录:修改历史记录，包括修改日期、修改者及修改内容
 * 版权所有:版权所有(C)2010-2014
 * 公　　司:深圳合时代金融服务有限公司
 *
 *
 * @author 万坤
 * @version 1.0.0
 * @date 2015-04-29
 * @history 创建
 */
public class MenuButton extends LinearLayout {
    /**
     * menu的文字
     */
    private TextView mTvMenu;
    /**
     * menu的图片
     */
    private ImageView mIVMenu;

    private LinearLayout mMenuBox;


    public MenuButton(Context context) {
        super(context);
        init(context, null);
    }

    public MenuButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    /**
     * 设置选中状态
     */
    public void setSelected(boolean selected){
        mMenuBox.setSelected(selected);
    }

    /**
     * 初始化View
     */
    private void init(Context context,  AttributeSet attrs){
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.view_menu_button, this, true);

        mTvMenu = (TextView) findViewById(R.id.menu_name);
        mIVMenu= (ImageView) findViewById(R.id.menu_image);
        mMenuBox = (LinearLayout) findViewById(R.id.menu_box);
        TypedArray array = context.obtainStyledAttributes(attrs,
                R.styleable.MenuButton);

        int nameRes = array.getResourceId(R.styleable.MenuButton_menuText, -1);
        int imageRes = array.getResourceId(R.styleable.MenuButton_menuImage,-1);

        if (nameRes != -1) {
            mTvMenu.setText(nameRes);
        }
        if (imageRes != -1) {
            mIVMenu.setImageResource(imageRes);
        }
        array.recycle();
    }
}
