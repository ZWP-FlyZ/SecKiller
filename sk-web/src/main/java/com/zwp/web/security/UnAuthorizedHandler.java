package com.zwp.web.security;

import com.zwp.comm.resulttype.ResponseResult;
import com.zwp.comm.resulttype.ResultStatus;
import com.zwp.comm.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 * @program: seckiller
 * @description: 用户未登录时处理
 * @author: zwp-flyz
 * @create: 2019-06-20 18:37
 * @version: v1.0
 **/
public class UnAuthorizedHandler implements AuthenticationEntryPoint {
    private static final Logger logger = LoggerFactory.getLogger(UnAuthorizedHandler.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        String username = request.getParameter("username");
        logger.debug("unauthorized -> user:{}",username);
        ResponseResult<String> res = ResponseResult.build(ResultStatus.UNAUTHORIZED);
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
