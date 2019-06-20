package com.zwp.web.login;

import com.zwp.comm.resulttype.ResponseResult;
import com.zwp.comm.resulttype.ResultStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * @program: seckiller
 * @description: 登录相关控制器
 * @author: zwp-flyz
 * @create: 2019-06-20 15:02
 * @version: v1.0
 **/
@RestController
public class LogInController {
    private final static Logger logger = LoggerFactory.getLogger(LogInController.class);

    @PostMapping("/login_succ")
    @ResponseBody
    public ResponseResult<String> loginSuccess(HttpServletRequest request){
        ResponseResult<String> loginres = ResponseResult.build();

        Authentication aut = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user =  (UserDetails)aut.getPrincipal();
        logger.info("username:{},password:{} login",user.getUsername(),user.getPassword());
        logger.info(request.getSession().getMaxInactiveInterval()+"");
        return loginres;
    }


    @PostMapping("/login_fail")
    @ResponseBody
    public ResponseResult<String> loginFailur(HttpServletRequest request){
        ResponseResult<String> loginres = ResponseResult.build(ResultStatus.FAILD);
        return loginres;
    }


}
