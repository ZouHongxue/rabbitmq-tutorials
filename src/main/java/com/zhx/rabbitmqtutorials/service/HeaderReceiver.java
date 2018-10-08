package com.zhx.rabbitmqtutorials.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;

public class HeaderReceiver {
    @RabbitListener(queues = "#{autoDeleteQueue1.name}")
    public void receiver1(byte[] msg) throws InterruptedException {
        Receiver.receiver(new String(msg), 1);
    }
}
