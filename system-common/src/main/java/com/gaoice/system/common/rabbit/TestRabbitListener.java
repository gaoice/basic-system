package com.gaoice.system.common.rabbit;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class TestRabbitListener {

    @RabbitListener(queues = {"test1Queue"})
    public void process1(Message message) throws Exception {
        System.out.println(message);
        Thread.sleep(1000);
    }

    @RabbitListener(queues = {"test2Queue"})
    public void process2(Message message) throws Exception {
        System.out.println(message);
        Thread.sleep(1000);
    }

}
