package com.remember5.openapi.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 配置跨域
 *
 * @author wangjiahao
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * 允许跨域配置
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        log.info("跨域已设置");
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
//                .allowedOrigins("*")  // 在升级springboot2.5.8的时候要注释掉，使用上面这个
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }

}
