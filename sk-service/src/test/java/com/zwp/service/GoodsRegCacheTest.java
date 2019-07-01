package com.zwp.service;

import com.zwp.comm.vo.SkGoodsVo;
import com.zwp.service.goods.SkGoodsCacheService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @program: seckiller
 * @description:
 * @author: zwp-flyz
 * @create: 2019-07-01 10:12
 * @version: v1.0
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class GoodsRegCacheTest {

    @Autowired
    SkGoodsCacheService cacheService;
    @Test
    public void ragGoodsCacheTaskTest(){

        SkGoodsVo goodsVo= new SkGoodsVo();
        goodsVo.setGoodsId(15L);
        goodsVo.setGoodsStock(111);
        goodsVo.setStartTime("2019/07/01 12:00:00");
        goodsVo.setEndTime("2019/07/01 12:01:00");
        cacheService.createSkGoodsStockTask(goodsVo);
    }
}
