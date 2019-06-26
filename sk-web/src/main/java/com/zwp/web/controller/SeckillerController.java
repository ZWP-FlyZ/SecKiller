package com.zwp.web.controller;

import com.google.common.util.concurrent.RateLimiter;
import com.zwp.comm.resulttype.ResponseResult;
import com.zwp.comm.resulttype.ResultStatus;
import com.zwp.comm.utils.MD5;
import com.zwp.comm.vo.UserAccountVo;
import com.zwp.service.seckill.SeckillService;
import com.zwp.web.vo.UserAccountDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @program: seckiller
 * @description: 秒杀流程的控制层
 * @author: zwp-flyz
 * @create: 2019-06-25 12:51
 * @version: v1.0
 **/
@RestController
@RequestMapping("seckill")
@ConfigurationProperties("sk.ratelimiter")
public class SeckillerController {

    //
    private final static String PERFIX_SK_VERIFY_CODE = "SK_VERIFY_CODE_GOODS_";
    private final static String PERIX_SK_PATH = "SK_PATH_GOODS_";

    private final static Logger LOGGER = LoggerFactory.getLogger(SeckillerController.class);




    @Autowired
    SeckillService seckillService;

    //最大限流数
    private Double maxPermits=100.0;
    // 限流超时毫秒数
    private Integer maxTimeout=1000;

    // 限流
    private RateLimiter rateLimiter;

    //本地秒杀结束标志，阻止多余连接
    // concurrenthashmap 线程安全
    @Qualifier("overFlag")
    Map<Long,Boolean> overFlag;


    @PostConstruct
    public void init(){
        rateLimiter = RateLimiter.create(maxPermits);
    }

    /**
     * 申请秒杀验证码
     * @param sess
     * @param goodsId
     * @return
     */
    @GetMapping("/verify_code")
    public ResponseResult<String> skVerify(HttpSession sess,
                                           HttpServletResponse response,
                                           Long goodsId) throws IOException {
        UserAccountDetails user =  getUserAccount();
        String vfc = generateVerifyCode(user.getUsername());
        //可以在该处检查请求秒杀的货物是否在秒杀期间内，
        //阻止不合法的秒杀请求
        BufferedImage img = seckillService.getVerifyImageByCode(vfc);
        LOGGER.debug("seckiller for goods:{} verifyCode:{}",goodsId,vfc);
        if(img==null)
                return ResponseResult.build(ResultStatus.EXCEPTION);
        ImageIO.write(img,"JPEG",response.getOutputStream());
        sess.setAttribute(PERFIX_SK_VERIFY_CODE+goodsId,vfc);
        return ResponseResult.build();
    }

    /**
     * 检测验证码并返回秒杀令牌
     * @param sess
     * @param goodsId
     * @param verifyCode
     * @return
     */
    @GetMapping("/sk_path")
    public ResponseResult<String> getSkPath(HttpSession sess,
                                            Long goodsId,
                                            String verifyCode){
        if(goodsId==null||verifyCode==null)
            return ResponseResult.build(ResultStatus.ERROR_UPLOAD_INFO);
        String key = PERFIX_SK_VERIFY_CODE+goodsId;
        String vfc = (String)sess.getAttribute(key);
        if(vfc==null)// 未曾请求验证码
            return ResponseResult.build(ResultStatus.UNVERIFIED);
        else if(!vfc.equals(verifyCode)){
            // 验证码不正确
            sess.removeAttribute(key);
            return ResponseResult.build(ResultStatus.ERROR_VERIFY_CODE);
        }else{
            UserAccountDetails user =  getUserAccount();
            String skp = generateSecKillerPath(user.getUsername(),goodsId);
            LOGGER.debug("user:{} goods:{} skpath:{}",user.getUsername(),goodsId,skp);
            sess.setAttribute(PERIX_SK_PATH+goodsId,skp);
            sess.removeAttribute(key);
            ResponseResult<String> res = ResponseResult.build();
            res.setData(skp);
            return res;
        }

    }


    /**
     * 秒杀
     * @param sess
     * @param goodsId
     * @return
     */
    @GetMapping("/{skPath}/do_seckill")
    public ResponseResult<String> seckill(HttpSession sess,
                                          @PathVariable("skPath") String skPath,
                                          Long goodsId){

        if(Objects.isNull(skPath)||Objects.isNull(goodsId))
            return ResponseResult.build(ResultStatus.ERROR_UPLOAD_INFO);
        String key = PERIX_SK_PATH+goodsId;
        String skp = (String)sess.getAttribute(key);

        if(skp==null){// 未曾获取秒杀令牌
            return ResponseResult.build(ResultStatus.UNVERIFIED);
        }else if(!skp.equals(skPath)){ //令牌不正确
            return ResponseResult.build(ResultStatus.ERROR_SK_PATH);
        }
        // 在controller层做RateLimiter限流
        // 将过高的请求流程限制在controller层中
        // 可以采用Google的guava RateLimiter实现,本项目采用该方法实现限流。
        // 也可以采用Redis的Lua脚本实现限流
        // 以下方法所有秒杀货物都共享同一个令牌桶
        if(!rateLimiter.tryAcquire(maxTimeout, TimeUnit.MILLISECONDS)){
            // 若干毫秒内如果不能获得执行令牌，则表示当前系统极度繁忙。
            // 注意若秒杀商品数较多，执行令牌较少和超时时间较短时，会出现商品还未秒杀完，
            // 需要做一些简单业务
        }

        // 检查本地缓存，秒杀结束
        if(overFlag.getOrDefault(goodsId,false)){
            LOGGER.debug("goods [{}] seckill over",goodsId);
            return ResponseResult.build(ResultStatus.SK_OVER);
        }

        UserAccountDetails user =  getUserAccount();
        // 检查用户秒杀状态并检查重复秒杀请求
        if(!seckillService.setOnSeckillStatusFlag(user.getUsername(),goodsId)){
            // 用户重复请求秒杀货物
            LOGGER.debug("Repeat seckill request: user[{}] - goodsId[{}]",user.getUsername(),goodsId);
            return ResponseResult.build(ResultStatus.REPEAT_SK_REQUEST);
        }

        // 检查是否用户已经秒杀成功并创建订单
        // 注意 系统认为任何用户都只能秒杀成功同一货物一次。
        if(seckillService.checkSeckillSuccess(user.getUsername(),goodsId)){
            LOGGER.debug("Order created but repeat  eclill request: user[{}] - goodsId[{}]",
                                    user.getUsername(),goodsId);
            return ResponseResult.build(ResultStatus.REPEAT_SK_REQUEST);
        }

        // 检查缓存中货物剩余数量并减少库存
        int rem = seckillService.desc(goodsId);
        if(rem<=0){
            overFlag.put(goodsId,true);
            LOGGER.debug("goods [{}] seckill over rem:[{}]",goodsId,rem);
            return ResponseResult.build(ResultStatus.SK_OVER);
        }

        // 消息队列中提交秒杀请求

        return ResponseResult.build();
    }




    /**
     * 由用户名和货物Id生成秒杀令牌
     * @param username
     * @param goodsId
     * @return
     */
    private String generateSecKillerPath(String username,Long goodsId){
        StringBuilder sb = new StringBuilder();
        sb.append("* *0* 00 * ");
        sb.append(username).append("*0*");
        sb.append(System.currentTimeMillis());
        sb.append("0*0").append(goodsId);
        String v = sb.toString();
        v = MD5.encode(v);v = MD5.encode(v+"HI,DECODER:)!");
        return v.substring(0,15);
    }

    /**
     * 生成5字节秒杀验证码
     * @param username
     * @return
     */
    private String generateVerifyCode(String username){
        String v = "* *0* 00 * "+username+"*0*"+System.currentTimeMillis();
        v = MD5.encode(v);v = MD5.encode("1234"+v);
        return v.substring(0,5);
    }


    private UserAccountDetails getUserAccount(){
        Authentication aut = SecurityContextHolder.getContext().getAuthentication();
        return (UserAccountDetails)aut.getPrincipal();
    }


}
