package com.zwp.service.rabbitmq;

import com.zwp.comm.enums.MqKeys;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: seckiller
 * @description: rabbitmq消息队列配置
 * @author: zwp-flyz
 * @create: 2019-06-29 14:06
 * @version: v1.0
 **/
@Configuration
@EnableRabbit
public class RabbitMqConfig {


    /**
     * 秒杀业务处理
     * @return
     */
    @Bean
    public SeckillMessageListener seckillMessageListener(){
        return new SeckillMessageListener();
    }

    @Bean
    public RabbitMqSendService service(){
        return new RabbitMqSendService();
    }

    /**
     * 秒杀用的队列，所有通过的拦截秒杀
     * @return
     */
    @Bean("seckillQueue")
    public Queue seckillQueue(){
        return new Queue(MqKeys.SECKILL_QUEUE,true);
    }

    @Bean("seckillExchange")
    public Exchange seckillExchange(){
        return new DirectExchange(MqKeys.SECKILL_EXCHANGE);
    }

    @Bean("seckillBinding")
    public Binding seckillBinding(){
        return BindingBuilder
                .bind(seckillQueue())
                .to(seckillExchange())
                .with(MqKeys.SECKILL_QUEUE)
                .noargs();
    }




}
