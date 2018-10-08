package com.zhx.rabbitmqtutorials.service;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Profile;

@Profile({"work", "world"})
@RabbitListener(queues = "hello1")
public class TutorialReceiver {

    @Profile({"work", "world"})
    @RabbitHandler
    public void receive(String msg) {
        System.out.println(" [x] Received '" + msg + "'");
    }
}
