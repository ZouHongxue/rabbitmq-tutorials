package com.zhx.rabbitmqtutorials.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Profile;

@Profile("fanout")
public class Tutorial3Receiver {

    @RabbitListener(queues = "#{autoDeleteQueue1.name}")
    public void receive1(String msg) throws InterruptedException {
        Receiver.receiver(msg, 1);
    }
}
