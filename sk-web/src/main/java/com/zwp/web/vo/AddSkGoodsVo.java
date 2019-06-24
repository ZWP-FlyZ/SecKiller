package com.zwp.web.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;

/**
 * @program: seckiller
 * @description: 来自商家注册秒杀的商品信息
 * @author: zwp-flyz
 * @create: 2019-06-24 13:59
 * @version: v1.0
 **/
@Data
public class AddSkGoodsVo {

    @NotNull(message = "goods name is null")
    @Size(min=1,message = "the length of goods name is less 1!")
    private String goodsName;
    // 秒杀货物字段
    @NotNull(message = "goodsSkPrice is null")
    @Min(value = 0,message = "goodsSkPrice < 0")
    private Double goodsSkPrice;
    @Min(value = 0,message = "goodsSkPrice < 0")
    @Max(value = Integer.MAX_VALUE-1,message = "goodsSkPrice is too lager")
    private Integer goodsSkStock;//秒杀总库存
    @NotNull(message = "startTime is null")
    @Size(min=19,max = 19,message = "Time Format:yyyy/MM/dd hh:mm:ss")
    private String startTime;
    @NotNull(message = "endTime is null")
    @Size(min=19,max = 19,message = "Time Format:yyyy/MM/dd hh:mm:ss")
    private String endTime;

    // 货物详情
    private String goodsDetail;
    @NotNull(message = "goodsPrice is null")
    @Min(value = 0,message = "the goodsPrice is less than 0")
    @Max(value = Integer.MAX_VALUE,message = "the goodsPrice is bigger than 2^31")
    private Double goodsPrice;//货物原价
    @Min(value = 0,message = "the goodsStock is less than 0")
    @Max(value = Integer.MAX_VALUE,message = "the goodsStock is bigger than 2^31")
    private Integer goodsStock;//货物库存，与秒杀库存不同


}
