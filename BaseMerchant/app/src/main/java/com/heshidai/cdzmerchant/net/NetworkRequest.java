/**
 * **************************************************************************
 * Copyright (C) 2014 ShenZhen HeShiDai Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为合时代控股有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 * **************************************************************************
 */
package com.heshidai.cdzmerchant.net;

import android.content.Intent;
import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.heshidai.cdzmerchant.app.HSDAppManager;
import com.heshidai.cdzmerchant.app.HSDApplication;
import com.heshidai.cdzmerchant.business.main.LoginActivity;
import com.heshidai.cdzmerchant.business.manager.CommonRequestManager;
import com.heshidai.cdzmerchant.business.manager.UserManager;
import com.heshidai.cdzmerchant.common.Constant;
import com.heshidai.cdzmerchant.common.SPManager;
import com.heshidai.cdzmerchant.entity.BaseEntity;
import com.heshidai.cdzmerchant.entity.LoginUser;
import com.heshidai.cdzmerchant.entity.RequestParams;
import com.heshidai.cdzmerchant.entity.User;
import com.heshidai.cdzmerchant.utils.DesUtil;
import com.heshidai.cdzmerchant.utils.MD5Util;
import com.heshidai.cdzmerchant.utils.PreferencesUtils;
import com.heshidai.cdzmerchant.utils.PromptManager;
import com.heshidai.cdzmerchant.utils.UserUtil;
import com.heshidai.cdzmerchant.utils.log.HLog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Volley网络请求
 */
public final class NetworkRequest {
    /**
     * GET请求
     *
     * @param url       地址
     * @param actionTag 区分一个页面多个请求
     */
    public static void get(final String url, int actionTag, INetRequest netCallBack) {
        final RequestParams requestParams = new RequestParams(Request.Method.GET, url, actionTag, actionTag, netCallBack, null);  //将请求参数存入自定义请求队列中，用于token失效后重新请求
        String token = HSDApplication.getToken();
        String newUrl = (TextUtils.isEmpty(token)) ? url : String.format("%s%s%s", url, url.contains("?") ? ("&token=") : ("?token="), token);//添加token
        HLog.i(Constant.TAG, "GET请求地址：" + newUrl);
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, newUrl, null, new Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject obj, Object netCallBack, String action) {
                HLog.i(Constant.TAG, "GET请求结果：" + ((obj == null) ? "" : obj.toString()));
                if(obj!=null){
                    dealToken(requestParams, obj);//数据预处理
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error, Object netCallBack, String action) {
                if (netCallBack != null) {
                    handError(error, netCallBack, Integer.valueOf(action));
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Accept", "application/json");
                headers.put("Content-Type", "application/json; charset=UTF-8");
                return headers;
            }
        };
        HVolley.getInstance().addToRequestQueue(request, String.valueOf(actionTag), String.valueOf(actionTag), netCallBack);
    }

    /**
     * POST请求
     *
     * @param url         地址
     * @param params      参数
     * @param actionTag   区分一个页面多个请求
     * @param netCallBack 回调
     */
    public static void post(final String url, final Map<String, String> params, final int actionTag,  INetRequest netCallBack) {
        final RequestParams requestParams = new RequestParams(Request.Method.POST, url, actionTag, actionTag, netCallBack, params); //将请求参数存入自定义请求队列中，用于token失效后重新请求
        String token = HSDApplication.getToken();
        if (params != null && !TextUtils.isEmpty(token)) {
            params.put("token", token);//添加token
        }

        HLog.i(Constant.TAG, "POST请求地址：" + url + "  参数：" + ((params == null) ? "" : params.toString()));
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Listener<String>() {
            @Override
            public void onResponse(String result, Object netCallBack, String action) {
                HLog.i(Constant.TAG, "POST请求结果：" + result);
                JSONObject jsonObject = null;
                if (!TextUtils.isEmpty(result)) {
                    try {
                        jsonObject = new JSONObject(result);
                        dealToken(requestParams, jsonObject);//数据预处理
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error, Object netCallBack, String action) {
                if (netCallBack != null) {
                    HLog.e(Constant.TAG, "POST请求结果：请求失败");
                    handError(error, netCallBack, Integer.valueOf(action));
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };
        HVolley.getInstance().addToRequestQueue(stringRequest, String.valueOf(actionTag), String.valueOf(actionTag), netCallBack);
    }

    /**
     * 第一次获取Keycode 的时间
     */
    public static long firsTime = 0;


    /**
     * 拦截返回的数据，处理keycode的方法
     */
    public static void dealToken(RequestParams requestParams, JSONObject jsonObj) {
        BaseEntity baseEntity = null;
        try {
            baseEntity = (BaseEntity) CommonRequestManager.resultToObject(jsonObj.toString(), BaseEntity.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (baseEntity == null) {
            HLog.e(Constant.TAG, "请求到的数据有误");
            (requestParams.getCallBack()).setNetWorkErrorCode(requestParams.getActionTag(), Constant.NO_CONNECTION_ERROR);//设置网络请求失败
            return;
        }
        switch (baseEntity.getCode()) {
            case 88://keyCode 失效，重新请求keycode
                HLog.e(Constant.TAG, "token 失效");
                //重新登录
                if ((System.currentTimeMillis() - firsTime) < 20000) {//如果两次token失效的时间间隔小于20秒，不执行getCode请求
                    loginOut(requestParams);
                } else {
                    firsTime = System.currentTimeMillis();
                    loginUser(requestParams);
                }
                break;
            default:
                (requestParams.getCallBack()).setAsyncJsonCallBack(requestParams.getActionTag(), jsonObj);//执行后续方法
                break;
        }
    }

    /**
     * 网络异常捕获处理
     *
     * @param error
     * @param netCallBack
     * @param action
     */
    public static void handError(VolleyError error, Object netCallBack, int action) {
        if (error != null) {
            NetworkResponse response = error.networkResponse;
            if (response != null) {// 服务端返回错误码
                ((INetRequest) netCallBack).setNetWorkErrorCode(action, response.statusCode);
                return;
            }
            if (error instanceof TimeoutError) {// 超时
                ((INetRequest) netCallBack).setTimeoutError(action);
            } else if (error instanceof AuthFailureError) {// 认证失败
            } else if (error instanceof NetworkError) {// 网络异常
                ((INetRequest) netCallBack).setNetWorkErrorCode(action, Constant.NO_CONNECTION_ERROR);
            } else if (error instanceof ParseError) {// 解析错误
            }
            error.printStackTrace();
        }
    }

    /**
     * /**
     * 取消请求
     *
     * @param tag 取消本次请求标签
     */
    public static void cancel(String tag) {
        HVolley.getInstance().cancelPendingRequests(tag);
    }


    /**
     * token失效后，重新登录
     */
    public static void loginUser(final RequestParams requestParams) {
        User user = (User) PreferencesUtils.readObject(HSDApplication.getApplication().getApplicationContext(), PreferencesUtils.PREFERENCE_NAME_DATA, SPManager.USER);
        if (user == null || TextUtils.isEmpty(user.getMerName()) || TextUtils.isEmpty(user.getPassword())) {
            loginOut(requestParams);
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("username", user.getMerAccount());
        map.put("password", user.getPassword());
        map.put("sign", MD5Util.toDigest(DesUtil.decrypt(user.getMerAccount()) + DesUtil.decrypt(user.getPassword()) + Constant.MD5_KEY));
        map.put("loginMode", "1");

        NetworkRequest.post(UserManager.SERVER_ADDRESS_LOGIN, map, UserManager.LOGIN_TAG, new INetRequest() {
            @Override
            public void setAsyncJsonCallBack(int action, JSONObject jsonObj) {
                HLog.e(Constant.TAG, "登录返回的数据"+jsonObj.toString());
                LoginUser loginUser = null;
                try {
                    loginUser = (LoginUser) CommonRequestManager.resultToObject(jsonObj.toString(), LoginUser.class);
                } catch (JSONException e) {

                    e.printStackTrace();
                }
                if (loginUser == null || loginUser.data == null || loginUser.code != 0) {//登录失败,退出登录状态
                    loginOut(requestParams);
                } else {
                    UserUtil.saveUserInfo(loginUser.data);//保存用户信息
                    requestAgain(requestParams);//重新请求
                }
            }

            @Override
            public void setTimeoutError(int action) {
                loginOut(requestParams);
            }

            @Override
            public void setNetWorkErrorCode(int action, int statusCode) {
                loginOut(requestParams);
            }
        });
    }


    /**
     * 强制退出用户，关闭mainActivy之上的所有页面，要求请重新登录
     */
    public static void loginOut(RequestParams requestParams) {
        HSDAppManager.getInstance().finishAllButMainActivity(LoginActivity.class);
        HSDApplication.getApplication().startActivity(new Intent(HSDApplication.getApplication(), LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        PreferencesUtils.saveObject(HSDApplication.getApplication(), PreferencesUtils.PREFERENCE_NAME_DATA, SPManager.USER, null);
        PromptManager.showToast(HSDApplication.getApplication(), "请重新登录");
    }

    /**
     * 重新执行被中断的请求
     */
    public static void requestAgain(RequestParams requestParams) {
        if (Request.Method.GET == requestParams.getMethod()) {//如果是get请求
            NetworkRequest.get(requestParams.getUrl(), requestParams.getActionTag(), requestParams.getCallBack());
        } else if (Request.Method.POST == requestParams.getMethod()) {//如果是Post请求
            NetworkRequest.post(requestParams.getUrl(), requestParams.getParams(), requestParams.getActionTag(), requestParams.getCallBack());
        }
    }


}