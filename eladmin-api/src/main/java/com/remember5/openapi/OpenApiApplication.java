package com.remember5.openapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author fly
 * @date 2021/8/20 17:02
 */
@ComponentScan(basePackages = {
        "me.zhengjie.*",
        "cn.hutool.extra.spring",
        "com.remember5.*",
})
@EnableJpaRepositories(basePackages = {
        "me.zhengjie.*",
        "com.remember5.*",
})
@EntityScan(basePackages = {
        "me.zhengjie.*",
        "com.remember5.*",
})
@SpringBootApplication
public class OpenApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(OpenApiApplication.class, args);
    }
}
