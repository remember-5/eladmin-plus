package com.remember5.oss.config;

import com.remember5.oss.properties.MinioProperties;
import com.remember5.oss.utils.MinioUtils;
import io.minio.MinioClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wangjiahao
 * @date 2022/5/8 14:09
 */
@Configuration
@EnableConfigurationProperties(MinioProperties.class)
@ConditionalOnProperty(prefix = "minio", name = "enabled", havingValue = "true", matchIfMissing = true)
// @ConditionalOnExpression(value = "T(org.apache.commons.lang3.StringUtils).isNotEmpty('${minio.host}')")
public class MinioConfiguration {

    /**
     * 创建Minio客户端连接
     */
    @Bean(name="minioClient")
    public MinioClient getMinioClient(MinioProperties minioProperties) {
        return MinioClient.builder()
                .endpoint(minioProperties.getHost())
                .credentials(minioProperties.getAccessKey(), minioProperties.getSecretKey())
                .build();
    }

    @Bean(name="minioUtils")
    public MinioUtils initMinioUtils(MinioClient minioClient, MinioProperties minioProperties) {
        return new MinioUtils(minioClient, minioProperties);
    }
}
