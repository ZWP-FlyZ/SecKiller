package com.zwp.service.login;

import com.zwp.comm.utils.UserIdUtils;
import com.zwp.comm.vo.UserAccountVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    /**
     * 登录从数据库中获取数据，若获取用户
     * @param username
     * @return
     */

    public UserAccountVo getUserAccountByUsername(String username){
        Assert.notNull(username,"username is null!");
        UserAccountVo vo = new UserAccountVo();
        vo.setUsername(username);
        vo.setUserId(UserIdUtils.getUserId(username));
        vo.setPassword("password");
        vo.setRole("ROLE_USER");
        vo.setStatus(0);
        return vo;
    }

    /**
     * 登录成功后更新数据
     * @param username
     * @return
     */
    @Transactional
    public UserAccountVo updateLogInStatus(String username){
        Assert.notNull(username,"username is null!");

        return getUserAccountByUsername(username);
    }


}
