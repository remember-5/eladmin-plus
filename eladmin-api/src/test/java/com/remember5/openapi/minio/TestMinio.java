package com.remember5.openapi.minio;

import com.remember5.minio.properties.MinioProperties;
import com.remember5.minio.server.MinioUtils;
import com.remember5.openapi.OpenApiApplication;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @author wangjiahao
 * @date 2022/5/6 16:54
 */
@Slf4j
@SpringBootTest(classes = OpenApiApplication.class)
public class TestMinio {

    @Resource
    MinioUtils minioUtils;

    @Resource
    MinioProperties minioProperties;

    @Test
    void testUpload() throws IOException, InterruptedException {
        File file = new File("/Users/wangjiahao/Downloads/2.png");
        MultipartFile cMultiFile = new MockMultipartFile("file", file.getName(), ContentType.APPLICATION_OCTET_STREAM.toString(), new FileInputStream(file));
        minioUtils.upload(cMultiFile, minioProperties.getBucket());
        new CountDownLatch(1).await();
    }
}
