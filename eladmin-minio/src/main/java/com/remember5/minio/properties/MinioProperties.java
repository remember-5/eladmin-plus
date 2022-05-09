package com.remember5.minio.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;

/**
 * @author wangjiahao
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "minio")
public class MinioProperties implements Serializable {
    private static final long serialVersionUID = 5512969315614829142L;
    private String host;
    private String bucket;
    private String accessKey;
    private String secretKey;
}
