package com.zwp.web.controller;

import com.zwp.comm.resulttype.ResponseResult;
import com.zwp.comm.resulttype.ResultStatus;
import com.zwp.comm.vo.SkGoodsVo;
import com.zwp.service.goods.GoodsService;
import com.zwp.web.vo.AddSkGoodsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @program: seckiller
 * @description: 货物控制层
 * @author: zwp-flyz
 * @create: 2019-06-24 08:40
 * @version: v1.0
 **/
@RestController
@RequestMapping("/skgoods")
public class GoodsController {

    private final static Logger LOGGER = LoggerFactory.getLogger(GoodsController.class);
    // 时间格式化器，线程安全
    private final static DateTimeFormatter fmt = DateTimeFormatter
                                            .ofPattern("yyyy/MM/dd HH:mm:ss");
    @Autowired
    GoodsService gds;



    /**
     * 返回秒杀货物，
     * @return 当无秒杀货物时，data返回空数组[]
     */
    @GetMapping("/goods_list")
    public ResponseResult<List<SkGoodsVo>> goodsList(){
        // 这里获得所有秒杀货物，实际应用中可以根据条件进行筛选
        // 其中包括还在秒杀中，时间过期的秒杀，还未开始的秒杀
        List<SkGoodsVo> li = gds.getSkGoodsList();
        ResponseResult<List<SkGoodsVo>> res =
                            ResponseResult.build();
        res.setData(li);
        return res;
    }


    @PostMapping("/reg_skgoods")
    public ResponseResult<String> addSkGoods(@Valid AddSkGoodsVo goods, BindingResult result){
        ResponseResult<String> res = null;
//        System.err.println(goods.toString());
        if(result.hasErrors()||checkSeckillerTime(goods)){
            LOGGER.info("Error SkGoods Data");
            res = ResponseResult.build(ResultStatus.INCURRECT_SK_GOODS_INFO);
            res.setData("秒杀货物注册数据不正确或者安排秒杀的时间不合适");
        }else{
            SkGoodsVo vo = generateGoodsVo(goods);
            LOGGER.debug(vo.toString());
            //注册秒杀货物
            ResultStatus status = gds.regSkGoods(vo);
            if(status.equals(ResultStatus.SUCCESS))
                LOGGER.info("SkGoods [{}] register success!",goods.getGoodsName());
            else
                LOGGER.info("SkGoods [{}] register failure!");
            res = ResponseResult.build(status);
        }
        return res;
    }


    @GetMapping("/goods_detail")
    public ResponseResult<SkGoodsVo> getSkGoodsDetail(Long goodsId){

        ResponseResult<SkGoodsVo> res = null;
        if(goodsId==null||goodsId<0)
            res = ResponseResult.build(ResultStatus.INCURRECT_SK_GOODS_INFO);
        else{
            SkGoodsVo vo = gds.getSkGoodsDetailByGoodsId(goodsId);
            if(vo==null)
                res = ResponseResult.build(ResultStatus.INCURRECT_SK_GOODS_INFO);
            else
            {
                res = ResponseResult.build();
                res.setData(vo);
            }
        }
        return res;
    }



    /**
     * 检测秒杀的开始和结束时间是否正确和合理
     * @param goods
     * @return 如果时间有问题，返回true，否则返回false
     */
    private boolean checkSeckillerTime(AddSkGoodsVo goods){
        LocalDateTime startDate = null,endDate;
        try {
            startDate = LocalDateTime.parse(goods.getStartTime(), fmt);
            endDate = LocalDateTime.parse(goods.getStartTime(), fmt);
            LocalDateTime t = endDate.plusMinutes(-1);
            if(startDate.isBefore(t)||startDate.isEqual(t))// 结束时间必须比开始时间迟1min
                throw new IllegalArgumentException("endTime - startTime < 1 minute");
        }catch (Exception e){
            LOGGER.debug("SkTime format error!",e);
            return true;
        }
        LocalDateTime nowP1 = LocalDateTime.now().plusHours(1);
        if(startDate.isBefore(nowP1)||
                startDate.equals(nowP1))// 必须提前一小时注册秒杀,否则不通过
            return true;
        return false;
    }

    /**
     * 构造SkGoodsVo
     * @param vo
     * @return
     */
    private SkGoodsVo generateGoodsVo(AddSkGoodsVo vo){
        SkGoodsVo skGoodsVo = new SkGoodsVo();
        skGoodsVo.setGoodsName(vo.getGoodsName());
        skGoodsVo.setGoodsSkPrice(vo.getGoodsSkPrice());
        skGoodsVo.setGoodsSkStock(vo.getGoodsSkStock());
        skGoodsVo.setGoodsSkCurStock(vo.getGoodsSkStock());
        skGoodsVo.setStartTime(vo.getStartTime());
        skGoodsVo.setEndTime(vo.getEndTime());
        skGoodsVo.setGoodsDetail(vo.getGoodsDetail());
        skGoodsVo.setGoodsImgPath("/path/to/"+vo.getGoodsName());
        skGoodsVo.setGoodsPrice(vo.getGoodsPrice());
        skGoodsVo.setGoodsStock(vo.getGoodsStock());
        return skGoodsVo;
    }


}
