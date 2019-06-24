package com.zwp.service.goods;

import com.zwp.comm.vo.SkGoodsVo;
import com.zwp.repo.mybatis.mappers.GoodsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: seckiller
 * @description: 货物相关服务
 * @author: zwp-flyz
 * @create: 2019-06-24 09:42
 * @version: v1.0
 **/
@Service
public class GoodsService {


    @Autowired
    GoodsMapper goodsMapper;

    /**
     * 获得货物列表
     * @return
     */
    public List<SkGoodsVo> getSkGoodsList(){
        return goodsMapper.getSkGoodsList();
    }




}
