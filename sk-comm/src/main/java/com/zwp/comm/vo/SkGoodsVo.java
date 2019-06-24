package com.zwp.comm.vo;

import lombok.Data;

/**
 * @program: seckiller
 * @description: 秒杀商品信息
 * @author: zwp-flyz
 * @create: 2019-06-24 08:54
 * @version: v1.0
 **/
@Data()
public class SkGoodsVo {
    private Long goodsId;
    private String goodsName;
    // 秒杀货物字段
    private Double goodsSkPrice;
    private Integer goodsSkStock;//秒杀总库存
    private Integer goodsSkCurStock;//秒杀当前库存
    private String startTime;
    private String endTime;
    // 货物详情
    private String goodsDetail;
    private String goodsImgPath;
    private Double goodsPrice;//货物原价
    private Integer goodsStock;//货物库存，与秒杀库存不同
}
