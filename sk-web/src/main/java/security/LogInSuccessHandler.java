package security;

import com.google.gson.Gson;
import com.zwp.comm.resulttype.ResponseResult;
import com.zwp.comm.resulttype.ResultStatus;
import com.zwp.comm.utils.JsonUtils;
import com.zwp.web.vo.UserAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 * @program: seckiller
 * @description: Spring Security 认证成功时处理函数
 * @author: zwp-flyz
 * @create: 2019-06-20 15:50
 * @version: v1.0
 **/
public class LogInSuccessHandler implements AuthenticationSuccessHandler {
    private static final Logger logger = LoggerFactory.getLogger(LogInSuccessHandler.class);
    private static final Gson gson = new Gson();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {
        UserAccount user = (UserAccount)authentication.getPrincipal();

        logger.info("login -> user:{}",user.getUsername());
        ResponseResult<String> res = ResponseResult.build(ResultStatus.SUCCESS);
        out(response,res);// 回报登录成功信息
    }

    private void out(HttpServletResponse response,Object body) throws IOException{
        try(OutputStream os = response.getOutputStream()){
            Writer wrt = new OutputStreamWriter(os,"UTF-8");
            wrt.write(JsonUtils.toJson(body));
            wrt.flush();
        }
    }


}
