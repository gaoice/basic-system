package com.gaoice.system.common.async;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * Async 方法线程池配置，配置最大值避免 OOM
 */
@Configuration
public class AsyncThreadPool {

    /**
     * 不要使用 Executors 创建的线程池，会出错
     *
     * @return
     */
    @Bean
    @Primary
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setThreadNamePrefix("com.gaoice.system.common.async.AsyncThreadPool-");
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(100);
        return executor;
    }

}
