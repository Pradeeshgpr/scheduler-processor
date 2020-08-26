package com.example.scheduler.processor.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;

public class DateUtils {

    public static final long getUTCTime() {
        LocalDateTime localDateTime =getUTCLocalDateTime();
        return localDateTime.toEpochSecond(ZoneOffset.UTC);
    }

    public static final LocalDateTime getUTCLocalDateTime() {
        return LocalDateTime.now(ZoneId.of(getDefaultTimeZone()));
    }

    public static final String getDefaultTimeZone() {
        return "UTC";
    }

    public static Date getUTCDate() {
        return new Date().from(getUTCLocalDateTime().atZone(ZoneId.of(getDefaultTimeZone())).toInstant());
    }
}
