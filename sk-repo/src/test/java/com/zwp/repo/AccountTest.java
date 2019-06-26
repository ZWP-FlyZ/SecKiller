package com.zwp.repo;

import com.zwp.comm.utils.PassEncUtils;
import com.zwp.comm.utils.UserIdUtils;
import com.zwp.comm.vo.UserAccountVo;
import com.zwp.repo.datasourceconfig.DataSourceContextHolder;
import com.zwp.repo.datasourceconfig.DataSourceType;
import com.zwp.repo.mybatis.mappers.LoginMapper;
import com.zwp.repo.mybatis.mappers.LogonMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @program: seckiller
 * @description: 账号登录注册测试
 * @author: zwp-flyz
 * @create: 2019-06-23 12:45
 * @version: v1.0
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountTest {
    private final static Logger LOGGER = LoggerFactory.getLogger(AccountTest.class);

    @Autowired
    LoginMapper login;

    @Autowired
    LogonMapper logon;


    @Test
    public void loginTest() {
        DataSourceContextHolder.setDataSource(DataSourceType.WRITE_DATASOURCE);
        UserAccountVo user = login.getUserAccountVoByUserId((long)1234);
        if(user==null) return ;
        String ut = DateTimeFormatter.
                ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now());
        LOGGER.info(ut);
        user.setLastLogTime(ut);
        Integer res = login.updateLogInStatus(user);
        LOGGER.info(res.toString());
    }


    @Test
    public void logonTest(){
        DataSourceContextHolder.setDataSource(DataSourceType.WRITE_DATASOURCE);
        UserAccountVo user = new UserAccountVo();
        user.setUsername("zwp");
        user.setUserId(UserIdUtils.getUserId("zwp"));
        user.setPassword("password");
        user.setSalt(PassEncUtils.salt());
        user.setRole("ROLE_USER");
        String ut = DateTimeFormatter.
                ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now());
        user.setRegTime(ut);
        user.setStatus(0);
////        int v =logon.saveUserAccount(user);
//        LOGGER.info(v+"");
    }


}
