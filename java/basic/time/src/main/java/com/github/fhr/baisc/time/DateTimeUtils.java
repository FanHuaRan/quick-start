package com.github.fhr.baisc.time;

import java.text.ParseException;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * @author Fan Huaran
 * created on 2019/2/21
 * @description
 */
public class DateTimeUtils {
    static String DEFAULT_TIMEZONE = "GMT+8";

    /**
     * 转换时间时区
     *
     * @param dateTime       本地时间
     * @param sourceTimeZone
     * @param targetTimeZone 目标时间时区
     * @return
     * @throws ParseException
     */
    public static LocalDateTime convertTimeZone(LocalDateTime dateTime, String sourceTimeZone, String targetTimeZone) {
        if (isEmpty(sourceTimeZone)) {
            sourceTimeZone = DEFAULT_TIMEZONE;
        }

        if (isEmpty(targetTimeZone)) {
            targetTimeZone = DEFAULT_TIMEZONE;
        }

        ZoneId sourceZoneId = ZoneId.of(sourceTimeZone);
        ZoneId targetZoneId = ZoneId.of(targetTimeZone);

        ZonedDateTime zonedDateTime = dateTime.atZone(sourceZoneId);
        return LocalDateTime.ofInstant(zonedDateTime.toInstant(), targetZoneId);
    }

    /**
     * 获取当前在指定时区的时间
     *
     * @param targetTimeZone
     * @return
     */
    public static LocalDateTime getNowTimeZone(String targetTimeZone) {
        if (isEmpty(targetTimeZone)) {
            targetTimeZone = DEFAULT_TIMEZONE;
        }

        ZoneId targetZoneId = ZoneId.of(targetTimeZone);

        return LocalDateTime.ofInstant(ZonedDateTime.now().toInstant(), targetZoneId);
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
