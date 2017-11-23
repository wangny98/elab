/*
 * 
 *  @(#)WeekUtil.java Created on 2011-7-12
 *
 * Copyright 2004-2005 Huawei Tech. Co. Ltd. All Rights Reserved.
 * 
 * Description 
 * 
 * CopyrightVersion 
 *
 */
package com.device.core.util;

import java.util.Calendar;
import java.util.Date;

/**
 * 
 * 获取周的工具类
 * 
 * @author  geek
 * @version  [版本号, 2012-7-26]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public final class WeekUtil {
    /**
     * 时间
     */
    private static final String[] SUNDAY = { "星期日", "Sunday" };

    private static final String[] MONDAY = { "星期一", "Monday" };

    private static final String[] TUESDAY = { "星期二", "Tuesday" };

    private static final String[] WEDNESDAY = { "星期三", "Wednesday" };

    private static final String[] THURSDAY = { "星期四", "Thursday" };

    private static final String[] FRIDAY = { "星期五", "Friday" };

    private static final String[] SATURDAY = { "星期六", "Saturday" };

    /**
     * 一周中时间的排序
     */
    private static final int SUN = 1;

    private static final int MON = 2;

    private static final int TUE = 3;

    private static final int WED = 4;

    private static final int THU = 5;

    private static final int FRI = 6;

    /**
     * 时间处理函数
     */
    private static Calendar calendar = Calendar.getInstance();

    private WeekUtil() {
    }

    /**
     * 获取时间
     * @param date 时间
     * @param local 语言(us or cn)
     * @return 周
     */
    public static synchronized String getDayOfWeekByDate(Date date, String local) {
        String dayOfWeek = "";

        /**
         * 获取时间
         */
        calendar.setTime(date);

        int indexOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        /**
         * 如果语言是英文,则返回英文时间
         * 否则返回中文
         */
        if ("us".equals(local)) {
            dayOfWeek = getDayOfWeekByEN(indexOfWeek);
        } else {
            dayOfWeek = getDayOfWeekByCN(indexOfWeek);
        }

        return dayOfWeek;
    }

    /**
     * 获取一周中的中文时间
     * @param i 一周中的时间排序
     * @return 中文时间
     */
    private static String getDayOfWeekByCN(int i) {
        String dayOfWeek = "";

        switch (i) {
        case SUN:
            dayOfWeek = SUNDAY[0];
            break;
        case MON:
            dayOfWeek = MONDAY[0];
            break;
        case TUE:
            dayOfWeek = TUESDAY[0];
            break;
        case WED:
            dayOfWeek = WEDNESDAY[0];
            break;
        case THU:
            dayOfWeek = THURSDAY[0];
            break;
        case FRI:
            dayOfWeek = FRIDAY[0];
            break;
        default:
            dayOfWeek = SATURDAY[0];
            break;
        }

        return dayOfWeek;
    }

    /**
     * 获取一天中的英文时间
     * @param i 一周中的时间排序
     * @return 英文时间
     */
    private static String getDayOfWeekByEN(int i) {
        String dayOfWeek = "";

        switch (i) {
        case SUN:
            dayOfWeek = SUNDAY[1];
            break;
        case MON:
            dayOfWeek = MONDAY[1];
            break;
        case TUE:
            dayOfWeek = TUESDAY[1];
            break;
        case WED:
            dayOfWeek = WEDNESDAY[1];
            break;
        case THU:
            dayOfWeek = THURSDAY[1];
            break;
        case FRI:
            dayOfWeek = FRIDAY[1];
            break;
        default:
            dayOfWeek = SATURDAY[1];
            break;
        }

        return dayOfWeek;
    }

}
