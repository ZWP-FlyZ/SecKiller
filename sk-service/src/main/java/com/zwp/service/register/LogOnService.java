package com.zwp.service.register;

import com.zwp.comm.resulttype.ResultStatus;
import com.zwp.comm.vo.UserAccountVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 * @program: seckiller
 * @description: 注册时数据库相关的义务逻辑和服务
 * @author: zwp-flyz
 * @create: 2019-06-21 10:45
 * @version: v1.0
 **/
@Service
public class LogOnService {

    @Autowired
    ApplicationContext ctx;

    /**
     * 通过verifyCode生成一个验证图片
     * @param verifyCode
     * @param verifyCode
     * @return
     */
    public BufferedImage getVerifyImage(String verifyCode) throws IOException {
        Resource res =ctx.getResource("classpath:static/background.jpg");
        return ImageIO.read(res.getInputStream());
    }

    public InputStream getVerifyImage() throws IOException {
        Resource res =ctx.getResource("classpath:static/background.jpg");
        return res.getInputStream();
    }

    /**
     * 将user保存入数据库中，当保存成功则返回SUCCESS，
     * 当用户名重复则返回EXIT_USERNAME
     * 当出现未知错误则返回EXCEPTION
     * @param user
     * @return
     */

    public ResultStatus registerUser(UserAccountVo user){
        return ResultStatus.SUCCESS;
    }


}
