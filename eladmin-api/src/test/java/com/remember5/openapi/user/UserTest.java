package com.remember5.openapi.user;

import com.remember5.biz.appversion.repository.AppVersionRepository;
import com.remember5.biz.appversion.service.dto.AppVersionDto;
import com.remember5.openapi.OpenApiApplication;
import com.remember5.openapi.modules.apiuser.repository.ApiUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@Slf4j
@SpringBootTest(classes = OpenApiApplication.class)
public class UserTest {

    @Resource
    ApiUserRepository apiUserRepository;

    AppVersionRepository apiVersionRepository;

    @Test
    void test(){
//        System.err.println(apiUserRepository.countByPhone("17600277246"));
    }


    @Test
    void query() {
        final AppVersionDto a3306 = apiVersionRepository.findByAppIdAndVersionAndBuildAndArchivedAndPublishedAndIsDeleted("A3306", "0.0.1", 100L, 0, 1, 0);
    }


}
