package com.zwp.web.security;

import com.zwp.comm.resulttype.ResponseResult;
import com.zwp.comm.resulttype.ResultStatus;
import com.zwp.comm.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 * @program: seckiller
 * @description: 登录失败处理
 * @author: zwp-flyz
 * @create: 2019-06-20 16:26
 * @version: v1.0
 **/
public class LogInFailHandler implements AuthenticationFailureHandler {
    private static final Logger logger = LoggerFactory.getLogger(LogInFailHandler.class);
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        String username = request.getParameter("username");
        logger.info("login failure -> user:{}",username);
        exception.printStackTrace();
        ResponseResult<String> res = ResponseResult.build(ResultStatus.PASS_ERROR);
        out(response,res);// 回报未登录信息
    }


    private void out(HttpServletResponse response,Object body) throws IOException{
        try(OutputStream os = response.getOutputStream()){
            Writer wrt = new OutputStreamWriter(os,"UTF-8");
            wrt.write(JsonUtils.toJson(body));
            wrt.flush();
        }
    }
}
