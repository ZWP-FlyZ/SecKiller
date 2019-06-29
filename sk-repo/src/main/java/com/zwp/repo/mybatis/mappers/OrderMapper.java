package com.zwp.repo.mybatis.mappers;

import com.zwp.comm.vo.SkOrderVo;

/**
 * @program: seckiller
 * @description: 订单相关Mapper接口
 * @author: zwp-flyz
 * @create: 2019-06-29 17:49
 * @version: v1.0
 **/
public interface OrderMapper {

    /**
     * 在SkOrder中插入插入订单占位
     * @param order
     * @return
     */
    Integer insertSkOrder(SkOrderVo order);

    /**
     * 在OrderInfo中插入订单详情
     * @param order
     * @return
     */
    Integer insertOrderInfo(SkOrderVo order);


    /**
     * 通过订单Id获得订单信息
     * @return
     */
    SkOrderVo selectOrderByOrderId(Long orderId);

    /**
     * 通过用户名和货物ID查找
     * @param userId
     * @param goodsId
     * @return
     */
    SkOrderVo selectSkOrderByUIdAndGoodsId(Long userId,Long goodsId);

}
