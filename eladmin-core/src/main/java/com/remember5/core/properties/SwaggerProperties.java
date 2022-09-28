package com.remember5.core.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author wangjiahao
 * @date 2021/12/3
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "swagger")
public class SwaggerProperties {

    private Boolean enabled;
    private String title;
    private String description;
    private String author;
    private String email;
    private String blog;
    private String controller;
    private String serviceUrl;
    private String version;
    private String host;
    private String groupName;
    private String basePackage;
}
