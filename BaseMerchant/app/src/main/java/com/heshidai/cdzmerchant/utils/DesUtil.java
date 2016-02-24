/******************************************************************************
 * Copyright (C) 2014 ShenZhen HeShiDai Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为合时代控股有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 *****************************************************************************/

package com.heshidai.cdzmerchant.utils;


import android.text.TextUtils;

import com.heshidai.cdzmerchant.common.Constant;

import org.apache.commons.net.util.Base64;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * 3DES加密解密
 * @author xiao di fa
 */
public class DesUtil {
    private final static String DES = "DES";

    /**
     * Description 根据键值进行加密
     * @param data 需要加密的数据
     * @return 加密过后的数据
     */
    public static String encrypt(String data) {
        String result = "";
        try {
            if(!TextUtils.isEmpty(data)) {
                byte[] bt = encrypt(data.getBytes(), Constant.DES_KEY.getBytes());
                if(null != bt && bt.length > 0) {
                    result = Base64.encodeBase64StringUnChunked(bt);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Description 根据键值进行解密
     * @param data 加密的数据
     * @return 解密过后的数据
     */
    public static String decrypt(String data) {
        String result = "";
        try {
            if(!TextUtils.isEmpty(data)) {
                byte[] bt = decrypt(Base64.decodeBase64(data), Constant.DES_KEY.getBytes());
                if(null != bt && bt.length > 0) {
                    result = new String(bt, "UTF-8");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Description 根据键值进行加密
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws Exception
     */
    private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
        // 生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);
        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey secretKey = keyFactory.generateSecret(dks);
        // Cipher对象实际完成加密操作
        Cipher cipher = Cipher.getInstance(DES);
        // 用密钥初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, sr);
        return cipher.doFinal(data);
    }

    /**
     * Description 根据键值进行解密
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws Exception
     */
    private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        // 生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);
        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey secretKey = keyFactory.generateSecret(dks);
        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance(DES);
        // 用密钥初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, secretKey, sr);
        return cipher.doFinal(data);
    }
}
