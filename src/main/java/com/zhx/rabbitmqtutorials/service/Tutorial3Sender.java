package com.zhx.rabbitmqtutorials.service;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

public class Tutorial3Sender {

    @Autowired
    private RabbitTemplate template;

    @Autowired
    private FanoutExchange fanoutExchange;

    private int dots = 0;

    private int count = 0;

    @Scheduled(fixedDelay = 1000, initialDelay = 500)
    public void send() {
        StringBuilder builder = new StringBuilder("Hello");
        if (dots++ == 3) {
            dots = 1;
        }
        for (int i = 0; i < dots; i++) {
            builder.append('.');
        }
        builder.append(Integer.toString(++count));
        String message = builder.toString();
        /*
         The first parameter is the the name of the exchange that was autowired into the sender.
         The empty string denotes the default or nameless exchange: messages are routed to the queue
         with the name specified by routingKey, if it exists.
         第一个参数是自动装配给sender的交换机的名字,空字符指向默认或无名的交换机:
         通过第二个参数routingKey来标识消息路由给哪个队列，如果routingKey存在的话。
         */
        template.convertAndSend(fanoutExchange.getName(), "", message);
        System.out.println(" [x] Sent '" + message + "'");
    }
}
