package com.gaoice.system.common.rabbit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestRabbitPublisher {

    @Autowired
    RabbitTemplate rabbitTemplate;

    public void send(String s) {
        rabbitTemplate.convertAndSend("test1Queue", s);
    }

}
