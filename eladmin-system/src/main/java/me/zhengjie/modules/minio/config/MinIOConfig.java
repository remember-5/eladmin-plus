package me.zhengjie.modules.minio.config;

import io.minio.MinioClient;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "spring.minio")
public class MinIOConfig {

    private String host;
    private Integer port;
    private String bucket;
    private String accessKey;
    private String secretKey;

    @Bean(name = "minioClient")
    public MinioClient minioClient() throws InvalidPortException, InvalidEndpointException {
        return new MinioClient(this.host, this.port, this.accessKey, this.secretKey);
    }

}
