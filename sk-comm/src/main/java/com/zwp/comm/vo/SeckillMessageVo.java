package com.zwp.comm.vo;

import lombok.*;

import java.io.Serializable;

/**
 * @program: seckiller
 * @description: 秒杀请求消息
 * @author: zwp-flyz
 * @create: 2019-06-29 14:19
 * @version: v1.0
 **/
@NoArgsConstructor
@AllArgsConstructor
public class SeckillMessageVo implements Serializable {

    private static final long serialVersionUID = -4083463748853059744L;
    @Getter @Setter private String username;
    @Getter @Setter private Long goodsId;

}
