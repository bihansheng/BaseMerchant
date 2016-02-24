/******************************************************************************
 * Copyright (C) 2014 ShenZhen HeShiDai Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为合时代控股有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.heshidai.cdzmerchant.utils;

import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

/**
 * 自定义动画
 * @author 万坤
 * @version 1.0.0
 * @date 2015-06-5
 * @history 创建
 */

public class MyAnimations {

    static AnimationSet setanimation() {
        AnimationSet set = new AnimationSet(false);
        // 透明度动画
        AlphaAnimation aa = new AlphaAnimation(1.0f, 0);
        aa.setDuration(1000);
        // 位移动画
        TranslateAnimation ta = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT,
                0, Animation.RELATIVE_TO_PARENT, 0,
                Animation.RELATIVE_TO_PARENT, -0.3f);
        ta.setDuration(1000);
        // 设置最后停止时的状态
        set.setFillAfter(true);
        set.setFillEnabled(true);
        set.addAnimation(ta);
        set.addAnimation(aa);
        return set;
    }

    /**
     * 旋转180度动画
     */
    public static RotateAnimation mRotateOTo180Animation;

    public static void setAnimiDown(ImageView imageView) {
        mRotateOTo180Animation = new RotateAnimation(0f, 180f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);// 0.5f
        mRotateOTo180Animation.setDuration(200);
        mRotateOTo180Animation.setFillAfter(true);
        mRotateOTo180Animation.setInterpolator(new LinearInterpolator());
        imageView.startAnimation(mRotateOTo180Animation);
    }

    public static void setAnimiUp(ImageView imageView) {
        mRotateOTo180Animation = new RotateAnimation(180f, 0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);// 0.5f
        mRotateOTo180Animation.setDuration(200);
        mRotateOTo180Animation.setFillAfter(true);
        mRotateOTo180Animation.setInterpolator(new LinearInterpolator());
        imageView.startAnimation(mRotateOTo180Animation);
    }

    /**
     * 设置为匀速旋转动画
     */

    public static  void  setRotateAnimation(ImageView imageView){
        RotateAnimation ra = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        //动画播放的时间
        ra.setDuration(1500);
        //重复次数
        ra.setRepeatCount(200);
        ra.setRepeatMode(Animation.RESTART );
        LinearInterpolator lir = new LinearInterpolator();//设置为匀速旋转
        ra.setInterpolator(lir);
        imageView.startAnimation(ra);
    }
}
