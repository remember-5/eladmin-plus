package com.remember5.openapi.user;

import com.remember5.biz.appversion.repository.AppVersionRepository;
import com.remember5.biz.appversion.service.dto.AppVersionDto;
import com.remember5.openapi.OpenApiApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@Slf4j
@SpringBootTest(classes = OpenApiApplication.class)
public class UserTest {

    @Resource
    AppVersionRepository apiVersionRepository;


    @Test
    void query() {
        final AppVersionDto a3306 = apiVersionRepository.findByAppIdAndVersionAndBuildAndArchivedAndPublishedAndIsDeleted("A3306", "0.0.1", 100L, 0, 1, 0);
        System.err.println(a3306);
    }


}
