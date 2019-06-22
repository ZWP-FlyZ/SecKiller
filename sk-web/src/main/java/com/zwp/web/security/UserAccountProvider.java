package com.zwp.web.security;

import com.zwp.comm.vo.UserAccountVo;
import com.zwp.service.login.LogInCacheService;
import com.zwp.service.login.LogInDbService;
import com.zwp.web.vo.UserAccountDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @program: seckiller
 * @description: SpringSecurity中提供用户信息的服务
 * @author: zwp-flyz
 * @create: 2019-06-20 19:55
 * @version: v1.0
 **/
public class UserAccountProvider implements UserDetailsService {

    @Autowired
    private LogInDbService lds;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

//        throw new UsernameNotFoundException("username:"+username+" is not exit");

        // 在高并发登录情况下，该处需要访问数据库
        // 可能出现问题，因此可以限流保护系统
        UserAccountVo vo = lds.getUserAccountByUsername(username);
        if(vo==null)
            throw new UsernameNotFoundException("username:"+username+" is not exits!");

        return UserAccountDetails.from(vo);
    }
}
