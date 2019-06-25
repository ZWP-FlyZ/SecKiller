package com.zwp.web.controller;

import com.zwp.comm.resulttype.ResponseResult;
import com.zwp.comm.resulttype.ResultStatus;
import com.zwp.comm.utils.MD5;
import com.zwp.service.seckill.SeckillService;
import com.zwp.web.vo.UserAccountDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

/**
 * @program: seckiller
 * @description: 秒杀流程的控制层
 * @author: zwp-flyz
 * @create: 2019-06-25 12:51
 * @version: v1.0
 **/
@RestController
@RequestMapping("seckill")
public class SeckillerController {

    //
    private final static String PERFIX_SK_VERIFY_CODE = "SK_VERIFY_CODE_GOODS_";
    private final static String PERIX_SK_PATH = "SK_PATH_GOODS_";

    private final static Logger LOGGER = LoggerFactory.getLogger(SeckillerController.class);

    @Autowired
    SeckillService seckillService;


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
        Authentication aut = SecurityContextHolder.getContext().getAuthentication();
        UserAccountDetails user =  (UserAccountDetails)aut.getPrincipal();
        String vfc = generateVerifyCode(user.getUsername());
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
            Authentication aut = SecurityContextHolder.getContext().getAuthentication();
            UserAccountDetails user =  (UserAccountDetails)aut.getPrincipal();
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

        ResponseResult<String> res = null;
        if(Objects.isNull(skPath)||Objects.isNull(goodsId))
            return ResponseResult.build(ResultStatus.ERROR_UPLOAD_INFO);
        String key = PERIX_SK_PATH+goodsId;
        String skp = (String)sess.getAttribute(key);

        if(skp==null){// 未曾获取秒杀令牌
            res = ResponseResult.build(ResultStatus.UNVERIFIED);
        }else if(!skp.equals(skPath)){
            res = ResponseResult.build(ResultStatus.ERROR_SK_PATH);
        }else{

        }
        return null;
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


}
