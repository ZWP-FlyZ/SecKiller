package com.zwp.repo.datasourceconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @program: seckiller
 * @description: 测试服务
 * @author: zwp-flyz
 * @create: 2019-06-22 15:25
 * @version: v1.0
 **/
@Service
public class TestService {


    @Autowired
    DataSource datasource;

    @UseDatasource(DataSourceType.READ_DATASOURCE)
    public void doService(){
        System.err.println("do service read");
        try {
            Connection c = datasource.getConnection();
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @UseDatasource(DataSourceType.WRITE_DATASOURCE)
    public void doService2(){
        System.err.println("do service write");
        try {
            Connection c = datasource.getConnection();
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
