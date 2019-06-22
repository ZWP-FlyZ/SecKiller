package com.zwp.repo.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: seckiller
 * @description: Druid数据源配置,多数据源，供数据库读写分离
 * @author: zwp-flyz
 * @create: 2019-06-22 09:09
 * @version: v1.0
 **/
@Configuration
public class DruidConfig {

    /**
     * 读数据源
     * @return
     */
    @Bean(value = "readDatasource",initMethod = "init")
    @ConfigurationProperties("sk.ds.reader")
    public DruidDataSource readDatasource(){
        return new DruidDataSource();
    }

    /**
     * 写数据源
     * @return
     */
    @Bean(value = "writeDatasource",initMethod = "init")
    @ConfigurationProperties("sk.ds.writer")
    public DruidDataSource writeDatasource(){
        return new DruidDataSource();
    }

}
