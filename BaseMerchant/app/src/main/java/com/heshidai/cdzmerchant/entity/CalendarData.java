/**
 * **************************************************************************
 * Copyright (C) 2014 ShenZhen HeShiDai Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为合时代控股有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 * **************************************************************************
 */
package com.heshidai.cdzmerchant.entity;

/**
 * 类功能介绍。
 * 修改记录: 修改历史记录，包括修改日期、修改者及修改内容
 * 版权所有: 版权所有(C)2010-2014
 * 公　　司: 深圳合时代金融服务有限公司
 *
 * @author 万坤
 * @version 1.0.0
 * @date 2015-09-29
 * @history
 */


public class CalendarData {

    public int year;
    public int month;
    public int day;
    public int week = -1;
    public boolean isNextMonthDay = false;//是否是下个月中的日期
    public boolean isLastMonthDay = false;//是否是上个月中的日期
    public CalendarData() { }

    public CalendarData(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public boolean isSameDay(CalendarData data) {
        return (data.day == day && data.month == month &&data.year == year);
    }

    @Override
    public String toString() {
        return "CalendarData{" +
                "year=" + year +
                ", month=" + month +
                ", day=" + day +
                ", week=" + week +
                ", isNextMonthDay=" + isNextMonthDay +
                ", isLastMonthDay=" + isLastMonthDay +
                '}';
    }
}
