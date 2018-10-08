package com.zhx.rabbitmqtutorials;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RabbitmqTutorialsApplication {

    public static void main(String[] args) {
        SpringApplication.run(RabbitmqTutorialsApplication.class, args);
    }
}
