package com.zwp.service.seckill;

import com.zwp.comm.resulttype.ResultStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @program: seckiller
 * @description: 秒杀流程的服务和义务逻辑
 * @author: zwp-flyz
 * @create: 2019-06-25 13:18
 * @version: v1.0
 **/
@Service
public class SeckillService {

    @Autowired
    ApplicationContext ctx;

    /**
     * 通过验证码生成验证图片
     * @param verifyCode
     * @return
     */
    public BufferedImage getVerifyImageByCode(String verifyCode) throws IOException {
        Assert.notNull(verifyCode,"the verifyCode is null");
        Resource res =ctx.getResource("classpath:static/background.jpg");
        return ImageIO.read(res.getInputStream());
    }

    public ResultStatus doSecKill(String username,Long goodsId){
            return null;
    }


}
