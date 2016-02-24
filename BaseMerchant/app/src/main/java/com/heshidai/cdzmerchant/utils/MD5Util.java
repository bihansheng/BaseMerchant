/******************************************************************************
 * Copyright (C) 2014 ShenZhen HeShiDai Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为合时代控股有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.heshidai.cdzmerchant.utils;

import java.security.MessageDigest;

/**
 * MD5加密工具类
 * @author zhou xiao jun
 */
public class MD5Util {

    /**
     * MD5加密
     * @param str
     * @return
     */
    public static String toDigest(String str) {
        try {
            MessageDigest alga = MessageDigest.getInstance("MD5");
            alga.update(str.getBytes());
            byte[] digesta = alga.digest();
            return byte2hex(digesta);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * 字节数组转哈希值
     * @param b
     * @return
     */
    private static String byte2hex(byte[] b) {
        String hs = "";
        String temp = "";
        for (int n = 0; n < b.length; n++) {
            temp = (Integer.toHexString(b[n] & 0XFF));
            if (temp.length() == 1) {
                hs = hs + "0" + temp;
            } else {
                hs = hs + temp;
            }
            if (n < b.length - 1) {
                hs = hs + "";
            }
        }
        return hs;
    }

}