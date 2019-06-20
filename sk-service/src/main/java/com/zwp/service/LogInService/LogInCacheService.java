package com.zwp.service.LogInService;

import com.zwp.comm.vo.LogInAccountVo;
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

    public LogInAccountVo getUserAccountByUsername(String username){
        if(username==null)  return null;
        return null;
    }

    public void setUserAccountCache(LogInAccountVo user){

    }

}
