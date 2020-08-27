package com.example.scheduler.processor.utils;

import org.springframework.scheduling.support.CronSequenceGenerator;

import java.util.Date;

public class CronUtils {

    public static final long DEFAULT_NEXT_RUN_VALUE = 1000;

    public static final long getNextRun() {
        return DateUtils.getUTCTime() + DEFAULT_NEXT_RUN_VALUE;
    }

    public static final long getNextRun(Date date, String cronExpression) {
        return new CronSequenceGenerator(cronExpression).next(date).getTime();
    }

    public static final long getNextRun(String cronExpression) {
        return new CronSequenceGenerator(cronExpression).next(DateUtils.getUTCDate()).getTime();
    }

}
