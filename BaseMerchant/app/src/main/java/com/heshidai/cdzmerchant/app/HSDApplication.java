/****************************************************************************
 * Copyright (C) 2014 ShenZhen HeShiDai Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为合时代控股有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 * **************************************************************************/
package com.heshidai.cdzmerchant.app;

import android.app.Application;
import android.graphics.Bitmap;

import com.heshidai.cdzmerchant.R;
import com.heshidai.cdzmerchant.common.Constant;
import com.heshidai.cdzmerchant.net.HVolley;
import com.heshidai.cdzmerchant.utils.FileUtil;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import cn.jpush.android.api.JPushInterface;

/**
 * Application类
 */
public class HSDApplication extends Application {

    private static HSDApplication mApplication;
    private static String token = "";//token全局

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        //创建本地文件夹
        FileUtil.createDirectory(this);
        // 注册App异常崩溃处理器
        HSDAppCrashHandler crashHandler = new HSDAppCrashHandler(mApplication);
        Thread.setDefaultUncaughtExceptionHandler(crashHandler);
        //初始化Volley
        HVolley.getInstance();
        //初始化imageLoader
        initImageLoader();
        //设置极光推送
        JPushInterface.setDebugMode(Constant.IS_LOGCAT);
        JPushInterface.init(getApplicationContext());
    }

    /**
     * 获取application实例
     *
     * @return
     */
    public static HSDApplication getApplication() {
        return mApplication;
    }

    /**
     * 设置token
     * @param t
     */
    public static void setToken(String t){
        token = t;
    }

    /**
     * 获取Token
     * @return
     */
    public static String getToken(){
        return token;
    }

    /**
     * 初始化图片缓存工具
     */
    private static void initImageLoader() {
        long maxMemory = Runtime.getRuntime().maxMemory();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplication())
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .defaultDisplayImageOptions(getOptions(R.mipmap.default_img_small))
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache((int)(maxMemory / 8)))
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCacheSize(Constant.MAX_DISK_CACHE) // 50 Mb
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .build();
        ImageLoader.getInstance().init(config);
    }

    /**
     * 获取图片配置参数
     * @param imageRes 默认图片
     * @return
     */
    public static DisplayImageOptions getOptions(int imageRes) {
        return new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.EXACTLY)//设置图片以如何的编码方式显示 EXACTLY_STRETCHED:图片会缩放到目标大小完全
                .bitmapConfig(Bitmap.Config.RGB_565)//设置图片的解码类型
                .showImageOnLoading(imageRes) //设置图片在下载期间显示的图片
                .showImageForEmptyUri(imageRes)//设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(imageRes)//设置图片加载/解码过程中错误时候显示的图片
                .build();
    }

}
