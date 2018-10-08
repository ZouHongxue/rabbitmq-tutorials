package com.zhx.rabbitmqtutorials.service;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Profile;
import org.springframework.util.StopWatch;

@Profile({"work", "world"})
@RabbitListener(queues = "hello1")
public class Tutorial2Receiver {

    private final int instance;

    public Tutorial2Receiver(int i) {
        this.instance = i;
    }

    @Profile({"work", "world"})
    @RabbitHandler
    public void receive(String msg) throws InterruptedException {
        StopWatch watch = new StopWatch();
        watch.start();
        System.out.println("instance " + this.instance + " [x] Received '" + msg + "'");
        doWork(msg);
        watch.stop();
        System.out.println("instance " + this.instance + " [x] Done in " + watch.getTotalTimeSeconds() + "s");
    }

    private void doWork(String msg) throws InterruptedException {
        for (char ch : msg.toCharArray()) {
            if (ch == '.') {
                Thread.sleep(1000);
            }
        }
    }
}
