package com.zwp.repo.datasourceconfig;

import com.alibaba.druid.pool.DruidDataSource;
import com.zwp.comm.utils.PassEncUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.annotation.Resource;
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
                                        DataSource writeDatasource,
                                        DataSource accountDatasource
                                        ){
        DynamicDataSource dd = new DynamicDataSource();
        dd.setDefaultTargetDataSource(readDatasource);// 默认读数据源
        Map<Object,Object> map = new HashMap<>();
        map.put(DataSourceType.READ_DATASOURCE, readDatasource);
        map.put(DataSourceType.WRITE_DATASOURCE, writeDatasource);
        map.put(DataSourceType.ACCOUNT_DATASOURCE,accountDatasource);
        dd.setTargetDataSources(map);
        return dd;
    }


    /**
     * 事务系统
     *
     * @return
     */
//    @Bean(TxManagers.READ_TX)
//    public DataSourceTransactionManager readTxManager(DruidDataSource readDatasource) {
//        return new DataSourceTransactionManager(readDatasource);
//    }
//
//    @Bean(TxManagers.WRITE_TX)
//    public DataSourceTransactionManager writeTxManager(DruidDataSource writeDatasource) {
//        return new DataSourceTransactionManager(writeDatasource);
//    }
//
//    @Bean(TxManagers.ACCOUNT_TX)
//    public DataSourceTransactionManager accountTxManager(DruidDataSource accountDatasource) {
//        return new DataSourceTransactionManager(accountDatasource);
//    }

    @Bean
    public DataSourceTransactionManager transactionManager(@Qualifier("datasource") DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
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


    /**
     * 独立处理用户登录和注册等账号信息的数据源
     * @return
     */
    @Bean(value = "accountDatasource",initMethod = "init")
    @ConfigurationProperties("sk.ds.account")
    public DruidDataSource accountDatasource(){
        return new DruidDataSource();
    }


}
