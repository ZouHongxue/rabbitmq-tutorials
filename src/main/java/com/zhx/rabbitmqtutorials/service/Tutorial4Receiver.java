package com.zhx.rabbitmqtutorials.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;

public class Tutorial4Receiver {

    @RabbitListener(queues = "#{autoDeleteQueue1.name}")
    public void receiver1(String msg) throws InterruptedException {
        Receiver.receiver(msg, 1);
    }

    @RabbitListener(queues = "#{autoDeleteQueue2.name}")
    public void receiver2(String msg) throws InterruptedException {
        Receiver.receiver(msg, 2);
    }
}
