package com.zwp.service.login;

import com.zwp.comm.vo.UserAccountVo;
import org.springframework.stereotype.Service;

/**
 * @program: seckiller
 * @description: 用户账号的缓存服务
 * @author: zwp-flyz
 * @create: 2019-06-20 20:07
 * @version: v1.0
 **/
@Service
public class LogInCacheService {

    public UserAccountVo getUserAccountByUsername(String username){
        if(username==null)  return null;
        return null;
    }

    /**
     *  将登录信息加入到缓存当中，
     *  注意登录信息的缓存必须能够支持定时内清除
     * @param user
     */
    public void setUserAccountCache(UserAccountVo user){

    }

}
