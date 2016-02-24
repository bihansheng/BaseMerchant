/**
 * ***************************************************************************
 * Copyright (C) 2014 ShenZhen HeShiDai Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为合时代控股有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 * ***************************************************************************
 */
package com.heshidai.cdzmerchant.utils;


import com.heshidai.cdzmerchant.app.HSDApplication;
import com.heshidai.cdzmerchant.common.SPManager;
import com.heshidai.cdzmerchant.entity.User;
import com.heshidai.cdzmerchant.jpush.JpushUtil;

/**用户类相关方法
 * @author 万坤
 *
 */
public class UserUtil {

    /**
     * 保存从网络上请求到的数据用户信息
     * @param tempUser
     */
    public static void saveUserInfo(User tempUser) {
        if(null!=tempUser) {
            HSDApplication.setToken(tempUser.getToken());
            User user = (User) PreferencesUtils.readObject(HSDApplication.getApplication(), PreferencesUtils.PREFERENCE_NAME_DATA, SPManager.USER);
            if (null == user ) {
                user = new User();
            }
            user.setMerId(DesUtil.encrypt(String.valueOf(tempUser.getMerId())));
            user.setMerName(DesUtil.encrypt(tempUser.getMerName()));
            user.setToken(tempUser.getToken());
            user.setIsStore(tempUser.getIsStore());
            if (null != tempUser.getPassword()) {
                user.setPassword(DesUtil.encrypt(tempUser.getPassword()));
                user.setMerAccount(DesUtil.encrypt(tempUser.getMerAccount()));
            }
            JpushUtil.setAlias(tempUser.getMerId());//别名
            PreferencesUtils.saveObject(HSDApplication.getApplication(), PreferencesUtils.PREFERENCE_NAME_DATA, SPManager.USER, user);
        }
    }

    /**
     * 将用户信息进行完全解密
     *
     */
    public static User getUserInfo(){
        User user = new User();
        User getUser = (User) PreferencesUtils.readObject(HSDApplication.getApplication(), PreferencesUtils.PREFERENCE_NAME_DATA, SPManager.USER);
        if (null!=getUser){
            user.setMerId(DesUtil.encrypt(getUser.getMerId()));
            user.setMerName(DesUtil.encrypt(getUser.getMerName()));
            user.setPassword(DesUtil.encrypt(getUser.getPassword()));
            user.setIsStore(getUser.getIsStore());
            user.setToken(getUser.getToken());
            user.setMerAccount(getUser.getMerAccount());
            user.setSign(getUser.getSign());
        }
        return user;
    }
}
