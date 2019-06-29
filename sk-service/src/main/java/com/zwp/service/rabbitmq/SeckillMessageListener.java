package com.zwp.service.rabbitmq;

import com.zwp.comm.enums.MqKeys;
import com.zwp.comm.vo.SeckillMessageVo;
import com.zwp.service.seckill.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @program: seckiller
 * @description: 消息接收器
 * @author: zwp-flyz
 * @create: 2019-06-29 14:49
 * @version: v1.0
 **/
@RabbitListener(queues = MqKeys.SECKILL_QUEUE)
public class SeckillMessageListener {

    private final static Logger LOGGER = LoggerFactory.getLogger(SeckillMessageListener.class);
    @Autowired
    SeckillService ss;

    /**
     * 秒杀消息处理
     * @param message
     */
    @RabbitHandler
    public void seckillProcess(SeckillMessageVo message){
        ss.doSecKill(message.getUsername(),message.getGoodsId());
    }

    @RabbitHandler
    public void stringProcess(String message){
        LOGGER.debug("get message:{}",message);
    }
}
