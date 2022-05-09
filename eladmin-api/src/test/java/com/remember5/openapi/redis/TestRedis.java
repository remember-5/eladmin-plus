package com.remember5.openapi.redis;

import com.remember5.openapi.OpenApiApplication;
import com.remember5.redis.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author wangjiahao
 * @date 2022/5/5 15:45
 */
@Slf4j
@SpringBootTest(classes = OpenApiApplication.class)
public class TestRedis {

    @Resource
    private RedisUtils redisUtils;

    @Test
    void test(){
        System.err.println(redisUtils.get("k123"));
    }
}
