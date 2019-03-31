package com.github.fhr.baisc.time;

import java.text.ParseException;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author Fan Huaran
 * created on 2019/2/21
 * @description
 */
public class OriginDateTimeUtils {

    static String DEFAULT_TIMEZONE = "GMT+8";

    /**
     * 转换时间时区
     *
     * @param date           本地时间
     * @param sourceTimeZone 源时间时区
     * @param targetTimeZone 目标时间时区
     * @return
     * @throws ParseException
     */
    public static Date convertTimeZone(Date date, String sourceTimeZone, String targetTimeZone) {
        if (isEmpty(sourceTimeZone)) {
            sourceTimeZone = DEFAULT_TIMEZONE;
        }

        if (isEmpty(targetTimeZone)) {
            targetTimeZone = DEFAULT_TIMEZONE;
        }
        //获取传入的时间值
        long time = date.getTime();

        //获取源时区时间相对的GMT时间
        long sourceRelativelyGMT = time - TimeZone.getTimeZone(sourceTimeZone).getRawOffset();

        //GMT时间+目标时间时区的偏移量获取目标时间
        long targetTime = sourceRelativelyGMT + TimeZone.getTimeZone(targetTimeZone).getRawOffset();

        return new Date(targetTime);
    }

    /**
     * 获取当前在指定时区的时间
     *
     * @param targetTimeZone
     * @return
     */
    public static Date getNowTimeZone(String targetTimeZone) {
        if (isEmpty(targetTimeZone)) {
            targetTimeZone = DEFAULT_TIMEZONE;
        }
        //获取传入的时间值
        long time = new Date().getTime();

        //获取源时区时间相对的GMT时间
        long sourceRelativelyGMT = time - TimeZone.getDefault().getRawOffset();

        //GMT时间+目标时间时区的偏移量获取目标时间
        long targetTime = sourceRelativelyGMT + TimeZone.getTimeZone(targetTimeZone).getRawOffset();

        return new Date(targetTime);
    }

    /**
     * Check empty string
     *
     * @param value
     * @return
     */
    private static boolean isEmpty(String value) {
        if (null == value || value.trim().length() <= 0) {
            return true;
        }
        return false;
    }

}
