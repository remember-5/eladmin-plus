package me.zhengjie.entity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author wangjiahao
 * @date 2021/12/3
 */
@Data
@Component
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
