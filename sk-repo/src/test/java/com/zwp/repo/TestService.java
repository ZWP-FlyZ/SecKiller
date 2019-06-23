package com.zwp.repo;

import com.zwp.comm.vo.UserAccountVo;
import com.zwp.repo.datasourceconfig.DataSourceType;
import com.zwp.repo.datasourceconfig.UseDatasource;
import com.zwp.repo.mybatis.mappers.LoginMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    LoginMapper mapper;

    @UseDatasource(DataSourceType.READ_DATASOURCE)
    public void doService(){
        System.err.println("do service read");
        UserAccountVo vo = mapper.getUserAccountVoByUserId((long)1234);
        System.err.println(vo);

    }

    @UseDatasource(DataSourceType.WRITE_DATASOURCE)
    public void doService2(){
        System.err.println("do service write");
        UserAccountVo vo =mapper.getUserAccountVoByUserId((long)1234);
        System.err.println(vo);
    }

}
