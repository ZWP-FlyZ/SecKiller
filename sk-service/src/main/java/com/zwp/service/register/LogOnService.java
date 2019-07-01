package com.zwp.service.register;

import com.zwp.comm.resulttype.ResultStatus;
import com.zwp.comm.utils.VerifyCodeUtils;
import com.zwp.comm.vo.UserAccountVo;
import com.zwp.repo.datasourceconfig.DataSourceType;
import com.zwp.repo.datasourceconfig.UseDatasource;
import com.zwp.repo.mybatis.mappers.LogonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @program: seckiller
 * @description: 注册时数据库相关的义务逻辑和服务
 * @author: zwp-flyz
 * @create: 2019-06-21 10:45
 * @version: v1.0
 **/
@Service
public class LogOnService {

    private final static Logger LOGGER = LoggerFactory.getLogger(LogOnService.class);

    @Autowired
    ApplicationContext ctx;

    @Autowired
    LogonMapper logonMapper;

    /**
     * 通过verifyCode生成一个验证图片
     * @param verifyCode
     * @param verifyCode
     * @return
     */
    public BufferedImage getVerifyImage(String verifyCode) throws IOException {
//        Resource res =ctx.getResource("classpath:static/background.jpg");
        return VerifyCodeUtils.generateVerifyPicture(verifyCode);

    }


    /**
     * 将user保存入数据库中，当保存成功则返回SUCCESS，
     * 当用户名重复则返回EXIT_USERNAME
     * @param user
     * @return
     */
    @UseDatasource(DataSourceType.ACCOUNT_DATASOURCE)
    public ResultStatus registerUser(UserAccountVo user){
        int res =logonMapper.saveUserAccount(user);
        if(res>0){
            LOGGER.debug("register user:[{}] success.",user.getUsername());
            return ResultStatus.SUCCESS;
        }else{
            LOGGER.debug("register user:[{}] failure, username repeat!.",user.getUsername());
            return ResultStatus.EXIT_USERNAME;
        }
    }
}
