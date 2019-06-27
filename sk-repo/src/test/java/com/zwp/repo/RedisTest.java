package com.zwp.repo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @program: seckiller
 * @description: Redis多数据源测试
 * @author: zwp-flyz
 * @create: 2019-06-27 09:49
 * @version: v1.0
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {

    @Autowired
    @Qualifier("redisReaderConnFactory")
    RedisConnectionFactory readFactory;

    @Autowired
    @Qualifier("redisWriterConnFactory")
    RedisConnectionFactory writeFactory;

    @Autowired
    @Qualifier("redisReaderTemp")
    StringRedisTemplate readerTemp;

    @Autowired
    @Qualifier("redisWriterTemp")
    StringRedisTemplate writeTemp;



    @Test
    public void readwriteTest(){
        byte[] v1 = readFactory.getConnection().get("zwp".getBytes());
        System.err.println(new String(v1));

        byte[] v2 = writeFactory.getConnection().get("z".getBytes());
        System.err.println(new String(v2));

        System.err.println(readerTemp.opsForValue().get("zwp"));
        System.err.println(readerTemp.opsForValue().get("z"));
    }



}
