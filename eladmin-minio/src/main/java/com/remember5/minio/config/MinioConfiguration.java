package com.remember5.minio.config;

import com.remember5.minio.properties.MinioProperties;
import io.minio.MinioClient;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wangjiahao
 * @date 2022/5/8 14:09
 */
@Configuration
@RequiredArgsConstructor
public class MinioConfiguration {

    private final MinioProperties minioProperties;


    /**
     * 创建Minio客户端连接
     *
     * @return Minio实例
     */
    @Bean
    @ConditionalOnProperty(prefix = "minio", value = "enabled", matchIfMissing = true)
    // @ConditionalOnExpression(value = "T(org.apache.commons.lang3.StringUtils).isNotEmpty('${minio.host}')")
    public MinioClient getMinioClient() {
        return MinioClient.builder()
                .endpoint(minioProperties.getHost())
                .credentials(minioProperties.getAccessKey(), minioProperties.getSecretKey())
                .build();
    }

}
