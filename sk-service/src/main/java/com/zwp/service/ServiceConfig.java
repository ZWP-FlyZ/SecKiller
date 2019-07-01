package com.zwp.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: seckiller
 * @description: 所有服务共用的配置
 * @author: zwp-flyz
 * @create: 2019-06-23 13:57
 * @version: v1.0
 **/
@Configuration
@EnableTransactionManagement
public class ServiceConfig {
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
