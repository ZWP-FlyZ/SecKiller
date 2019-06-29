package com.zwp.service.rabbitmq;

import com.zwp.comm.enums.MqKeys;
import com.zwp.comm.vo.SeckillMessageVo;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @program: seckiller
 * @description: 消息队列发送服务
 * @author: zwp-flyz
 * @create: 2019-06-29 14:36
 * @version: v1.0
 **/

public class RabbitMqSendService {

    @Autowired
    private RabbitTemplate template;

    /**
     * 向消息队列中添加秒杀请求的消息
     * @param msg
     */
    public void sendSeckillMessage(SeckillMessageVo msg){
        template.convertAndSend(MqKeys.SECKILL_EXCHANGE,
                                MqKeys.SECKILL_QUEUE,
                                msg);
    }

    /**
     * 测试用
     * @param msg
     */
    public void sendStringMessage(String msg){
        template.convertAndSend(MqKeys.SECKILL_EXCHANGE,
                MqKeys.SECKILL_QUEUE,
                msg);
    }


}
