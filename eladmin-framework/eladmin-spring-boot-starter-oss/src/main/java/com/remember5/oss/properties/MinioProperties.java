package com.remember5.oss.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

/**
 * @author wangjiahao
 */
@Data
@ConfigurationProperties(prefix = "minio")
public class MinioProperties implements Serializable {
    private static final long serialVersionUID = 5512969315614829142L;
    private Boolean enabled;
    private String host;
    private String bucket;
    private String accessKey;
    private String secretKey;
    private String domain;
    private Integer fileExpires;
    private String bucketPrefix;
}
