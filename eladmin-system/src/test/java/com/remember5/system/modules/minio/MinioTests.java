package com.remember5.system.modules.minio;

import com.remember5.core.result.R;
import com.remember5.system.SystemApplicationTests;
import com.remember5.system.modules.minio.service.MinioService;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author wangjiahao
 * @date 2022/12/9 12:20
 */
@Slf4j
class MinioTests extends SystemApplicationTests {

    @Autowired
    MinioService minIOService;

    @Test
    public void testUpload() throws IOException {
        File file = new File("");
        MultipartFile cMultiFile = new MockMultipartFile("file", file.getName(), ContentType.APPLICATION_OCTET_STREAM.toString(), new FileInputStream(file));
        R restResult = minIOService.uploadFile(cMultiFile);
        System.err.println(restResult);
    }
}
