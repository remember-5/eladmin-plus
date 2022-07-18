package com.remember5.push.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * push的配置文件
 *
 * @author wangjiahao
 * @date 2022/5/27 19:26
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "getui")
public class PushProperties {

    private String maxConnections;

    private String appid;

    private String appkey;

    private String masterSecret;

    private String domain;


}
