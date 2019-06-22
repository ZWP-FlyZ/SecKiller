package com.zwp.repo.mybatis;

import com.zwp.repo.datasourceconfig.DynamicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * @program: seckiller
 * @description: MyBatis的配置文件
 * @author: zwp-flyz
 * @create: 2019-06-22 18:32
 * @version: v1.0
 **/
@Configuration
@EnableTransactionManagement
@MapperScan("com.zwp.repo.mybatis.mappers")
public class MyBatisConfig {


    /**
     * 注册SqlSessionFactory，
     * 因为停用了DatasourceAutoConfiguration，
     * 因此必须重新注册会话工厂
     * @return
     */
    @Bean
    @ConfigurationProperties("mybatis")
    public SqlSessionFactoryBean sqlSessionFactory(DynamicDataSource dataSource){
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        return factoryBean;
    }


}
