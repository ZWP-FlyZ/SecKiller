package com.zwp.comm.vo;

import lombok.Data;

/**
 * @program: seckiller
 * @description: 秒杀订单
 * @author: zwp-flyz
 * @create: 2019-06-29 18:36
 * @version: v1.0
 **/
@Data
public class SkOrderVo {

    private Long orderId;
    private Long userId;
    private Long goodsId;
    private String goodsName;
    private Double goodsPrice;
    private Integer goodsCot;
    private Integer orderStatus;
    private String createTime;
    private String payTime;
    private Integer orderAddr;

}
