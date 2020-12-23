package com.tedbj.grace.file.config;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import lombok.extern.slf4j.Slf4j;

/**
 * 线程池设置
 */
@Configuration
@EnableAsync
@Slf4j
@RefreshScope
public class ExecutorConfig {

    /**
     * 核心线程数
     */
    @Value("${async.executor.thread.core_pool_size:5}")
    private int corePoolSize;

    /**
     * 最大线程数
     */
    @Value("${async.executor.thread.max_pool_size:5}")
    private int maxPoolSize;

    /**
     * 队列大小
     */
    @Value("${async.executor.thread.queue_capacity:99999}")
    private int queueCapacity;

    /**
     * 线程池中的线程的名称前缀
     */
    @Value("${async.executor.thread.name.prefix:async-service-file-}")
    private String namePrefix;

    /**
     * 异步服务
     *
     * @return Executor
     */
    @Bean(name = "asyncServiceExecutor")
    public Executor asyncServiceExecutor() {
        log.info("start asyncServiceExecutor");
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //配置核心线程数
        executor.setCorePoolSize(corePoolSize);
        //配置最大线程数
        executor.setMaxPoolSize(maxPoolSize);
        //配置队列大小
        executor.setQueueCapacity(queueCapacity);
        //配置线程池中的线程的名称前缀
        executor.setThreadNamePrefix(namePrefix);
        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //执行初始化
        executor.initialize();
        return executor;
    }

}
