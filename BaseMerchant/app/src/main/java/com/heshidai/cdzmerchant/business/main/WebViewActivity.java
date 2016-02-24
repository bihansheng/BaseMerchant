/******************************************************************************
 * Copyright (C) 2016 ShenZhen HeShiDai Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为合时代控股有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.heshidai.cdzmerchant.business.main;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebBackForwardList;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.heshidai.cdzmerchant.R;
import com.heshidai.cdzmerchant.app.HSDAppManager;
import com.heshidai.cdzmerchant.base.BaseActivity;

/**
 * @author lixiangxiang
 * @desc 加载网页页面，传入需要调用的URL地址
 * @date 2016/1/14 16:24
 */
@SuppressLint({"JavascriptInterface", "SetJavaScriptEnabled"})
public class WebViewActivity extends BaseActivity {

    private WebView webView;
    private ProgressBar progressBar;
    private Toolbar mToolbar;
    private TextView mTvTitle;

    private String url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        initLayout();
        localURL(webView, url);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        //数据
        url = getIntent().getStringExtra("url");
        localURL(webView, url);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void initLayout() {
        url = getIntent().getStringExtra("url");
        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        mTvTitle = (TextView)findViewById(R.id.tv_title);
        webView = (WebView) findViewById(R.id.webview);
        //进度条
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        mToolbar.setNavigationIcon(R.drawable.btn_selector_back);// 左边  抽屉栏图标,这里将这个改为返回按钮
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exit();
            }
        });
//        webView.addJavascriptInterface(WebViewActivity.this, WebViewActivity.class.getSimpleName());
        //填补js远程代码执行漏洞
        if (Build.VERSION.SDK_INT >= 11) {
            webView.removeJavascriptInterface("searchBoxJavaBridge_");
            webView.removeJavascriptInterface("accessibility");
            webView.removeJavascriptInterface("accessibilityTraversal");
        }
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setAllowFileAccess(true);//允许访问文件
        webSettings.setSupportZoom(true);//设置支持缩放
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);//设置缓存模式（不使用缓存，只从网络获取数据.）
        webSettings.setBuiltInZoomControls(true);//启动内置缩放
        webSettings.setLoadsImagesAutomatically(true);//自动加载图片
        webSettings.setDefaultTextEncodingName("UTF-8");
        webSettings.setLoadWithOverviewMode(true);//自适应屏幕
        webView.setWebChromeClient(new WebChromeClient() {//进度条的加载
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if (mToolbar != null) {
                    mTvTitle.setText((TextUtils.isEmpty(title)) ? "" : title);
                }
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    progressBar.setVisibility(View.GONE);
                } else {
                    if (progressBar.getVisibility() == View.GONE) {
                        progressBar.setVisibility(View.VISIBLE);
                    }
                    progressBar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }
        });
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {//此处能拦截超链接的url,即拦截href请求的内容.
                if (url.contains("first_access_share")) {//如果包含“first_access_share”拦截跳转
                    return true;
                }
                if (url.endsWith(".apk")) {
                    Intent apkIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    WebViewActivity.this.startActivity(apkIntent);
                    return true;
                }
                view.loadUrl(url);
                return true;
            }
        });
    }

    /**
     * 加载网址
     *
     * @param webView
     * @param url
     */
    private void localURL(WebView webView, String url) {
        webView.loadUrl(url);
    }

    /**
     * 退出或者进入启动页面
     */
    private void exit() {
        HSDAppManager hsdAppManager = HSDAppManager.getInstance();
        Activity activity = hsdAppManager.currentActivity();
        if(hsdAppManager.isOneActivity() && null != activity && !activity.isFinishing() && activity instanceof WebViewActivity) {
            Intent welcomeIntent = new Intent(WebViewActivity.this, WelcomeActivity.class);
            startActivity(welcomeIntent);
        }
        WebViewActivity.this.finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if(webView.canGoBack()) {
                WebBackForwardList mWebBackForwardList = webView.copyBackForwardList();
                if (mWebBackForwardList.getCurrentIndex() > 0) {
                    String historyUrl = mWebBackForwardList.getItemAtIndex(mWebBackForwardList.getCurrentIndex() - 1).getUrl();
                    if (!historyUrl.equals(url)) {
                        webView.goBack();
                        return true;
                    }
                }
            } else {
                exit();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
