package com.zwp.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: seckiller
 * @description: Mvc相关的配置
 * @author: zwp-flyz
 * @create: 2019-06-24 22:41
 * @version: v1.0
 **/

@Configuration
@EnableTransactionManagement
public class MvcConfig {


    /**
     * 本地缓存，存储某一货物是否已经秒杀结束，
     * 注意需要定时清理该缓存
     * @return
     */
    @Bean("overFlag")
    public Map<Long,Boolean> overFlag(){
        return new ConcurrentHashMap<>();
    }

}
