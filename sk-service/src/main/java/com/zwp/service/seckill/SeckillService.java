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


    /**
     * 标记用户是否已经处于秒杀某一货物的状态
     * @param username
     * @param goodsId
     * @return 如果设置成功返回true，如果已经处于秒杀状态则放回false
     */
    public boolean setOnSeckillStatusFlag(String username,Long goodsId){

        // 注意最好在该服务中设置自动清除秒杀状态缓存的功能
            return true;
    }

    /**
     * 检查用户是否已经成功秒杀一件货物
     * @param username
     * @param goodsId
     * @return 若秒杀成功返回true，否则false
     */
    public boolean checkSeckillSuccess(String username,Long goodsId){
        return false;
    }


    /**
     * 从缓存中减少货物库存
     * @param goodsId
     * @return 若货物处于秒杀期间，则返回减少后剩余数量。
     * 入货物不在秒杀期间则返回Integer.MIN_VALUE
     */
    public int desc(Long goodsId){
        return 0;
    }


}
