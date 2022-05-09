package com.remember5.openapi.user;

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

    @Test
    void test(){
        System.err.println(apiUserRepository.countByPhone("17600277246"));
    }


}
