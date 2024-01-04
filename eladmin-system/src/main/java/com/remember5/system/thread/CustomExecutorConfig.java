package com.remember5.system.thread;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 创建自定义的线程池
 *
 * @author wangjiahao
 * @date 2023/01/08
 * @since 2.3.0
 **/
@Configuration
public class CustomExecutorConfig {

    /**
     * 自定义线程池，用法 @Async
     *
     * @return Executor
     */
    @Bean
    @Primary
    public Executor elAsync() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //核心线程池大小
        executor.setCorePoolSize(AsyncTaskProperties.corePoolSize);
        //最大线程数
        executor.setMaxPoolSize(AsyncTaskProperties.maxPoolSize);
        //队列容量
        executor.setQueueCapacity(AsyncTaskProperties.queueCapacity);
        //活跃时间
        executor.setKeepAliveSeconds(AsyncTaskProperties.keepAliveSeconds);
        //线程名字前缀
        executor.setThreadNamePrefix("el-async-");
        // setRejectedExecutionHandler：当pool已经达到max size的时候，如何处理新任务
        // CallerRunsPolicy：不在新线程中执行任务，而是由调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }
}
