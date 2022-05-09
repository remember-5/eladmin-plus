package com.remember5.minio.config;

import cn.hutool.core.util.StrUtil;
import com.remember5.minio.properties.MinioProperties;
import io.minio.MinioClient;
import lombok.RequiredArgsConstructor;

/**
 * @author wangjiahao
 * @date 2022/5/8 14:09
 */
//@Configuration
@RequiredArgsConstructor
//@ConditionalOnExpression(value = "T(org.apache.commons.lang3.StringUtils).isNotEmpty('${minio.host}')")
public class MinioConfig {

    private final MinioProperties minioProperties;

//    @Bean
    public MinioClient getMinioClient() {
        boolean flag = StrUtil.isNotBlank(minioProperties.getHost());
        return flag ? MinioClient.builder()
                .endpoint(minioProperties.getHost())
                .credentials(minioProperties.getAccessKey(), minioProperties.getSecretKey())
                .build() : null;
    }

}
