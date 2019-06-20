package com.zwp.web.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.data.redis.config.annotation.web.http.RedisHttpSessionConfiguration;


@Configuration
@EnableRedisHttpSession()
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
    public LettuceConnectionFactory fact(){
        RedisStandaloneConfiguration cfg =
                new RedisStandaloneConfiguration();
        cfg.setHostName(host);
        cfg.setPort(port);
        cfg.setPassword(RedisPassword.of(password));
        return new LettuceConnectionFactory(cfg);
    }

}
