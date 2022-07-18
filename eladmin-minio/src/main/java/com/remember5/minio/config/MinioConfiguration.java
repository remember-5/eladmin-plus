package com.remember5.minio.config;

import com.remember5.minio.properties.MinioProperties;
import io.minio.MinioClient;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;

/**
 * @author wangjiahao
 * @date 2022/5/8 14:09
 */
//@Configuration
@RequiredArgsConstructor
public class MinioConfiguration {

    private final MinioProperties minioProperties;

    @Bean
    @ConditionalOnExpression(value = "T(org.apache.commons.lang3.StringUtils).isNotEmpty('${minio.host}')")
    public MinioClient getMinioClient() {
        return MinioClient.builder()
                .endpoint(minioProperties.getHost())
                .credentials(minioProperties.getAccessKey(), minioProperties.getSecretKey())
                .build();
    }

}
