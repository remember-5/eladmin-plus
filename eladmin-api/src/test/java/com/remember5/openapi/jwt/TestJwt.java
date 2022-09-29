package com.remember5.openapi.jwt;

import com.remember5.core.utils.TokenProvider;
import com.remember5.openapi.OpenApiApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author wangjiahao
 * @date 2022/5/6 14:06
 */
@Slf4j
@SpringBootTest(classes = OpenApiApplication.class)
public class TestJwt {
    @Resource
    TokenProvider tokenProvider;

    @Test
    void generateJwtToken() throws InterruptedException {
//        ApiUserDto apiUserDto = new ApiUserDto();
//        apiUserDto.setId(20L);
//        apiUserDto.setPhone("17600277246");
//        apiUserDto.setUsername("wangjiahao");
//        final String token = tokenProvider.generateToken(apiUserDto);
//
//        final JWT jwt = JWTUtil.parseToken(token);
//        System.err.println(token);
//        System.err.println(jwt.getHeader(JWTHeader.TYPE));
//        System.err.println(jwt.getPayload("sub"));
//        System.err.println(jwt.getPayload("id"));
//        System.err.println(jwt.getPayload("phone"));
//        System.err.println(jwt.getPayload("username"));
//        System.err.println(tokenProvider.isTokenExpired(token));
//        System.err.println(tokenProvider.isTokenExpired(token+"123"));
//        new CountDownLatch(1).await();
    }
}
