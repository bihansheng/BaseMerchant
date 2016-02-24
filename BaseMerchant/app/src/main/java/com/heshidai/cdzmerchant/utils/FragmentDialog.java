/******************************************************************************
 * Copyright (C) 2014 ShenZhen HeShiDai Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为合时代控股有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.heshidai.cdzmerchant.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.heshidai.cdzmerchant.R;
import com.heshidai.cdzmerchant.business.dialog.ValidateDialog;

/**
 * 碎片工具对话框
 *
 * @author xiao di fa
 */
public class FragmentDialog {

    public static ValidateDialog dialog = null;

    /**
     * 自定义对话框
     */
    public static void showValidate(Activity activity, boolean isCancel) {
        if (dialog == null || !dialog.isShowing()) {
            dialog = new ValidateDialog(activity, R.style.Dialog);
            dialog.setCanceledOnTouchOutside(!isCancel);
            dialog.setCancelable(isCancel);
            Window window = dialog.getWindow();
            window.setGravity(Gravity.CENTER);
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = 80 * DisplayUtil.getWidth(activity) / 100;
            dialog.show();
        }
    }

    public static Dialog dialogFragment = null;

    /**
     * 单个按钮对话框，默认是确定
     * @param activity
     * @param title
     * @param message
     * @param positiveListener
     */
    public static void mAlert(Activity activity, String title, String message, View.OnClickListener positiveListener) {
        if(activity != null && !activity.isFinishing()) {
            show(activity, initView(activity, title, message, Gravity.CENTER, "", "", null, positiveListener), true);
        }
    }

    /**
     * 单个按钮对话框
     * @param activity
     * @param title
     * @param message
     * @param positive
     * @param positiveListener
     */
    public static void mAlert(Activity activity, String title, String message, String positive, View.OnClickListener positiveListener) {
        if(activity != null && !activity.isFinishing()) {
            show(activity, initView(activity, title, message, Gravity.CENTER, positive, "", positiveListener, null), true);
        }
    }

    /**
     * 单个按钮对话框，禁止取消
     * @param activity
     * @param title
     * @param message
     * @param positive
     * @param positiveListener
     */
    public static void mAlertNoCancel(Activity activity, String title, String message, String positive, View.OnClickListener positiveListener) {
        if(activity != null && !activity.isFinishing()) {
            show(activity, initView(activity, title, message, Gravity.CENTER, positive, "", positiveListener, null), false);
        }
    }

    /**
     * 两个按钮对话框，默认是取消和确定
     * @param activity
     * @param title
     * @param message
     * @param positiveListener
     * @param negativeListener
     */
    public static void mConfirm(Activity activity, String title, String message, View.OnClickListener positiveListener, View.OnClickListener negativeListener) {
        if(activity != null && !activity.isFinishing()) {
            show(activity, initView(activity, title, message, Gravity.CENTER, "", "", positiveListener, negativeListener), true);
        }
    }

    /**
     * 两个按钮对话框
     * @param activity
     * @param title
     * @param message
     * @param positive
     * @param negative
     * @param positiveListener
     * @param negativeListener
     */
    public static void mConfirm(Activity activity, String title, String message, String positive, String negative, View.OnClickListener positiveListener, View.OnClickListener negativeListener) {
        if(activity != null && !activity.isFinishing()) {
            show(activity, initView(activity, title, message, Gravity.CENTER, positive, negative, positiveListener, negativeListener), true);
        }
    }

    /**
     * 两个按钮对话框
     * @param activity
     * @param title
     * @param message
     * @param positive
     * @param negative
     * @param positiveListener
     * @param negativeListener
     */
    public static void mConfirmLeft(Activity activity, String title, String message, String positive, String negative, View.OnClickListener positiveListener, View.OnClickListener negativeListener) {
        if(activity != null && !activity.isFinishing()) {
            show(activity, initView(activity, title, message, Gravity.LEFT|Gravity.CENTER, positive, negative, positiveListener, negativeListener), true);
        }
    }

    /**
     * 显示带关闭按钮的对话框
     * @param activity
     * @param title
     * @param message
     * @param closeListener
     */
    public static void mAlertClose(Activity activity, String title, String message, View.OnClickListener closeListener) {
        if(activity != null && !activity.isFinishing()) {
            show(activity, initCloseView(activity, title, message, Gravity.LEFT | Gravity.CENTER, "", "", null, null, closeListener), true);
        }
    }

    /**
     * 显示带关闭按钮的对话框
     * @param activity
     * @param title
     * @param message
     * @param positive
     * @param positiveListener
     * @param closeListener
     */
    public static void mConfirmClose(Activity activity, String title, String message, String positive, View.OnClickListener positiveListener, View.OnClickListener closeListener) {
        if(activity != null && !activity.isFinishing()) {
            show(activity, initCloseView(activity, title, message, Gravity.LEFT | Gravity.CENTER, positive, "", positiveListener, null, closeListener), true);
        }
    }

    /**
     * 显示对话框
     * @param activity
     * @param view
     * @param isCancel true表示可以取消 否则不能取消
     */
    private static void show(Activity activity, View view, boolean isCancel) {
        if(dialogFragment == null || !dialogFragment.isShowing()) {
            dialogFragment = new Dialog(activity, R.style.Dialog);
            dialogFragment.setContentView(view);
            dialogFragment.setCanceledOnTouchOutside(isCancel);
            dialogFragment.setCancelable(isCancel);
            Window window = dialogFragment.getWindow();
            window.setGravity(Gravity.CENTER);
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = 80 * DisplayUtil.getWidth(activity) / 100;
            dialogFragment.show();
        }
    }

    /**
     * 关闭对话框
     */
    public static void close() {
        if (dialogFragment != null && dialogFragment.isShowing()) {
            dialogFragment.dismiss();
            dialogFragment = null;
        }
    }

    /**
     * 初始化对话框界面
     * @param activity
     * @param title
     * @param message
     * @param gravity
     * @param positive
     * @param negative
     * @param positiveListener
     * @param negativeListener
     * @return
     */
    private static View initView(Activity activity, String title, String message, int gravity, String positive, String negative, View.OnClickListener positiveListener, View.OnClickListener negativeListener) {
        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_tip2, null);
        TextView textTitle = (TextView) view.findViewById(R.id.text_title);//标题
        if(!TextUtils.isEmpty(title)) {
            textTitle.setText(title);
            textTitle.setVisibility(View.VISIBLE);
        } else {
            textTitle.setVisibility(View.GONE);
        }
        TextView textMessage = (TextView) view.findViewById(R.id.text_message);//提示内容
        textMessage.setGravity(gravity);
        textMessage.setMovementMethod(new ScrollingMovementMethod());
        if(!TextUtils.isEmpty(message)) {
            textMessage.setText(message);
            textMessage.setVisibility(View.VISIBLE);
        } else {
            textMessage.setVisibility(View.GONE);
        }
        Button btnPositive = (Button) view.findViewById(R.id.btn_left);//左边按钮
        btnPositive.setText((TextUtils.isEmpty(positive)) ? activity.getString(R.string.dialog_left_btn) : positive);
        if(positiveListener != null) {
            btnPositive.setVisibility(View.VISIBLE);
            btnPositive.setOnClickListener(positiveListener);
        } else {
            btnPositive.setVisibility(View.GONE);
        }
        View dividerView = view.findViewById(R.id.view_divider);//分割线
        if(positiveListener != null && negativeListener != null) {
            dividerView.setVisibility(View.VISIBLE);
        } else {
            dividerView.setVisibility(View.GONE);
        }
        Button btnNegative = (Button) view.findViewById(R.id.btn_right);//右边按钮
        btnNegative.setText((TextUtils.isEmpty(negative)) ? activity.getString(R.string.dialog_right_btn) : negative);
        if(negativeListener != null) {
            btnNegative.setVisibility(View.VISIBLE);
            btnNegative.setOnClickListener(negativeListener);
        } else {
            btnNegative.setVisibility(View.GONE);
        }
        return view;
    }

    /**
     * 初始化弹出层
     * @param activity
     * @param title
     * @param message
     * @param gravity
     * @param positive
     * @param negative
     * @param positiveListener
     * @param negativeListener
     * @param closeListener
     * @return
     */
    private static View initCloseView(Activity activity, String title, String message, int gravity, String positive, String negative, View.OnClickListener positiveListener, View.OnClickListener negativeListener, View.OnClickListener closeListener) {
        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_close, null);
        TextView textTitle = (TextView) view.findViewById(R.id.text_title);//标题
        if(!TextUtils.isEmpty(title)) {
            textTitle.setText(title);
            textTitle.setVisibility(View.VISIBLE);
        } else {
            textTitle.setVisibility(View.GONE);
        }
        Button btnClose = (Button) view.findViewById(R.id.btn_close);//关闭按钮
        if(closeListener != null) {
            btnClose.setVisibility(View.VISIBLE);
            btnClose.setOnClickListener(closeListener);
        } else {
            btnClose.setVisibility(View.GONE);
        }
        TextView textMessage = (TextView) view.findViewById(R.id.text_message);//提示内容
        textMessage.setGravity(gravity);
        textMessage.setMovementMethod(new ScrollingMovementMethod());
        if(!TextUtils.isEmpty(message)) {
            textMessage.setText(message);
            textMessage.setVisibility(View.VISIBLE);
        } else {
            textMessage.setVisibility(View.GONE);
        }
        View dividerView2 = view.findViewById(R.id.view_divider2);//分割线
        LinearLayout llBtn = (LinearLayout) view.findViewById(R.id.ll_btns);
        if(positiveListener != null || negativeListener != null) {
            dividerView2.setVisibility(View.VISIBLE);
            llBtn.setVisibility(View.VISIBLE);
        } else {
            dividerView2.setVisibility(View.GONE);
            llBtn.setVisibility(View.GONE);
        }
        Button btnPositive = (Button) view.findViewById(R.id.btn_left);//左边按钮
        btnPositive.setText((TextUtils.isEmpty(positive)) ? activity.getString(R.string.dialog_left_btn) : positive);
        if(positiveListener != null) {
            btnPositive.setVisibility(View.VISIBLE);
            btnPositive.setOnClickListener(positiveListener);
        } else {
            btnPositive.setVisibility(View.GONE);
        }
        View dividerView = view.findViewById(R.id.view_divider);//分割线
        if(positiveListener != null && negativeListener != null) {
            dividerView.setVisibility(View.VISIBLE);
        } else {
            dividerView.setVisibility(View.GONE);
        }
        Button btnNegative = (Button) view.findViewById(R.id.btn_right);//右边按钮
        btnNegative.setText((TextUtils.isEmpty(negative)) ? activity.getString(R.string.dialog_right_btn) : negative);
        if(negativeListener != null) {
            btnNegative.setVisibility(View.VISIBLE);
            btnNegative.setOnClickListener(negativeListener);
        } else {
            btnNegative.setVisibility(View.GONE);
        }
        return view;
    }

    /**日期选择对话框
     * @param activity
     * @param listener
     * @param keyListener
     * @param title
     * @param items
     */
    @SuppressLint("InlinedApi")
    public static void datePickerDialog(Activity activity, DatePickerDialog.OnDateSetListener listener, DialogInterface.OnKeyListener keyListener, String title, int... items) {
        if (null != activity && !activity.isFinishing()) {
            int[] content = items;
            if(null != content && content.length >= 3) {
                DatePickerDialog dialog = new DatePickerDialog(activity, DatePickerDialog.THEME_DEVICE_DEFAULT_LIGHT, listener, content[0], content[1], content[2]);
                if(null != title && title.length() > 0) {
                    dialog.setTitle(title);
                }
                dialog.setOnKeyListener(keyListener);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
            }
        }
    }

    /**时间选择对话框
     * @param activity
     * @param listener
     * @param keyListener
     * @param title
     * @param items
     */
    @SuppressLint("InlinedApi")
    public static void timePickerDialog(Activity activity, TimePickerDialog.OnTimeSetListener listener, DialogInterface.OnKeyListener keyListener, String title, int... items) {
        if (null != activity && !activity.isFinishing()) {
            int[] content = items;
            if(null != content && content.length >= 2) {
                TimePickerDialog dialog = new TimePickerDialog(activity, DatePickerDialog.THEME_DEVICE_DEFAULT_LIGHT, listener, content[0], content[1], true);
                if(null != title && title.length() > 0) {
                    dialog.setTitle(title);
                }
                dialog.setOnKeyListener(keyListener);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
            }
        }
    }

}