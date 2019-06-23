package com.zwp.repo.datasourceconfig;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: seckiller
 * @description: Druid数据源配置,多数据源，供数据库读写分离，
 * 注意需要关闭DataSourceAutoConfiguration
 * @author: zwp-flyz
 * @create: 2019-06-22 09:09
 * @version: v1.0
 **/
@Configuration
@EnableAspectJAutoProxy
public class DataSourceConfig {


    /**
     * 动态数据源注入
     * @param readDatasource  读数据源注入
     * @param writeDatasource 写数据源注入
     * @return
     */
    @Bean("datasource")
    public DynamicDataSource datasource(DataSource readDatasource,
                                 DataSource writeDatasource){
        DynamicDataSource dd = new DynamicDataSource();
        dd.setDefaultTargetDataSource(readDatasource);// 默认读数据源
        Map<Object,Object> map = new HashMap<>();
        map.put(DataSourceType.READ_DATASOURCE, readDatasource);
        map.put(DataSourceType.WRITE_DATASOURCE, writeDatasource);
        dd.setTargetDataSources(map);
        return dd;
    }


    /**
     * 事务系统
     * @param datasource
     * @return
     */
    @Bean("txManager")
    public DataSourceTransactionManager getTxManager(DataSource datasource) {
        return new DataSourceTransactionManager(datasource);
    }



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
