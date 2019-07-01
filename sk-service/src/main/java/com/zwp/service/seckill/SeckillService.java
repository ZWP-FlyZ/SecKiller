package com.zwp.service.seckill;

import com.zwp.comm.resulttype.ResultStatus;
import com.zwp.comm.utils.JsonUtils;
import com.zwp.comm.utils.UserIdUtils;
import com.zwp.comm.vo.SkGoodsVo;
import com.zwp.comm.vo.SkOrderVo;
import com.zwp.service.goods.GoodsService;
import com.zwp.service.order.OrderService;
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
    // 订单缓存
    private final static String SECKILL_SUCCESS_ORDER_PREFIX = "seckill_success:";

    public final static String SECKILL_GOODS_COT_PREFIX= "seckill_goods_cot:";
    // 处于秒杀某一货物状态 状态清除时间
    private final static Long defaultSeckillStatusTimeout = 1800L;

    // 处于秒杀某一货物状态 状态清除时间
    private final static Long defaultSuccessStatusTimeout = 12*3600L;


    @Autowired
    ApplicationContext ctx;

    @Autowired
    @Qualifier("redisReaderTemp")
    StringRedisTemplate redisReaderTemp;

    @Autowired
    @Qualifier("redisWriterTemp")
    StringRedisTemplate redisWriterTemp;


    @Autowired
    OrderService orderService;

    @Autowired
    GoodsService goodsService;

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

    /**
     * 在持久层处理秒杀义务，更新数据库
     * @param username
     * @param goodsId
     * @return
     */
    public ResultStatus doSecKill(String username,Long goodsId){
        Assert.notNull(username,"userId is null");
        Assert.notNull(goodsId,"goodsId is null");
        ResultStatus res = null;
        SkOrderVo order =new SkOrderVo();
        order.setUserId(UserIdUtils.getUserId(username));
        order.setGoodsId(goodsId);
        if(!goodsService.desrGoodsStock(goodsId)){
            //数据库减库存失败，已经没有库存
            LOGGER.debug("username:{} goodsId:{} out of stock in database!",username,goodsId);
            return ResultStatus.SK_OVER;
        }

        if(orderService.createOrder(order)){
            //设置秒杀成功标记
            String key = SECKILL_SUCCESS_PREFIX +"["+username+"]->["+goodsId+"]";
            redisWriterTemp.opsForValue().set(key,"1",
                    defaultSuccessStatusTimeout,TimeUnit.SECONDS);
            // 创建订单成功
            String keyorder = SECKILL_SUCCESS_ORDER_PREFIX +"["+username+"]->["+goodsId+"]";
            res= ResultStatus.SUCCESS;
            //将订单信息存储在缓存中
            redisWriterTemp.opsForValue().set(keyorder,
                                            JsonUtils.toJson(order),
                                            defaultSuccessStatusTimeout,
                                            TimeUnit.SECONDS);
        }else{
            // 订单已经存在
            res=ResultStatus.REPEAT_SK_REQUEST;
            LOGGER.debug("username:{} goodsId:{} has already created order!",username,goodsId);
        }
        return res;
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
        redisWriterTemp.expire(key,defaultSeckillStatusTimeout, TimeUnit.SECONDS);
        boolean res = true;
        if(v==1){
            LOGGER.debug("Success set in seckill status user:[{}] goods:[{}]",username,goodsId);
            // 刚处于秒杀某物品的阶段
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
                    "goods:[{}].",username,goodsId);
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


    /**
     * 检查是否秒杀成功，
     * @param username
     * @param goodsId
     * @return 如果秒杀成功返回订单，否则返回null
     */
    public SkOrderVo checkSkSuccessAndGetOrder(String username,Long goodsId){
        Assert.notNull(username,"userId is null");
        Assert.notNull(goodsId,"goodsId is null");
        //首先检查缓存是否存在
        SkOrderVo res =null;
        String keyorder = SECKILL_SUCCESS_ORDER_PREFIX +"["+username+"]->["+goodsId+"]";
        String json = redisReaderTemp.opsForValue().get(keyorder);

        if(json!=null) return (SkOrderVo)JsonUtils.fromJson(json);
        else{
            res = orderService.getSkOrderByUIdAndGoodsId(UserIdUtils.getUserId(username),goodsId);
            // 将订单重新加入到缓存中
            if(res!=null)
                redisWriterTemp.opsForValue().set(keyorder,
                        JsonUtils.toJson(res),
                        defaultSuccessStatusTimeout,
                        TimeUnit.SECONDS);
        }
        return res;
    }


    /**
     * 将秒杀货物的数量加入到缓存中
     * @param goodsVo
     * @return
     */
    public void addGoodsCotToCache(SkGoodsVo goodsVo){
        String key = SECKILL_GOODS_COT_PREFIX+"["+goodsVo.getGoodsId()+"]";
        redisWriterTemp.opsForValue().set(key,goodsVo.getGoodsSkStock()+"");
    }

    /**
     * 将秒杀货物的数量从缓存中移除
     * @param goodsVo
     * @return
     */
    public boolean deleteGoodsCotToCache(SkGoodsVo goodsVo){
        String key = SECKILL_GOODS_COT_PREFIX+"["+goodsVo.getGoodsId()+"]";
        return redisWriterTemp.delete(key);
    }




}
