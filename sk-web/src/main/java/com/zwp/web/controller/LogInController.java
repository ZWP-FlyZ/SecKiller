package com.zwp.web.controller;

import com.zwp.comm.resulttype.ResponseResult;
import com.zwp.comm.resulttype.ResultStatus;
import com.zwp.comm.vo.UserAccountVo;
import com.zwp.service.login.LogInDbService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @program: seckiller
 * @description: 登录相关控制器,以登录相关逻辑移动到security
 * @author: zwp-flyz
 * @create: 2019-06-20 15:02
 * @version: v1.0
 **/
@RestController
public class LogInController {
    private final static Logger logger = LoggerFactory.getLogger(LogInController.class);

    @Autowired
    LogInDbService lds;


    /**
     * 在通过密码验证后
     * @param request
     * @return
     */
    @PostMapping("/login")
    public ResponseResult<String> loginSuccess(HttpServletRequest request, HttpServletResponse response){
        ResponseResult<String> res = null;
        Authentication aut = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user =  (UserDetails)aut.getPrincipal();
        UserAccountVo vo = null;
        try{
            // 注意对于登录操作并不是很频繁的系统，
            // 缓存登录信息并不会带来很大提升，
            // 可能反而降低性能和资源浪费,
            // 并且存在缓存和数据库之间同步问题，
            // 因此可以采取限流+数据库更新的方式。
            lds.updateLogInStatus(user.getUsername());
            logger.info("login success-> username:{} ",user.getUsername());
            res = ResponseResult.build();
        }catch (Exception e){
            logger.error("unknown error -> username:{} ",user.getUsername());
            logger.error("update login status error. ",e);
            //清除本次登录信息，
            // 注意通过这种方式清除可能会有问题
            SecurityContextHolder.getContext().setAuthentication(null);
            res = ResponseResult.build(ResultStatus.EXCEPTION);
        }
        return res;
    }


}
