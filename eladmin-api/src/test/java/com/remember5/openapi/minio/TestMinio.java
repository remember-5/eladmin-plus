package com.remember5.openapi.minio;

import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import com.remember5.oss.entity.MinioResponse;
import com.remember5.oss.properties.MinioProperties;
import com.remember5.oss.utils.MinioUtils;
import com.remember5.openapi.OpenApiApplication;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.ContentType;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

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
    @Disabled
    void testUpload() throws IOException, InterruptedException {
        File file = new File("/Users/wangjiahao/Downloads/2.png");
        MultipartFile cMultiFile = new MockMultipartFile("file", file.getName(), ContentType.APPLICATION_OCTET_STREAM.toString(), new FileInputStream(file));
        minioUtils.upload(cMultiFile, minioProperties.getBucket());
    }

    @Test
    @Disabled
    void testUploadByUrl() throws InterruptedException {
//        String url = "http://42.193.105.146:9000/nt1/2022-05-17/2dc9f29a-2e66-4abb-a7f9-f2dbbd2d2ab5.png";
        String url = "https://skyline.github.com/_nuxt/assets/sound/music-807dfe09ce23793891674eb022b38c1b.mp3";
        MinioResponse upload = minioUtils.upload(url, minioProperties.getBucket());
        System.err.println(JSON.toJSONString(upload, JSONWriter.Feature.FieldBased, JSONWriter.Feature.PrettyFormat));
//        System.err.println(JSON.toJSONString(upload));
        Assertions.assertTrue(upload.stats());
    }

    @Test
    void testFileMime() {
        List<String> list = Lists.list("aa.mp4",
                "bb.png",
                "cc.mp3",
                "a.pdf",
                "a.xlsx",
                "a.doc"
        );
        for (String s : list) {
            System.err.println(FileUtil.getMimeType(s));
        }
    }
}
