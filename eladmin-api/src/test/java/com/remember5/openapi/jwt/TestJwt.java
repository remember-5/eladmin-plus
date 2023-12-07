package com.remember5.openapi.jwt;

import com.alibaba.fastjson2.JSONObject;
import com.remember5.openapi.OpenApiApplication;
import com.remember5.security.utils.TokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author wangjiahao
 * @date 2022/5/6 14:06
 */
@Slf4j
@SpringBootTest(classes = OpenApiApplication.class)
public class TestJwt {
    @Autowired
    TokenProvider tokenProvider;

    @Test
    void testGenerateToken() {
        final String token = tokenProvider.createAccessToken(1234L, "wangjiahao");

        System.err.println("生成token: {}" + token);
        System.err.println("生成token#getPayload: {}" + tokenProvider.getClaims(token));
        System.err.println("生成token#verify: {}" + tokenProvider.verifyToken(token));
        System.err.println("生成token#getExpires: {}" + tokenProvider.getExpires(token));


        final String errorStr = token + "1";
        System.err.println("错误token: {}" + errorStr);
        System.err.println("错误token#getPayload: {}" + tokenProvider.getClaims(errorStr));
        System.err.println("错误token#verify: {}" + tokenProvider.verifyToken(errorStr));
    }

    public static void main(String[] args) {
        System.err.println();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("time,", Math.toIntExact(System.currentTimeMillis() / 1000));
        System.err.println(jsonObject.toJSONString());
    }

}
