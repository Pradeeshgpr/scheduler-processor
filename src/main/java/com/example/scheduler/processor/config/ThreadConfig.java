package com.example.scheduler.processor.config;

import com.example.scheduler.processor.conzt.ThreadConfiguration;
import com.example.scheduler.processor.utils.ThreadCountProcessorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
@PropertySource({"classpath:thread/thread-config-${spring.profiles.active}.properties"})
public class ThreadConfig {

    @Autowired
    ThreadCountProcessorUtils threadCountProcessorUtils;

    @Bean(ThreadConfiguration.SCHEDULE_TRIGGER_BEAN)
    public ThreadPoolExecutor getThreadPoolExecutor() {
        return new ThreadPoolExecutor(threadCountProcessorUtils.getActiveThreadCount(),
                threadCountProcessorUtils.getQeueThreadCount(),
                0L,
                TimeUnit.MILLISECONDS,
                getBlockingQueue());
    }

    @Bean
    public BlockingQueue getBlockingQueue() {
        return new LinkedBlockingQueue<Runnable>();
    }

}
