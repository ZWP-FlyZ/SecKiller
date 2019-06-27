package com.zwp.web.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.session.SessionAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import java.net.UnknownHostException;


@EnableRedisHttpSession
public class SpringSessionConfig {


    @Value("${session.redis.host}")
    String host;
    @Value("${session.redis.port}")
    int port;
    @Value("${session.redis.password}")
    String  password;


    /**
     *  配置redis连接工厂，连接属性来自配置文件
     *  连接工厂的详细设置见redis-data的相关文档
     * @return
     */
    @Bean("lettuceConnectionFactory")
    @Primary
    public LettuceConnectionFactory lettuceConnectionFactory(){
        RedisStandaloneConfiguration cfg =
                new RedisStandaloneConfiguration();
        cfg.setHostName(host);
        cfg.setPort(port);
        cfg.setPassword(RedisPassword.of(password));
        return new LettuceConnectionFactory(cfg);
    }

    /**
     * 关闭RedisAutoConfiguration后需要配置redis
     *
     * @param redisConnectionFactory
     * @return
     */
    @Bean("redisTemplate")
    public RedisTemplate<Object, Object> redisTemplate(@Qualifier("lettuceConnectionFactory")
                                                                   RedisConnectionFactory redisConnectionFactory)
             {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

    @Bean("stringRedisTemplate")
    public StringRedisTemplate stringRedisTemplate(@Qualifier("lettuceConnectionFactory")
                                                               RedisConnectionFactory redisConnectionFactory)
            {

        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }


}
