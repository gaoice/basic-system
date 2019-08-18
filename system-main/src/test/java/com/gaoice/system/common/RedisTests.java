package com.gaoice.system.common;

import com.gaoice.system.SystemApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SystemApplication.class)
public class RedisTests {

    /**
     * redisTemplate 和 stringRedisTemplate 数据不通
     */
    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Test
    @SuppressWarnings("unchecked")
    public void redis() {
        stringRedisTemplate.opsForValue().set("testKey", "testValue");
        Object o = stringRedisTemplate.opsForValue().get("testKey");
        System.out.println(o);
    }

}
