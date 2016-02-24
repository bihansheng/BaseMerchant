/******************************************************************************
 * Copyright (C) 2014 ShenZhen HeShiDai Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为合时代控股有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.heshidai.cdzmerchant.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.heshidai.cdzmerchant.R;
import com.heshidai.cdzmerchant.business.callback.DialogCallBack;
import com.heshidai.cdzmerchant.business.dialog.DialogDownProgress;

/**
 * 提示信息操作工具类
 * @author xiao di fa
 */
public class PromptManager {
    /**
     * 联网进度条
     */
    private static ProgressDialog progressDialog;

    private static DialogDownProgress downloadProgress;//下载对话框

    /**
     * 提示文字
     *
     * @param context Context
     * @param msg     文本内容
     */
    public static void showToast(Context context, String msg) {
        if (!TextUtils.isEmpty(msg)) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        }
    }

    public static void showDialog() {

    }

    /**
     * 提示文字
     *
     * @param context  Context
     * @param msgResId 文本内容ID
     */
    public static void showToast(Context context, int msgResId) {
        Toast.makeText(context, msgResId, Toast.LENGTH_SHORT).show();
    }

    /**
     * 提示时间更长的文字
     *
     * @param context
     * @param msgResId
     */
    public static void showLongToast(Context context, int msgResId) {
        Toast.makeText(context, msgResId, Toast.LENGTH_LONG).show();
    }


    /**
     * 显示水平下载对话框
     *
     * @param context         要弹出对话框的界面
     * @param fileSize        文件大小
     * @param isCancel        true可以取消 false不能取消
     * @param cancelListener  取消按钮监听
     * @param dismissListener 对话框关闭监听
     */
    public static void showProgressHorizontalDialog(Context context, int fileSize, boolean isCancel, DialogCallBack cancelListener, DialogInterface.OnDismissListener dismissListener) {
        if (downloadProgress == null || !downloadProgress.isShowing()) {
            downloadProgress = new DialogDownProgress(context);
            downloadProgress.setFileSize(fileSize);
            downloadProgress.setCancelable(isCancel);
            downloadProgress.setCanceledOnTouchOutside(false);
            if (isCancel) {
                downloadProgress.setData(isCancel, context.getString(R.string.common_cancel_download));
                downloadProgress.setCancel(cancelListener);
            } else {
                downloadProgress.setData(isCancel, context.getString(R.string.common_downloading));
            }
            downloadProgress.setOnDismissListener(dismissListener);
            downloadProgress.show();
        }
    }

    /**
     * 下载对话框是否显示
     *
     * @return
     */
    public static boolean isShowProgressDialog() {
        if (downloadProgress != null && downloadProgress.isShowing()) {
            return true;
        }
        return false;
    }

    /**
     * 设置下载进度
     *
     * @param progress
     */
    public static void setProgressDialogProgress(int progress) {
        if (downloadProgress != null && downloadProgress.isShowing()) {
            downloadProgress.setProgress(progress);
        }
    }

    /**
     * 隐藏下载对话框
     */
    public static void cancelProgressDialog() {
        if (downloadProgress != null && downloadProgress.isShowing()) {
            downloadProgress.dismiss();
            downloadProgress = null;
        }
    }

    /**
     * 显示联网进度条对话框,不能取消
     *
     * @param context
     * @param onBack
     * @param onCancel
     */
    public static void showProgressDialogCommit(Context context, boolean onBack, boolean onCancel) {
        if (progressDialog == null || !progressDialog.isShowing()) {
            progressDialog = new ProgressDialog(context,R.style.dialog_wait);
            progressDialog.setCancelable(onBack);
            progressDialog.setCanceledOnTouchOutside(onCancel);
            progressDialog.show();
            progressDialog.setContentView(R.layout.dialog_progress);
        }
    }

    /**
     * 显示联网进度条对话框,默认设置文字图标，默认可以点击消失
     * @param context Context
     */
    public static void showProgressDialog(Context context) {
        showProgressDialog(context,context.getResources().getString(R.string.common_loading));
    }

    /**
     * 显示联网进度条对话框,单独设置显示文字，默认可以点击消失
     *
     * @param context Context
     * @param msg     文字内容
     */
    public static void showProgressDialog(Context context, String msg) {
        showProgressDialogCommit(context, msg, true, false);
    }

    /**
     * 显示联网进度条对话框
     * @param context 上下文
     * @param msg 显示提示
     * @param onBack  按返回键是否能退出
     * @param onCancel 点击弹框外部是否可以退出
     */
    public static void showProgressDialogCommit(Context context, String msg, boolean onBack, boolean onCancel) {
        if (progressDialog == null || !progressDialog.isShowing()) {
            progressDialog = new ProgressDialog(context,R.style.dialog_wait);
            View content = View.inflate(context, R.layout.dialog_progress, null);
            TextView tv = (TextView) content.findViewById(R.id.tv_text);
            tv.setText(msg);
            ImageView iv = (ImageView) content.findViewById(R.id.imageView1);
            MyAnimations.setRotateAnimation(iv);
            progressDialog.setCanceledOnTouchOutside(onCancel);
            progressDialog.setCancelable(onBack);
            progressDialog.show();
            progressDialog.setContentView(content);
        }
    }

    /**
     * 显示旋转图片
     *
     * @param context Context
     */
    public static void showImageProgressDialog(Context context) {
        if (progressDialog == null || !progressDialog.isShowing()) {
            progressDialog = new ProgressDialog(context);
            View content = View.inflate(context, R.layout.dialog_image_progress, null);
            content.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
            progressDialog.show();
            progressDialog.setContentView(content);
        }
    }

    /**
     * 隐藏进度条对话框
     */
    public static void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

}
