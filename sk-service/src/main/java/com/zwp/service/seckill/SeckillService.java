package com.zwp.service.seckill;

import com.zwp.comm.resulttype.ResultStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @program: seckiller
 * @description: 秒杀流程的服务和义务逻辑
 * @author: zwp-flyz
 * @create: 2019-06-25 13:18
 * @version: v1.0
 **/
@Service
public class SeckillService {

    private final static Logger LOGGER = LoggerFactory.getLogger(SeckillService.class);

    // 表明当前用户是否处于秒杀某一货物的状态
    private final static String SECKILL_STATUS_PREFIX = "seckill_status:";
    // 表明当前用户成功秒杀某一货物
    private final static String SECKILL_SUCCESS_PREFIX = "seckill_success:";

    private final static String SECKILL_GOODS_COT_PREFIX= "seckill_goods_cot:";
    // 处于秒杀某一货物状态 状态清除时间
    private final static Long defaultSeckillStatusTimeout = 1800L;


    @Autowired
    ApplicationContext ctx;

    @Autowired
    @Qualifier("redisReaderTemp")
    StringRedisTemplate redisReaderTemp;

    @Autowired
    @Qualifier("redisWriterTemp")
    StringRedisTemplate redisWriterTemp;


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
     * 标记用户是否已经处于秒杀某一货物的状态,默认30分钟清除
     * @param username
     * @param goodsId
     * @return 如果设置成功返回true，如果已经处于秒杀状态则放回false
     */
    public boolean setOnSeckillStatusFlag(String username,Long goodsId){
        // 注意最好在该服务中设置自动清除秒杀状态缓存的功能
        String key = SECKILL_STATUS_PREFIX +"["+username+"]->["+goodsId+"]";
        Long v = redisWriterTemp.opsForValue().increment(key); // 自增
        boolean res = true;
        if(v==1){
            LOGGER.debug("Success set in seckill status user:[{}] goods:[{}]",username,goodsId);
            // 刚处于秒杀某物品的阶段
            redisWriterTemp.expire(key,defaultSeckillStatusTimeout, TimeUnit.SECONDS);
        }else if(v>1){
            // 已经处于秒杀某物品阶段
            LOGGER.debug("user:[{}] is in seckill status! " +
                        "goods:[{}] request count:{}.",username,goodsId,v);
            res=false;
        }
        return res;
    }

    /**
     * 检查用户是否已经成功秒杀一件货物,
     *
     * @param username
     * @param goodsId
     * @return 若秒杀成功返回数量，否则0
     */
    public Integer checkSeckillSuccess(String username,Long goodsId){
        String key = SECKILL_SUCCESS_PREFIX +"["+username+"]->["+goodsId+"]";
        String v = redisReaderTemp.opsForValue().get(key);
        Integer res;
        if(v!=null&&(res=Integer.valueOf(v))>0){
            LOGGER.debug("user:[{}] is success created order for " +
                    "goods:[{}] orders count:{}.",username,goodsId,res);
            return res;
        }else
            return 0;//秒杀不成功
    }


    /**
     * 从缓存中减少货物库存
     * @param goodsId
     * @return 返回减少后剩余数量，当小于0表示秒杀结束或者未在秒杀时间范围内
     */
    public int desc(Long goodsId){
        String key = SECKILL_GOODS_COT_PREFIX+"["+goodsId+"]";
        Long rem = redisWriterTemp.opsForValue().decrement(key);
        LOGGER.debug("goods:[{}] remain:[{}].",goodsId,rem);
        return rem.intValue();
    }


}
