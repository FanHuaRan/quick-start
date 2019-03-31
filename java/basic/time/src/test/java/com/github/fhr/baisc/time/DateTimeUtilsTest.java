package com.github.fhr.baisc.time;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static org.junit.Assert.*;

public class DateTimeUtilsTest {

    @Test
    public void convertTimeZone() {
        LocalDateTime time = LocalDateTime.now();
        System.out.println("beijing time: " + time);

        LocalDateTime lonDonTime = DateTimeUtils.convertTimeZone(time, "Asia/Shanghai","Europe/London");
        System.out.println("longDon time: " + lonDonTime);

        LocalDateTime losTime = DateTimeUtils.convertTimeZone(time, "Asia/Shanghai","America/Los_Angeles");
        System.out.println("Los_Angeles time:" + losTime);
    }
}