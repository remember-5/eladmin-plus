package com.remember5.openapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author fly
 * @date 2021/8/20 17:02
 */
@ComponentScan(basePackages = {
        "me.zhengjie.utils",
        "me.zhengjie.config",
        "me.zhengjie.properties",
        "cn.hutool.extra.spring",
        "com.remember5.openapi.*",
        "com.remember5.redis.*",
        "com.remember5.captcha.*",
        "com.remember5.minio.*",
})
@SpringBootApplication
public class OpenApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(OpenApiApplication.class, args);
    }
}
