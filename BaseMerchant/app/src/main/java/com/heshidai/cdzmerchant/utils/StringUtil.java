/**
 * ***************************************************************************
 * Copyright (C) 2014 ShenZhen HeShiDai Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为合时代控股有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 * ***************************************************************************
 */
package com.heshidai.cdzmerchant.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;

import com.heshidai.cdzmerchant.R;
import com.heshidai.cdzmerchant.app.HSDApplication;
import com.heshidai.cdzmerchant.common.Constant;
import com.heshidai.cdzmerchant.common.SPManager;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 */
public class StringUtil {

    //手机正则表达式
    private final static String phone = "^ [1-9]\\d{8}$";
    //邮箱正则表达式
    private final static String email = "[a-zA-Z0-9_]+@[a-zA-Z0-9]+(\\.[a-zA-Z]+)+";
    //用户名正则表达式
    private final static String userName = "^\\w{6,20}$";

    /**
     * 判断是否是合法的用户名
     *
     * @param name 名称
     * @return 返回boolean
     */
    public static boolean isUserName(String name) {
        if (TextUtils.isEmpty(name)) {
            return false;
        }
        Pattern pattern = Pattern.compile(userName);
        return pattern.matcher(name).matches();
    }

    /**
     * 判断是否是正确的手机号码
     *
     * @param number 手机号码
     * @return 返回boolean
     */
    public static boolean isPhoneNumber(String number) {
        if (TextUtils.isEmpty(number)) {
            return false;
        }
        Pattern pattern = Pattern.compile(phone);
        return pattern.matcher(number).matches();
    }

    /**
     * 判断是不是一个合法的邮箱
     *
     * @param mail 邮箱
     * @return 返回boolean
     */
    public static boolean isEmail(String mail) {
        if (TextUtils.isEmpty(mail)) {
            return false;
        }
        Pattern pattern = Pattern.compile(email);
        return pattern.matcher(mail).matches();
    }

    /**
     * 密码是否正确
     *
     * @param pwd
     * @return
     */
    public static boolean isPassword(String pwd) {
        if (!TextUtils.isEmpty(pwd)) {
            if (pwd.length() >= 6 && pwd.length() <= 20) {
                return true;
            }
        }
        return false;
    }

    /**
     * 修改身份证号码为前三后四，其余用"****"代替
     *
     * @param idCard
     * @return 返回替换后的身份证号
     */
    public static String changeIdCard(String idCard) {
        if (TextUtils.isEmpty(idCard)) {
            return "";
        }
        return idCard.substring(0, 3) + "***********" + idCard.substring(idCard.length() - 4, idCard.length());
    }

    /**
     * 修改手机号码为前三后四，其余用"****"代替
     *
     * @param phone 手机号码
     * @return 返回修改后的手机号码
     */
    public static String changePhoneNumber(String phone) {
        if (TextUtils.isEmpty(phone)) {
            return "";
        }
        return phone.substring(0, 3) + "****" + phone.substring(phone.length() - 4, phone.length());
    }

    /**
     * 判断字符串是否存在该子串
     *
     * @param oldString 旧的字符串
     * @param subString 子串
     * @return 返回boolean
     */
    public static boolean indexOfSubString(String oldString, String subString) {
        if (TextUtils.isEmpty(oldString) || TextUtils.isEmpty(subString)) {
            return false;
        }
        return (oldString.contains(subString));
    }

    /**
     * 获取MD5加密字符串
     *
     * @param oldString 老的字符串
     * @return 返回加密过后的字符串
     */
    public static String getMd5(String oldString) {
        if (TextUtils.isEmpty(oldString)) {
            return "";
        }
        byte[] hash = null;
        try {
            hash = MessageDigest.getInstance("MD5").digest(oldString.getBytes("UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) {
                hex.append("0");
            }
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }

    /**
     * 字节转字符串
     *
     * @param bytes 字节数组
     * @return 返回字符串
     */
    public static String byteToString(byte[] bytes) {
        String result = "";
        try {
            if (null != bytes && bytes.length > 0) {
                result = new String(bytes, "UTF-8");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 将double类型的数据转成 36125.0——》36,125.00的格式,固定保留两位小数 切割方式
     *
     * @param number 数据
     * @return 返回格式化结果
     */
    public static String formatNumToString(double number) {
        String tempStr = formatNumToChina(number);
        String[] tempSplits = tempStr.split("\\.");
        if (tempSplits != null && tempSplits.length > 0) {
            if (tempSplits.length < 2) {
                return tempStr + ".00";
            } else {
                String point = tempSplits[1];
                if (point.length() == 0) {
                    return tempSplits[0] + ".00";
                }
                if (point.length() == 1) {
                    return tempSplits[0] + "." + tempSplits[1] + "0";
                }
                if (point.length() > 2) {
                    return tempSplits[0] + "." + point.substring(0, 2);
                }
                return tempStr;
            }
        }
        return "";
    }

    /**
     * 将double类型的数据转成1,236.5格式，eg:36125.0->36,125
     *
     * @param number 数据
     * @return 返回格式化数据
     */
    public static String formatNumToChina(double number) {
        NumberFormat nf = NumberFormat.getInstance(Locale.CHINA);
        return nf.format(number);
    }

    /**
     * 格式化钱
     *
     * @param money 钱
     * @return 返回格式化之后的钱
     */
    public static String formatMoney(double money) {
        if (money < 10000) {
            return formatNumToString(money);
        }
        if (money >= 10000) {
            double i = money / 10000;
            return String.format("%s万", formatNumToString(i));
        }
        return "";
    }

    /**
     * 格式化Double，保留2位小数点
     *
     * @param number
     * @return
     */
    public static String formatDouble(double number) {
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(number);
    }

    /**
     * 去除字符串中的空格、回车、换行符、制表符
     *
     * @param oldString 旧的字符串
     * @return 返回去除后的字符串
     */
    public static String replaceBlank(String oldString) {
        String newString = "";
        if (oldString != null && oldString.length() > 0) {
            Pattern pattern = Pattern.compile("\\s*|\t|\r|\n");
            Matcher matcher = pattern.matcher(oldString);
            newString = matcher.replaceAll("");
        }
        return newString;
    }

    /**
     * 删除制定字符
     *
     * @param editable Editable
     * @param ch       需要删除的字符
     */
    public static void deleteChar(Editable editable, char ch) {
        if (editable.length() > 0) {
            int pos = editable.length() - 1;
            char c = editable.charAt(pos);
            if (c == ch) {
                editable.delete(pos, pos + 1);
            }
        }
    }

    /**
     * 删除多个字符
     *
     * @param editable Editable
     * @param ch       字符数组
     */
    public static void deleteChar(Editable editable, char[] ch) {
        if (editable.length() > 0) {
            int pos = editable.length() - 1;
            char c = editable.charAt(pos);
            int len = ch.length;
            for (int i = 0; i < len; i++) {
                if (c == ch[i]) {
                    editable.delete(pos, pos + 1);
                }
            }
        }
    }

    /**
     * 删除字符串里面的子串
     *
     * @param oldString    老的字符串
     * @param deleteString 需要删除的字符串
     * @return 返回删除过后的字符串
     */
    public static String deleteChar(String oldString, String deleteString) {
        if (TextUtils.isEmpty(oldString) || TextUtils.isEmpty(deleteString)) {
            return oldString;
        }
        if (oldString.contains(deleteString)) {
            oldString = oldString.replaceAll(deleteString, " ");
            oldString = StringUtil.replaceBlank(oldString);
        }
        return oldString;
    }

    /**
     * 判断单个字符是不是中文
     *
     * @param c
     * @return
     */
    private static boolean isChinese(char c) {
        Character.UnicodeBlock unicodeBlock = Character.UnicodeBlock.of(c);
        return null != unicodeBlock && (unicodeBlock == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || unicodeBlock == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || unicodeBlock == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || unicodeBlock == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || unicodeBlock == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || unicodeBlock == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS);
    }

    /**
     * 判断字符串是不是中文
     *
     * @param name
     * @return
     */
    public static boolean isChinese(String name) {
        char[] ch = name.toCharArray();
        if (null != ch && ch.length > 0) {
            for (int i = 0, len = ch.length; i < len; i++) {
                char c = ch[i];
                if (isChinese(c)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断给定字符串是否是空白串；其中空白串是指由空格、制表符、回车符、换行符组成的字符串
     *
     * @param input 字符串
     * @return boolean 若输入字符串为null或空字符串，返回true
     */
    public static boolean isEmptyOrNull(String input) {
        boolean flag = false;
        if (input == null || "".equals(input) || "null".equalsIgnoreCase(input)) {
            flag = true;
        } else {
            int length = input.length();
            for (int i = 0; i < length; i++) {
                char c = input.charAt(i);
                if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                    flag = false;
                    break;
                }
            }
        }
        return flag;
    }

    /**
     * 将金钱元单位转为分单位
     *
     * @param yuan
     * @return
     */
    public static long yuanToFen(long yuan) {
        return Arith.mul(yuan, 100);
    }

    /**
     * 将金钱分单位转为元单位
     *
     * @param fen
     * @return
     */
    public static long fenToYuan(long fen) {
        return Arith.div(fen, 100);
    }

    /**
     * 将金钱分单位转为元单位
     *
     * @param str
     * @return
     */
    public static String fenToYuan(String str) {
        String reStr;
        if (TextUtils.isEmpty(str) || "null".equals(str)) {//空时直接返回""
            reStr = "0";
        } else {
            int length = str.length();
            switch (length) {
                case 1:
                    reStr = (str.equals("0")) ? "0" : "0.0" + str;
                    break;
                case 2:
                    reStr = "0." + str;
                    break;
                default:
                    String s_1 = str.substring(str.length() - 2, str.length());//最后两位
                    reStr = String.format("%s%s", str.substring(0, str.length() - 2), ("00".equals(s_1) ? "" : "." + s_1));
                    break;
            }
        }
        return reStr;
    }

    /**
     * 将金钱元单位转为分单位
     *
     * @param str
     * @return
     */
    public static String yuanToFen(String str) {
        String fen = Constant.DEFAULT_STRING;
        try {
            double yuan = Double.valueOf(str);
            fen = String.valueOf((int) (yuan * 100));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return fen;
    }

    /**
     * 将金钱元单位转为分单位
     *
     * @param yuan
     * @return
     */
    public static int yuanToFen(double yuan) {
        return (int) yuan * 100;
    }

    /**
     * 将金钱元单位转为分单位
     *
     * @param yuan
     * @return
     */
    public static int yuanToFen(int yuan) {
        return yuan * 100;
    }

    /**
     * 在数字型字符串千分位加逗号
     *
     * @param str
     * @return
     */
    public static String NumDddComma(String str) {
        boolean neg = false;//判断是否是负数
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        if (str.startsWith("-")) {  //处理负数
            str = str.substring(1);
            neg = true;
        }
        String tail = null;
        if (str.indexOf('.') != -1) { //处理小数点
            tail = str.substring(str.indexOf('.'));
            str = str.substring(0, str.indexOf('.'));
        }
        StringBuilder sb = new StringBuilder(str);
        sb.reverse();
        for (int i = 3; i < sb.length(); i += 4) {
            sb.insert(i, ',');
        }
        sb.reverse();
        if (neg) {
            sb.insert(0, '-');
        }
        if (tail != null) {
            sb.append(tail);
        }
        return sb.toString();
    }

    /**
     * 折扣乘以10
     *
     * @param discount
     * @return
     */
    public static float discount(float discount) {
        return discount * 10;
    }

    /**
     * 代金券验证码加空格
     *
     * @param checkCode
     * @return
     */
    public static String addSpace(String checkCode) {
        if (!TextUtils.isEmpty(checkCode)) {
            StringBuilder sb = new StringBuilder(checkCode);
            for (int i = 4; i < sb.length(); i += 5) {
                sb.insert(i, ' ');
            }
            return sb.toString();
        } else {
            return "";
        }
    }


    /**
     * 检查是否包含关键词
     *
     * @param context
     * @param content 关键词
     * @return
     */
    public static boolean checkWords(Context context, String content) {
        if (!TextUtils.isEmpty(content)) {
            StringBuffer sb = new StringBuffer();
            InputStream is = null;
            try {
                Properties pro = new Properties();
                is = context.getAssets().open("words.properties");
                pro.load(is);
                Enumeration enu = pro.propertyNames();
                while (enu.hasMoreElements()) {
                    sb.append(new String(enu.nextElement().toString().getBytes("ISO-8859-1"), "GBK"));
                    sb.append(",");
                }
                if (!TextUtils.isEmpty(sb)) {
                    sb.deleteCharAt(sb.length() - 1);
                    for (String word : sb.toString().split(",")) {
                        if (content.contains(word)) {
                            return true;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (is != null) {
                        is.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    /**
     * 读取AndroidManifest.xml里面的<application></application>里面的<meta-data></meta-data>信息
     *
     * @param name 名称
     * @return
     */
    public static String readManifest(String name) {
        String value = "";
        if (!TextUtils.isEmpty(name)) {
            try {
                ApplicationInfo applicationInfo = HSDApplication.getApplication().getPackageManager().getApplicationInfo(HSDApplication.getApplication().getPackageName(), PackageManager.GET_META_DATA);
                if (applicationInfo != null) {
                    value = applicationInfo.metaData.getString(name);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return value;
    }

//	/**
//	 * 读取渠道ID
//	 * @return
//	 */
//	public static String readChannelId() {
//		String channelId = readManifest(Constant.UMENG_CHANNEL_ID);
//		if(!TextUtils.isEmpty(channelId)) {
//			channelId = channelId.substring(channelId.indexOf("_") + 1);
//		}
//		return channelId;
//	}


    /**
     * 对指定文本进行颜色和大小的改变处理
     *
     * @param first       第一段文本
     * @param second      第二段文本
     * @param firstColor  第一段文本的颜色
     * @param secondColor 第二段文本的颜色
     * @param firstSize   第一段文本的大小 单位px
     * @param secondSize  第二段文本的大小 单位px
     * @return 处理过的String
     */
    public static SpannableString spanString(String first, String second, int firstColor,
                                             int secondColor,
                                             int firstSize, int secondSize) {
        if (TextUtils.isEmpty(first) || TextUtils.isEmpty(second)) {
            return null;
        }
        SpannableString spannableString = new SpannableString(first + second);
        int start = first.length();
        int end = spannableString.length();
        if (firstSize != -1) {
            AbsoluteSizeSpan mFirstSize = new AbsoluteSizeSpan(firstSize);
            spannableString.setSpan(mFirstSize, 0, start, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        if (firstColor != -1) {
            ForegroundColorSpan mFirstColor = new ForegroundColorSpan(firstColor);
            spannableString.setSpan(mFirstColor, 0, start, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        if (secondSize != -1) {
            AbsoluteSizeSpan mSecondSize = new AbsoluteSizeSpan(secondSize);
            spannableString.setSpan(mSecondSize, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        if (secondColor != -1) {
            ForegroundColorSpan mSecondColor = new ForegroundColorSpan(secondColor);
            spannableString.setSpan(mSecondColor, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        return spannableString;
    }

    /**
     * 对指定文本进行颜色和大小的改变处理
     *
     * @param first       第一段文本
     * @param second      第二段文本
     * @param firstColor  第一段文本的颜色
     * @param secondColor 第二段文本的颜色
     * @return 处理过的String
     */
    public static SpannableString spanString(String first, String second, int firstColor,
                                             int secondColor) {
        if (TextUtils.isEmpty(first) || TextUtils.isEmpty(second)) {
            return null;
        }
        SpannableString spannableString = new SpannableString(first + second);
        int start = first.length();
        int end = spannableString.length();

        if (firstColor != -1) {
            ForegroundColorSpan mFirstColor = new ForegroundColorSpan(firstColor);
            spannableString.setSpan(mFirstColor, 0, start, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        if (secondColor != -1) {
            ForegroundColorSpan mSecondColor = new ForegroundColorSpan(secondColor);
            spannableString.setSpan(mSecondColor, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        return spannableString;
    }


    /**
     * 对指定文本进行颜色和大小的改变处理
     *
     * @param first       第一段文本
     * @param second      第二段文本
     * @param firstColor  第一段文本的颜色
     * @param secondColor 第二段文本的颜色
     * @param isBold      第二段文本的是否加粗
     * @return 处理过的String
     */
    public static SpannableString spanString(String first, String second, int firstColor,
                                             int secondColor, boolean isBold) {
        if (TextUtils.isEmpty(first) || TextUtils.isEmpty(second)) {
            return null;
        }
        SpannableString spannableString = new SpannableString(first + second);
        int start = first.length();
        int end = spannableString.length();

        if (firstColor != -1) {
            ForegroundColorSpan mFirstColor = new ForegroundColorSpan(firstColor);
            spannableString.setSpan(mFirstColor, 0, start, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        if (secondColor != -1) {
            ForegroundColorSpan mSecondColor = new ForegroundColorSpan(secondColor);
            spannableString.setSpan(mSecondColor, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        if (isBold) {
            StyleSpan span = new StyleSpan(Typeface.BOLD);//加粗
            spannableString.setSpan(span, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }


        return spannableString;
    }

    /**
     * 获取星期几     * @return
     */
    public static Map<Integer, String> getWeekString() {
        Map<Integer, String> map = new LinkedHashMap<>();
        map.put(1, HSDApplication.getApplication().getString(R.string.Monday));
        map.put(2, HSDApplication.getApplication().getString(R.string.Tuesday));
        map.put(3, HSDApplication.getApplication().getString(R.string.Wednesday));
        map.put(4, HSDApplication.getApplication().getString(R.string.Thursday));
        map.put(5, HSDApplication.getApplication().getString(R.string.Friday));
        map.put(6, HSDApplication.getApplication().getString(R.string.Saturday));
        map.put(0, HSDApplication.getApplication().getString(R.string.Sunday));
        return map;
    }

    /**
     * 根据商家Id 返回一个字段
     *
     * @param merId
     * @return
     */
    public static String getFieldForMerId(String merId,String order) {

        return String.format("%s%s", merId, order);
    }

    /**
     * 数字加一个空格   index 是需要加空格的数字+1
     *
     * @param index
     * @param sb
     * @return
     */
    public static StringBuilder addSpace(int index, StringBuilder sb) {
        if ((sb.length() + 1) % index == 0 && sb.length()>0) {
            sb.append(" ");
        }
        return sb;
    }

    /**
     * 除去 空格
     *
     * @param sb
     * @return
     */
    public static StringBuilder divSpace(String sb) {
        StringBuilder newSb = new StringBuilder();
        if (null == sb) {
            newSb.append("");
        }
        for (int i = 0; i < sb.length(); i++) {
            if (!sb.substring(i, i + 1).equals(" ")) {
                newSb.append(sb.substring(i, i + 1));
            }
        }
        return newSb;
    }
}
