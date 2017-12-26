package com.zdan91.duiker.utils;


import org.joda.time.DateTime;

import java.util.Date;

/**
 * 时间处理工具类
 */
public class DateTimeUtil {

    /**
     * 获取今天的开始时间  2017-12-26 00:00:00
     *
     * @return
     */
    public static Date getStartOfToday() {
        return getStartOfDay(0);
    }

    /**
     * 获取某天开始的时间
     * 比如: 2017-12-26 00:00:00
     *
     * @param offSetDay 相对于今天的偏移量.昨天-1,明天1
     * @return
     */
    public static Date getStartOfDay(int offSetDay) {
        return new DateTime().plusDays(offSetDay).withMillisOfDay(0).toDate();
    }

}
