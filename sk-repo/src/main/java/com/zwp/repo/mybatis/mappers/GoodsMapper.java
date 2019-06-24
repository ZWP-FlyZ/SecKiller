package com.zwp.repo.mybatis.mappers;

import com.zwp.comm.vo.SkGoodsVo;

import java.util.List;

/**
 * @program: seckiller
 * @description: 获取和添加秒杀货物列表和货物详情Mapper
 * @author: zwp-flyz
 * @create: 2019-06-24 10:10
 * @version: v1.0
 **/
public interface GoodsMapper {

    /**
     * 获得所有秒杀货物列表
     * @return
     */
     List<SkGoodsVo> getSkGoodsList();


}
