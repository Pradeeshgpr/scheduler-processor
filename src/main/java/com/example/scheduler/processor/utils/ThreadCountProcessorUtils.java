package com.example.scheduler.processor.utils;

import com.example.scheduler.processor.conzt.ThreadConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class ThreadCountProcessorUtils {

    @Autowired
    private Environment environment;

    public final int getFreeThreadCount(int activeCount, int queueCount, int maximumPoolSize) {
        return maximumPoolSize - (activeCount+ queueCount);
    }

    public final int getActiveThreadCount() {
        return Integer.parseInt(environment.getProperty(ThreadConfiguration.NUM_THREAD, ThreadConfiguration.NUM_THREAD_DEFAULT));
    }
    public final int getQeueThreadCount() {
        return Integer.parseInt(environment.getProperty(ThreadConfiguration.MAX_POOL_SIZE, ThreadConfiguration.MAX_POOL_SIZE_DEFAULT));
    }

}
