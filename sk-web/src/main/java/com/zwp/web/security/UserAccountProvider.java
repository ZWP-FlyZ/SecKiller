package com.zwp.web.security;

import com.zwp.comm.vo.LogInAccountVo;
import com.zwp.service.LogInService.LogInCacheService;
import com.zwp.service.LogInService.LogInDbService;
import com.zwp.web.vo.UserAccount;
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

    @Autowired
    private LogInCacheService lcs;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

//        throw new UsernameNotFoundException("username:"+username+" is not exit");
        LogInAccountVo vo =
                lcs.getUserAccountByUsername(username);// 缓存获取登录账号
        if(vo==null){
            vo = lds.getUserAccountByUsername(username);
            if(vo!=null)
                lcs.setUserAccountCache(vo);
        }
        if(vo==null)
            throw new UsernameNotFoundException("username:"+username+" is not exits!");

        return UserAccount.from(vo);
    }
}
