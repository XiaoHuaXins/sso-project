package com.smart.sso.demo.utils;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Author xhx
 * @Date 2022/1/26 17:47
 */
public class TimeUtils {
    public static String getUTCTime() {
        LocalDateTime now = LocalDateTime.now(Clock.systemUTC());
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return pattern.format(now);
    }
}
