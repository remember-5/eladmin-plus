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

    /**
     * 是否开启
     */
    private Boolean enabled;

    /**
     * 最大连接数
     */
    private String maxConnections;

    /**
     * 个推的appid
     */
    private String appid;

    /**
     * 个推的appkey
     */
    private String appkey;

    /**
     * /
     */
    private String masterSecret;

    /**
     * 个推地址
     */
    private String domain;


}
