package com.zhx.rabbitmqtutorials.service;

import org.springframework.amqp.core.HeadersExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

public class HeaderSender {

    @Autowired
    private RabbitTemplate template;

    @Autowired
    private HeadersExchange headersExchange;

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

        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setHeader("color", "red");
        Message message1 = new Message(message.getBytes(), messageProperties);
        template.convertAndSend(headersExchange.getName(), "", message1);
        System.out.println(" [x] Sent '" + message + "'");
    }
}
