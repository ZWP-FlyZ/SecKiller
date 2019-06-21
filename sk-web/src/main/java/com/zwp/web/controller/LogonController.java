package com.zwp.web.controller;

import com.zwp.comm.enums.Roles;
import com.zwp.comm.resulttype.ResponseResult;
import com.zwp.comm.resulttype.ResultStatus;
import com.zwp.comm.utils.MD5;
import com.zwp.comm.utils.PassEncUtils;
import com.zwp.comm.utils.UserIdUtils;
import com.zwp.comm.vo.UserAccountVo;
import com.zwp.service.register.LogOnService;
import com.zwp.web.vo.LogonVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StreamUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.time.Instant;

/**
 * @program: seckiller
 * @description: 处理账户注册
 * @author: zwp-flyz
 * @create: 2019-06-21 10:28
 * @version: v1.0
 **/
@RestController
@RequestMapping("/reg")
public class LogonController {

    private final static String VERIFY_CODE_KEY = "verifyCode";
    private final static Logger logger = LoggerFactory.getLogger(LogonController.class);

    @Autowired
    LogOnService los;



    /**
     * 由会话ID生成验证图片和验证码，并在session中存储结果
     * @return 报告返回结果
     */
    @GetMapping("/verify")
    public ResponseResult<String> getVerification(HttpSession sess, HttpServletResponse response)
            throws IOException {
        ResponseResult<String> res = ResponseResult.build();
        String vc = generateVerifyCode(sess.getId());
        logger.info("session [{}] generate verify code [{}]",sess.getId(),vc);
        sess.setAttribute(VERIFY_CODE_KEY,vc);//将验证码保存在session中
        BufferedImage img = los.getVerifyImage(vc);

        try(InputStream is = los.getVerifyImage();
            OutputStream os = response.getOutputStream();) {
//            ImageIO.write(img,"JPEG",os);//注意这里根据实际需求更改
            StreamUtils.copy(is,os);
        } catch(Exception e){
            logger.error("Exception at transport img",e);
            sess.removeAttribute(VERIFY_CODE_KEY);
            res = ResponseResult.build(ResultStatus.EXCEPTION);
        }
        return res;
    }


    @PostMapping("/do_register")
    public ResponseResult<String> logon(HttpSession sess,
                                        @Valid LogonVo logonVo ,
                                        BindingResult result){
        String verifyCode = (String)sess.getAttribute(VERIFY_CODE_KEY);
        ResponseResult<String> res=null; ResultStatus status = null;
        if(verifyCode==null)
            res = ResponseResult.build(ResultStatus.UNVERIFIED);
        else if(result.hasErrors()){
            //不合法注册信息
            res = ResponseResult.build(ResultStatus.ERROR_REGISTER_INFO);
            res.setData(result.getAllErrors().get(0).getDefaultMessage());
        }else if(!logonVo.getVerifyCode()
                    .equals(verifyCode)){
            // 验证码错误，服务端清除之前在Session中的verifyCode
            res = ResponseResult.build(ResultStatus.ERROR_VERIFY_CODE);
        }else {
            UserAccountVo uav = generateUserAccount(logonVo);
            // 由服务层处理是否重命名，注册成功、失败、未知错误的状态
            status = los.registerUser(uav);
            res = ResponseResult.build(status);
        }//end else
        sess.removeAttribute(VERIFY_CODE_KEY);
        return res;
    }


    /**
     * 通过key和当前时间生成一个验证码
     * @param key
     * @return
     */
    private String generateVerifyCode(String key){
        key+=System.currentTimeMillis();
        return MD5.encode(key).substring(0,5);
    }

    /**
     * 将LogonVo转到UserAccountVo
     * @param user
     * @return
     */
    private UserAccountVo generateUserAccount(LogonVo user){
        UserAccountVo ua = new UserAccountVo();
        ua.setUsername(user.getUsername());
        //由用户名获得数字ID
        ua.setUserId(UserIdUtils.getUserId(user.getUsername()));
        ua.setLogCot(0);
        ua.setRegTime(Instant.now().toString());
        ua.setRole(Roles.ROLE_USER.getName());
        ua.setSalt(PassEncUtils.salt());
        ua.setPassword(PassEncUtils
                .encodePassword(user.getPassword(),ua.getSalt()));
        return ua;
    }




}
