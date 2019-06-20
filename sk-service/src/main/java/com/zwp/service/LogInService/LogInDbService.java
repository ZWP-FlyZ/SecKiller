package com.zwp.service.LogInService;

import com.zwp.comm.vo.LogInAccountVo;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @program: seckiller
 * @description: 用户登录时与数据库关联的义务逻辑
 * @author: zwp-flyz
 * @create: 2019-06-20 20:02
 * @version: v1.0
 **/
@Service
public class LogInDbService {


    public LogInAccountVo getUserAccountByUsername(String username){
        Assert.notNull(username,"username is null!");
        LogInAccountVo vo = new LogInAccountVo();
        vo.setUsername(username);
        vo.setUserId(Long.MAX_VALUE/2+username.hashCode());
        vo.setPassword("password");
        vo.setRole("ROLE_USER");
        vo.setStatus(0);
        return vo;
    }


}
