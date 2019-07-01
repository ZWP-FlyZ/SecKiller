package com.zwp.service;

import com.zwp.service.rabbitmq.RabbitMqSendService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @program: seckiller
 * @description: 消息队列测试
 * @author: zwp-flyz
 * @create: 2019-06-29 15:23
 * @version: v1.0
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class MqTest {

    @Autowired
    RabbitMqSendService service;

    @Test
    public void sendMessage() {
        service.sendStringMessage("hi mq test");
    }



}
