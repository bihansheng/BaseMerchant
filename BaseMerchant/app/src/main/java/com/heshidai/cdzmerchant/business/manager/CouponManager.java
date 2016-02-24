/**
 * **************************************************************************
 * Copyright (C) 2014 ShenZhen HeShiDai Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为合时代控股有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 * **************************************************************************
 */
package com.heshidai.cdzmerchant.business.manager;

import android.content.Context;

import com.google.gson.JsonSyntaxException;
import com.heshidai.cdzmerchant.common.Constant;
import com.heshidai.cdzmerchant.entity.AOrderRecordEntity;
import com.heshidai.cdzmerchant.entity.OrderList;
import com.heshidai.cdzmerchant.event.BaseEvent;
import com.heshidai.cdzmerchant.net.NetworkRequest;
import com.heshidai.cdzmerchant.net.ResponseHandler;
import com.heshidai.cdzmerchant.utils.DesUtil;
import com.heshidai.cdzmerchant.utils.GsonManager;
import com.heshidai.cdzmerchant.utils.MD5Util;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

/**
 * 代金券相关信息处理类
 * 修改记录: 修改历史记录，包括修改日期、修改者及修改内容
 * 版权所有: 版权所有(C)2010-2014
 * 公　　司: 深圳合时代金融服务有限公司
 *
 * @author 万坤
 * @version 1.0.0
 * @date 2015-10-27
 * @history
 */


public class CouponManager {
    public static final int GET_CONSUME_DETAIL_LIST_TAG = 0x101;  //获取验券列表
    public static final int UPDATA_TAG = 0x102;//消费券号
    public static final int VALIDATE_TAG = 0x103; //验证券号
    public static final int CONSUME_TAG = 0x104;//消费券号

    /**
     * 获取验券记录列表
     *
     * @param context   上下文
     * @param merId     商家id
     * @param startTime 搜索开始时间
     * @param endTime   搜索结束时间
     * @param pageNum   第几页
     */
    public static void getConsumeDetailList(Context context, String merId, String startTime, String endTime, int pageNum) {
        Map<String, String> map = new HashMap<>();
        map.put("merId", DesUtil.encrypt(merId));
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        map.put("pageNum", String.valueOf(pageNum));
        map.put("pageSize", String.valueOf(Constant.PAGE_SIZE));
        map.put("sign", MD5Util.toDigest(merId + Constant.MD5_KEY));
        NetworkRequest.post(CommonRequestManager.SERVER_ADDRESS + "/appOrder/app_order_consumeDetailList.action", map, GET_CONSUME_DETAIL_LIST_TAG, new ResponseHandler(context, ResponseHandler.MODEL_COUPON));
    }

    /**
     * 查询代金券
     *
     * @param context
     * @param checkCode
     */
    public static void getCheckOrder(Context context, String checkCode) {
        String sign = String.format("%s%s", checkCode, Constant.MD5_KEY);//下一层处理
        Map<String, String> map = new HashMap<>();
        map.put("checkCode", DesUtil.encrypt(checkCode));
        map.put("sign", MD5Util.toDigest(sign));
        NetworkRequest.post(CommonRequestManager.SERVER_ADDRESS + "/appOrder/app_order_appFindOrderByCkCode.action", map, VALIDATE_TAG, new ResponseHandler(context, ResponseHandler.MODEL_COUPON));
    }

    /**
     * 消费
     *
     * @param context
     * @param checkCode
     */
    public static void getConsumeOrder(Context context, String checkCode) {
        String sign = String.format("%s%s", checkCode, Constant.MD5_KEY);//下一层处理
        Map<String, String> map = new HashMap<>();
        map.put("checkCode", DesUtil.encrypt(checkCode));
        map.put("sign", MD5Util.toDigest(sign));
        NetworkRequest.post(CommonRequestManager.SERVER_ADDRESS + "/appOrder/app_order_appValidateCouponInfo.action", map, CONSUME_TAG, new ResponseHandler(context, ResponseHandler.MODEL_COUPON));
    }



    /**
     * 解析结果中真正数据--代金券
     * 这样的好处是可以统一捕捉并处理数据错误的问题,如果返回的数据 不符合要求，可以在这里拦截，将code改成对应的errorCode即可
     */
    public static BaseEvent couponResultIndeed(String result, int action, BaseEvent event) throws JSONException, JsonSyntaxException {
        switch (action) {
            case GET_CONSUME_DETAIL_LIST_TAG://获取验券历史列表
                event.obj = CommonRequestManager.resultToObject(result, OrderList.class);
                break;
            case VALIDATE_TAG://查询对象
                event.obj = GsonManager.getInstance().fromJson(result, AOrderRecordEntity.class);
                break;
            case CONSUME_TAG://验证的对象
                event.obj = GsonManager.getInstance().fromJson(result, AOrderRecordEntity.class);
                break;
            default:
                break;
        }
        return event;
    }


}
