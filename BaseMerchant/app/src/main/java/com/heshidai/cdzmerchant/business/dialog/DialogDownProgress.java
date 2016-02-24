/******************************************************************************
 * Copyright (C) 2014 ShenZhen HeShiDai Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为合时代控股有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.heshidai.cdzmerchant.business.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.heshidai.cdzmerchant.R;
import com.heshidai.cdzmerchant.business.callback.DialogCallBack;

import java.text.NumberFormat;

/**
 * 自定义下载对话框
 * @author xiao di fa
 */
public class DialogDownProgress extends AlertDialog {

	private ProgressBar progressBar;
	private TextView textPercent, textNumber;
	private Button btnCancel;
	private Handler handler;

	private boolean isCancel;//能否取消
	private String message = "";//显示文字
	private static final String numberFormat = "%1.2fM/%2.2fM";
	private int fileSize, currentPosition;
	private boolean isStart;
	private NumberFormat percentFormat;
	private DialogCallBack cancelCallBack;

	public DialogDownProgress(Context context) {
		super(context);
		init();
	}

	/**
	 * 初始化
	 */
	private void init() {
		percentFormat = NumberFormat.getPercentInstance();
		percentFormat.setMaximumFractionDigits(0);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_download);
		progressBar = (ProgressBar) findViewById(R.id.progress);
		textPercent = (TextView) findViewById(R.id.text_percent);
		textNumber = (TextView) findViewById(R.id.text_number);
		btnCancel = (Button) findViewById(R.id.btn_cancel);
		btnCancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (cancelCallBack != null) {
					cancelCallBack.setCancelCallBack();
                }
			}
		});
		handler = new Handler(new Handler.Callback() {
			@Override
			public boolean handleMessage(Message msg) {
				int progress = progressBar.getProgress();
				double dMax = (double) fileSize / (double) (1024 * 1024);
				double dProgress = (double) progress / 100 * dMax;
				btnCancel.setEnabled(isCancel);
				btnCancel.setText(message);
				textNumber.setText(String.format(numberFormat, dProgress, dMax));// 格式化成m的格式
				if (percentFormat != null) {
					double percent = (double) progress / (double) progressBar.getMax();
					SpannableString tmp = new SpannableString(percentFormat.format(percent));
					tmp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0, tmp.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
					textPercent.setText(tmp);
				} else {
					textPercent.setText("");
				}
				return false;
			}
		});
		onProgressChanged();
		if (currentPosition > 0) {
			setProgress(currentPosition);
		}
	}

	/**
	 * 进度变更
	 */
	private void onProgressChanged() {
		handler.sendEmptyMessage(0);
	}

	/**
	 * 设置进度条的最大值
	 * @param fileSize
	 */
	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}


	/**
	 * 设置数据
	 * @param isCancel
	 * @param message
	 */
	public void setData(boolean isCancel, String message) {
		this.isCancel = isCancel;
		this.message = message;
	}

	/**
	 * 设置进度条进度
	 * @param value
	 */
	public void setProgress(int value) {
		if (isStart) {
			progressBar.setProgress(value);
			onProgressChanged();
		} else {
			currentPosition = value;
		}
	}

	/**
	 * 设置取消回调
	 * @param callBack
	 */
	public void setCancel(DialogCallBack callBack) {
		cancelCallBack = callBack;
	}

	@Override
	protected void onStart() {
		super.onStart();
		isStart = true;
	}

	@Override
	protected void onStop() {
		super.onStop();
		isStart = false;
	}

}
