package com.zwp.repo.redis;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @program: seckiller
 * @description: Redis服务器相关配置
 * @author: zwp-flyz
 * @create: 2019-06-26 15:09
 * @version: v1.0
 **/
@Configuration
public class RedisConfig {


    @Bean("readerProperties")
    @ConfigurationProperties("sk.redis.reader")
    public RedisConfigProperty readerProperties(){
        return new RedisConfigProperty();
    }

    @Bean("writerProperties")
    @ConfigurationProperties("sk.redis.writer")
    public RedisConfigProperty writerProperties(){
        return new RedisConfigProperty();
    }

    /**
     * 读数据源
     * @return
     */
    @Bean("redisReaderConnFactory")
    public RedisConnectionFactory redisReaderConnFactory(@Qualifier("readerProperties")
                                                         RedisConfigProperty property){
        return factory(property);
    }

    /**
     * 写数据源
     * @return
     */
    @Bean("redisWriterConnFactory")
    public RedisConnectionFactory redisWriterConnFactory(@Qualifier("writerProperties")
                                                                     RedisConfigProperty property){
        return factory(property);
    }

    /**
     * 读数据模板
     * @param factory
     * @return
     */
    @Bean("redisReaderTemp")
    public StringRedisTemplate redisReaderTemp(
            @Qualifier("redisReaderConnFactory")RedisConnectionFactory factory){
        return new StringRedisTemplate(factory);
    }

    /**
     * 写模板
     * @param factory
     * @return
     */
    @Bean("redisWriterTemp")
    public StringRedisTemplate redisWriterTemp(
            @Qualifier("redisWriterConnFactory")RedisConnectionFactory factory){
        StringRedisTemplate template = new StringRedisTemplate(factory);
        template.setEnableTransactionSupport(true);//开启事务功能
        return template;
    }

    private RedisConnectionFactory factory(RedisConfigProperty property){
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName(property.getHost());
        config.setPassword(property.getPassword());
        config.setPort(property.getPort());

        return  new LettuceConnectionFactory(config);

    }

}
