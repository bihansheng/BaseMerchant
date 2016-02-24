/******************************************************************************
 * Copyright (C) 2014 ShenZhen HeShiDai Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为合时代控股有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.heshidai.cdzmerchant.business.manager;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;

import com.android.volley.toolbox.HttpsTrustManager;
import com.heshidai.cdzmerchant.R;
import com.heshidai.cdzmerchant.business.callback.DialogCallBack;
import com.heshidai.cdzmerchant.business.callback.UpdateCallBack;
import com.heshidai.cdzmerchant.common.Constant;
import com.heshidai.cdzmerchant.entity.Update;
import com.heshidai.cdzmerchant.net.INetRequest;
import com.heshidai.cdzmerchant.utils.FileUtil;
import com.heshidai.cdzmerchant.utils.FragmentDialog;
import com.heshidai.cdzmerchant.utils.GsonManager;
import com.heshidai.cdzmerchant.utils.PackageUtil;
import com.heshidai.cdzmerchant.utils.PromptManager;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

/**
 * 更新版本通用类
 * @author xiao di fa
 *
 */
public class UpdateManager {
	private static final String ACTION_TAG_INIT = "1";

	private Activity activity;
	private static UpdateManager updateManager;

	private UpdateManager() {}

	public static synchronized UpdateManager getInstance() {
		if (updateManager == null) {
			updateManager = new UpdateManager();
		}
		return updateManager;
	}

	/**
	 * 初始化数据
	 * @param activity
	 */
	public void init(Activity activity) {
		this.activity = activity;
	}

	/**
	 * 检查是否有更新
	 * @param showTip 是否提示
	 * @param callBack
	 */
	public void checkUpdate(final boolean showTip, final UpdateCallBack callBack) {
		CommonRequestManager.getApkVersion(new INetRequest() {
			@Override
			public void setAsyncJsonCallBack(int action, JSONObject jsonObj) {
				try {
					int code = jsonObj.getInt("code");
					String msg = jsonObj.getString("msg");
					if (code == 00) {//成功
						JSONObject data = jsonObj.getJSONObject("data");
						Update update = GsonManager.getInstance().fromJson((data == null) ? "" : data.toString(), Update.class);
						if (update != null && update.getVersionCode() > PackageUtil.getCode(activity)) {
							callBack.setUpdateCallBack(true, update);
						} else {
							callBack.setUpdateCallBack(false, null);
						}
					} else if (code == 01) {//失败
						if (showTip) {
							PromptManager.showToast(activity, msg);
						}
						callBack.setUpdateCallBack(false, null);
					} else {
						callBack.setUpdateCallBack(false, null);
					}
				} catch (Exception e) {
					e.printStackTrace();
					callBack.setUpdateCallBack(false, null);
				}
			}

			@Override
			public void setTimeoutError(int action) {
				if (showTip) {
					PromptManager.showToast(activity, activity.getString(R.string.common_timeout_msg));
				}
				callBack.setUpdateCallBack(false, null);
			}

			@Override
			public void setNetWorkErrorCode(int action, int statusCode) {
				if (showTip) {
					PromptManager.showToast(activity, activity.getString(R.string.network_offline));
				}
				callBack.setUpdateCallBack(false, null);
			}
		});
	}

	/**
	 * 更新版本提示
	 * @param showTip
	 * @param hasNewVersion
	 * @param update
	 */
	public void updateApkVersion(boolean showTip, boolean hasNewVersion, final Update update) {
		if(activity != null && !activity.isFinishing()) {
			if (hasNewVersion && update != null) {//如果有最新版本就提示
				if(update.getIsneed() == 1) {//如果是强制更新
					FragmentDialog.mAlertNoCancel(activity, String.format("%s  %s", activity.getString(R.string.more_version_new), update.getVersionName()), activity.getString(R.string.more_version_need), activity.getString(R.string.more_version_confirm), new View.OnClickListener() {
						@Override
						public void onClick(View view) {
							FragmentDialog.close();
							updateApkVersion(update);
						}
					});
				} else {//如果不是强制更新
					FragmentDialog.mConfirmLeft(activity, String.format("%s  %s", activity.getString(R.string.more_version_new), update.getVersionName()), update.getContent(), activity.getString(R.string.more_version_cancel), activity.getString(R.string.more_version_confirm),
							new View.OnClickListener() {
								@Override
								public void onClick(View v) {
									FragmentDialog.close();
								}
							}, new View.OnClickListener() {
								@Override
								public void onClick(View v) {
									FragmentDialog.close();
									updateApkVersion(update);
								}
							});
				}
			} else if(showTip) {//没有最新版本
				FragmentDialog.mAlert(activity, activity.getString(R.string.more_version_tip), activity.getString(R.string.more_version_no_new),
						new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								FragmentDialog.close();
							}
						});
			}
		}
	}

	/**
	 * 更新APK
	 * @param update
	 */
	private void updateApkVersion(Update update) {
		if(activity != null && !activity.isFinishing() && !TextUtils.isEmpty(update.getDownloadLink())) {
			File apkFile = new File(String.format("%s%s", Constant.INSTALL_DIRECTORY, update.getDownloadLink().substring(update.getDownloadLink().lastIndexOf("/"))));
			if(apkFile.exists() && apkFile.length() == update.getFileSize()) {
				PromptManager.showToast(activity, R.string.more_version_exist);
				installApkVersion(apkFile);
				return;
			}
			new DownloadFileTask(activity, update).execute();
		}
	}

	/**
	 * 下载APK
	 * @author xiao di fa
	 *
	 */
	private class DownloadFileTask extends AsyncTask<String, Integer, Integer> {
		private Activity activity;
		private Update update;
		private File apkFile;

		DownloadFileTask(Activity activity, Update update) {
			this.activity = activity;
			this.update = update;
			this.apkFile = new File(String.format("%s%s", Constant.INSTALL_DIRECTORY, update.getDownloadLink().substring(update.getDownloadLink().lastIndexOf("/"))));
		}

		// 创建下载对话框
		@Override
		protected void onPreExecute() {
			PromptManager.showProgressHorizontalDialog(activity, (int) update.getFileSize(), (update.getIsneed() == 1) ? false : true, new DialogCallBack() {
				@Override
				public void setCancelCallBack() {
					onCancelled();
				}
			}, new DialogInterface.OnDismissListener() {
				@Override
				public void onDismiss(DialogInterface dialog) {
					onCancelled();
				}
			});
			super.onPreExecute();
		}

		// 开始下载
		@Override
		protected Integer doInBackground(String... params) {
			HttpsURLConnection connection = null;
			BufferedInputStream inputStream = null;
			FileOutputStream fos = null;
			int progressLen = -1;//标识是否下载成功
			try {
				URL url = new URL(update.getDownloadLink());
				if(url != null) {
					connection = (HttpsURLConnection) url.openConnection();
					if(connection != null) {
						TrustManager[] tm = { new HttpsTrustManager() };
						SSLContext sslContext = SSLContext.getInstance("SSL");
						sslContext.init(null, tm, new SecureRandom());
						SSLSocketFactory ssf = sslContext.getSocketFactory();
						connection.setSSLSocketFactory(ssf);
						if (connection.getResponseCode() == HttpsURLConnection.HTTP_OK) {
							inputStream = new BufferedInputStream(connection.getInputStream());
							long length = connection.getContentLength();
							fos = new FileOutputStream(apkFile, false);
							byte buf[] = new byte[1024];
							long count = 0;
							int len = -1;
							while ((len = inputStream.read(buf)) != -1) {
								if (PromptManager.isShowProgressDialog()) {
									fos.write(buf, 0, len);
									count += len;
									progressLen = (int) (count * 100 / length);//计算下载到多少
									publishProgress(progressLen);
									fos.flush();
								} else {
									publishProgress(0);
								}
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				// 关闭流
				close(connection, inputStream, fos);
			}
			return progressLen;
		}


		// 更新下载对话框
		@Override
		protected void onProgressUpdate(Integer... values) {
			PromptManager.setProgressDialogProgress(values[0]);
			super.onProgressUpdate(values);
		}

		// 下载完成
		@Override
		protected void onPostExecute(Integer result) {
			PromptManager.cancelProgressDialog();
			if (result == 100) {
				installApkVersion(apkFile);
			} else {
				//删除文件
				if(apkFile != null && apkFile.exists()) {
					apkFile.delete();
				}
			}
			super.onPostExecute(result);
		}

		// 用户取消了下载，进度条复位
		@Override
		protected void onCancelled() {
			PromptManager.cancelProgressDialog();
			super.onCancelled();
		}
	}

	/**
	 * 关闭流
	 * @param connection
	 * @param inputStream
	 * @param outputStream
	 */
	private void close(HttpURLConnection connection, InputStream inputStream, OutputStream outputStream) {
		try {
			if (null != outputStream) {
				outputStream.close();
			}
			if (null != inputStream) {
				inputStream.close();
			}
			if(null != connection) {
				connection.disconnect();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 安装APK
	 * @param apkFile
	 */
	private void installApkVersion(File apkFile) {
		if(apkFile != null && apkFile.exists()) {
			Intent apkIntent = new Intent(Intent.ACTION_VIEW);
			apkIntent.setDataAndType(Uri.parse("file://" + apkFile.toString()), "application/vnd.android.package-archive");
			activity.startActivity(apkIntent);
			FileUtil.deleteAllFile(String.format("/data/data/%s", activity.getPackageName()));
			if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
				File externalCacheDir = activity.getExternalCacheDir();
				if(externalCacheDir != null && externalCacheDir.exists()) {
					FileUtil.deleteAllFile(activity.getExternalCacheDir().getPath());//清除手机Sdcard里面的文件
				}
			}
		}
	}

}
