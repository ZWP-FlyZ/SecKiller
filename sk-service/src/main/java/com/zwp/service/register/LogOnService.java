package com.zwp.service.register;

import com.zwp.comm.resulttype.ResultStatus;
import com.zwp.comm.vo.UserAccountVo;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;

/**
 * @program: seckiller
 * @description: 注册时数据库相关的义务逻辑和服务
 * @author: zwp-flyz
 * @create: 2019-06-21 10:45
 * @version: v1.0
 **/
@Service
public class LogOnService {

    /**
     * 通过verifyCode生成一个验证图片
     * @param verifyCode
     * @param verifyCode
     * @return
     */
    public BufferedImage getVerifyImage(String verifyCode){
            return null;
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
