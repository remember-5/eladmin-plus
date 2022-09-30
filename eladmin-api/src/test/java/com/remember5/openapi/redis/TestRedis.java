package com.remember5.openapi.redis;

import cn.hutool.core.date.DateUtil;
import com.remember5.openapi.OpenApiApplication;
import com.remember5.core.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.concurrent.CountDownLatch;

/**
 * @author wangjiahao
 * @date 2022/5/5 15:45
 */
@Slf4j
@SpringBootTest(classes = OpenApiApplication.class)
public class TestRedis {

    @Resource
    private RedisUtils redisUtils;
    @Resource
    private RedissonClient redissonClient;

    @Test
    void test(){
        // 测试写入
        String key = "MY:KEY";
        redisUtils.set(key, 10);
        String val = redisUtils.get(key).toString();
        System.err.println(val);

        // 测试
        long increment = redisUtils.increment(key, 14);
        System.err.println(increment);

        long decrement = redisUtils.decrement(key, 3);
        System.err.println(decrement);
    }



    @Test
    void testRedisUtilsSet() throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            String key = "my"+i;
            redisUtils.set(key,i);
            System.err.println(redissonClient.getBucket(key).get());
        }

    }

    @Test
    void testRedissonSet() throws InterruptedException {
        RBucket<String> nameBucket = redissonClient.getBucket("name");
        nameBucket.set("wangjiahao");
        System.err.println(nameBucket.getCodec());
        System.err.println(redissonClient.getBucket("name").get());
        new CountDownLatch(1).await();
    }

    @Test
    public void lockTest() throws InterruptedException {
        String key = "ORDER1";
        redisUtils.set(key, 2);
        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                final RLock lock = redissonClient.getLock("lock");
                try {
                    lock.lock();
                    long decrement = redisUtils.decrement(key, 1);
                    if (decrement >= 0) {
                        log.info("{} 抢到了，当前剩下 {}", Thread.currentThread().getName(), decrement);
                    } else {
                        log.warn("{} 抱歉 没抢到", Thread.currentThread().getName());
                    }
                    Thread.sleep(3000L);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
                System.out.println(DateUtil.now());

            }
            ).start();
        }
        new CountDownLatch(1).await();
    }
}
