package com.zwp.repo;

import com.zwp.repo.datasourceconfig.DynamicDatasourceAspect;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;

/**
 * @program: seckiller
 * @description: 多数据源测试
 * @author: zwp-flyz
 * @create: 2019-06-23 12:47
 * @version: v1.0
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class MutDataSourceTest {

    @Autowired
    DataSource datasource;

    @Autowired
    DataSource readDatasource;

    @Autowired
    DataSource writeDatasource;

    @Autowired
    DynamicDatasourceAspect aspect;

    @Autowired
    TestService ts;

    @Test
    public void doService(){
        ts.doService();
        ts.doService2();
    }


}
