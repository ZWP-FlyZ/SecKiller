package security;

import com.zwp.comm.resulttype.ResponseResult;
import com.zwp.comm.resulttype.ResultStatus;
import com.zwp.comm.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 * @program: seckiller
 * @description: 访问未授权资源处理
 * @author: zwp-flyz
 * @create: 2019-06-20 19:31
 * @version: v1.0
 **/
public class RequestAccessDeniedHandler implements AccessDeniedHandler {
    private static final Logger logger = LoggerFactory.getLogger(RequestAccessDeniedHandler.class);
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {

        String username = request.getParameter("username");
        String accPath = request.getRequestURI();
        logger.info("access denied  -> user:{} path:{} ",username,accPath);
        ResponseResult<String> res = ResponseResult.build(ResultStatus.ACCESS_DENIED);
        out(response,res);// 回报权限信息
    }
    private void out(HttpServletResponse response,Object body) throws IOException{
        try(OutputStream os = response.getOutputStream()){
            Writer wrt = new OutputStreamWriter(os,"UTF-8");
            wrt.write(JsonUtils.toJson(body));
            wrt.flush();
        }
    }
}
