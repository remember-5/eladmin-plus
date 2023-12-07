package com.remember5.oss.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;



/**
 * @author wangjiahao
 */
@Data
@ConfigurationProperties(prefix = MinioProperties.PREFIX)
public class MinioProperties {

    public static final String PREFIX = "minio";

    private Boolean enabled;
    private String host;
    private String bucket;
    private String accessKey;
    private String secretKey;
    private String domain;
    private Integer fileExpires;
    private String bucketPrefix;
}
