package com.zwp.web.controller;

import com.zwp.comm.resulttype.ResponseResult;
import com.zwp.comm.vo.SkGoodsVo;
import com.zwp.service.goods.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
