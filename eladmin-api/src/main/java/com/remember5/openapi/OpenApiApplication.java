package com.remember5.openapi;

import com.remember5.security.utils.SpringContextHolder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author fly
 * @date 2021/8/20 17:02
 */

@SpringBootApplication
@EnableTransactionManagement // 开启事物
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableAspectJAutoProxy(exposeProxy = true) // Aop exposeProxy=强制采用cglib代理 proxyTargetClass=暴露cglib代理的目标对象
@ComponentScan(basePackages = {
        "cn.hutool.extra.spring",
        "com.remember5.*",
})
@EnableJpaRepositories(basePackages = {
        "com.remember5.*",
})
@EntityScan(basePackages = {
        "com.remember5.*",
})
public class OpenApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(OpenApiApplication.class, args);
    }

    @Bean
    public SpringContextHolder springContextHolder() {
        return new SpringContextHolder();
    }
}
