package com.smart.sso.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import java.util.concurrent.*;

/**
 * @Author xhx
 * @Date 2022/1/26 16:34
 */
@Component
@EnableAsync
public class ThreadPoolConfig implements AsyncConfigurer {
    final int CORE_SIZE = 2;
    final int MAX_SIZE = 3;
    final int WAIT_TIME = 5;
    @Override
    @Bean(name = "threadPoolTaskExecutor")
    public ThreadPoolTaskExecutor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(CORE_SIZE);
        executor.setMaxPoolSize(MAX_SIZE);
        executor.setKeepAliveSeconds(WAIT_TIME);
        executor.setThreadNamePrefix("image-Executor-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardOldestPolicy());
        return executor;
    }
}
