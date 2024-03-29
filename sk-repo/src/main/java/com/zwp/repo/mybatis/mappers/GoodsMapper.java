package com.zwp.repo.mybatis.mappers;

import com.zwp.comm.vo.SkGoodsVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @program: seckiller
 * @description: 获取和添加秒杀货物列表和货物详情Mapper
 * @author: zwp-flyz
 * @create: 2019-06-24 10:10
 * @version: v1.0
 **/
@Mapper
public interface GoodsMapper {

    /**
     * 获得所有秒杀货物列表
     * @return
     */
     List<SkGoodsVo> selectSkGoodsList();

    /**
     * 保存秒杀货物
     * @param goods
     * @return
     */
     Integer insertSkGoods(SkGoodsVo goods);

     Integer insertSkGoodsDetail(SkGoodsVo goods);

     SkGoodsVo selectSkGoodsDetailByGoodsId(Long goodsId);

    /**
     * 减少库存
     * @return
     */
     Integer decSkGoods(SkGoodsVo goods);
}
