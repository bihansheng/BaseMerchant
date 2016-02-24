/****************************************************************************
 * Copyright (C) 2014 ShenZhen HeShiDai Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为合时代控股有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 **************************************************************************/
package com.heshidai.cdzmerchant.app;

import android.app.Activity;

import com.heshidai.cdzmerchant.net.NetworkRequest;
import com.heshidai.cdzmerchant.utils.log.HLogToFileQueue;

import java.util.Stack;

/**
 * 应用程序Activity管理类
 * @author xiao di fa
 */
public class HSDAppManager {

    private static Stack<Activity> activityStack;
    private static HSDAppManager instance;

    private HSDAppManager() {
        if (null == activityStack) {
            activityStack = new Stack<>();
        }
    }

    public synchronized static HSDAppManager getInstance() {
        if (instance == null) {
            instance = new HSDAppManager();
        }
        return instance;
    }

    /**
     * 添加Activity
     * @param activity Activity
     */
    public void addActivity(Activity activity) {
        activityStack.add(activity);
    }

    /**
     * 判断APP是否正在运行
     * @return
     */
    public boolean isAppRunning() {
        if (null == activityStack || activityStack.size() == 0) {
            return false;
        }
        return true;
    }

    /**
     * 判断APP是否只有一个Activity
     * @return
     */
    public boolean isOneActivity() {
        if (null != activityStack && activityStack.size() == 1) {
            return true;
        }
        return false;
    }

    /**
     * 获取当前Activity
     * @return Activity
     */
    public Activity currentActivity() {
        return activityStack.lastElement();
    }

    /**
     * 结束当前Activity
     */
    public void finishActivity() {
        finishActivity(activityStack.lastElement());
    }

    /**
     * 结束指定的Activity
     * @param activity Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }

    /**
     * 结束指定类的Activity
     * @param cls 类
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束所有的Activity
     */
    private void finishAllActivity() {
        if (activityStack != null && activityStack.size() > 0) {
            int size = activityStack.size();
            for (int i = 0; i < size; i++) {
                Activity activity = activityStack.get(i);
                if (null != activity && !activity.isFinishing()) {
                    activity.finish();
                }
            }
            activityStack.clear();
        }
    }

    /**
     * 结束mainActivity之外的所有的Activity
     */
    @Deprecated
    public void finishAllButMainActivity() {
        if (activityStack != null && activityStack.size() > 0) {
            int size = activityStack.size();
            Stack<Activity> activitys = new Stack<>();
            for (int i = 0; i < size; i++) {
                if (i != 0) {
                    Activity activity = activityStack.get(i);
                    activitys.add(activity);
                    if (null != activity && !activity.isFinishing()) {
                        activity.finish();
                    }
                }
            }
            activityStack.remove(activitys);
        }
    }

    /**
     * 保留指定的activity
     * @param cls
     */
    public void finishAllButMainActivity(Class<?> cls) {
        if (activityStack != null && activityStack.size() > 0) {
            Stack<Activity> activities = new Stack<>();
            for (Activity activity : activityStack) {
                if (!activity.getClass().equals(cls)) {
                    activities.add(activity);
                    if (null != activity && !activity.isFinishing()) {
                        activity.finish();
                    }
                }
            }
            activityStack.remove(activities);
        }
    }

    /**
     * 是否完全退出应用程序
     * @param isExitApp 是true，否false
     */
    public void appExit(boolean isExitApp) {
        try {
            // 停止网络请求
            NetworkRequest.cancel(null);
            // 关闭所有Activity
            finishAllActivity();
            if (isExitApp) {
                // 关闭日志输出到SD卡
                HLogToFileQueue.getInstance().stopHLogToFile();
                // 杀死该应用进程
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}