package com.github.fhr.baisc.time;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

public class OriginDateTimeUtilsTest {

    @org.junit.Test
    public void convertTimeZone() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM:dd HH:mm:ss");

        Date time = new Date();
        System.out.println("beijing time: " + simpleDateFormat.format(time));
        Date lonDonTime = OriginDateTimeUtils.convertTimeZone(time, "Asia/Shanghai", "Europe/London");
        System.out.println("longDon time: " + simpleDateFormat.format(lonDonTime));
        Date losTime = OriginDateTimeUtils.convertTimeZone(time, "Asia/Shanghai", "America/Los_Angeles");
        System.out.println("Los_Angeles time:" + simpleDateFormat.format(losTime));
    }
}