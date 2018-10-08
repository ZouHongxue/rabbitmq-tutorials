package com.zhx.rabbitmqtutorials.config;

import com.zhx.rabbitmqtutorials.service.*;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.HashMap;

@Configuration
public class AmqpConfig {

    private static final String QUEUE_NAME = "hello1";

//Tutorial 1-2 hello world work-Queues
    @Profile({"work", "world"})
    private static class ReceiverConfig {

        @Bean
        public Queue hello() {
            return new Queue(QUEUE_NAME);
        }

        @Bean
        public TutorialSender sender() {
            return new TutorialSender();
        }

        @Bean
        public Tutorial2Sender sender2() {
            return new Tutorial2Sender();
        }

        @Bean
        public Tutorial2Receiver receiver1() {
            return new Tutorial2Receiver(1);
        }

        @Bean
        public Tutorial2Receiver receiver2() {
            return new Tutorial2Receiver(2);
        }

        @Bean
        public TutorialReceiver receiver() {
        return new TutorialReceiver();
    }
    }

// tutorial 广播交换机
    @Profile("fanout")
    private static class ReceiverConfig3 {

        @Bean
        public FanoutExchange fanoutExchange() {
            return new FanoutExchange("tut.fanout");
        }

        @Bean
        public Tutorial3Sender sender3() {
            return new Tutorial3Sender();
        }

        //匿名队列
        @Bean(name = "autoDeleteQueue1")
        public Queue autuDeleteQueue() {
            return new AnonymousQueue();
        }

        @Bean
        public Binding binding2(FanoutExchange fanoutExchange, @Qualifier("autoDeleteQueue1") Queue autoDeleteQueue) {
            return BindingBuilder.bind(autoDeleteQueue).to(fanoutExchange);
        }

        @Bean
        public Tutorial3Receiver receiver() {
            return new Tutorial3Receiver();
        }
    }

    @Profile("header")
    private static class ReceiverConfig5 {

        @Bean
        public HeadersExchange headersExchange() {
            return new HeadersExchange("tut.header");
        }

        @Bean
        public HeaderSender headerSender() {
            return new HeaderSender();
        }

        @Bean(name = "autoDeleteQueue1")
        public Queue autoDeleteQueue1() {
            return new AnonymousQueue();
        }

        @Bean
        public Binding binding1(HeadersExchange headersExchange, @Qualifier("autoDeleteQueue1") Queue autoDeleteQueue1) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("color", "red");
//            return BindingBuilder.bind(autoDeleteQueue1).to(headersExchange).where("color").matches("red");
//            return BindingBuilder.bind(autoDeleteQueue1).to(headersExchange).whereAll("color").exist();
            return BindingBuilder.bind(autoDeleteQueue1).to(headersExchange).whereAny(map).match();
        }

        @Bean
        public HeaderReceiver headerReceiver() {
            return new HeaderReceiver();
        }
    }

    @Profile("topics")
    private static class ReceiverConfig4 {

        @Bean
        public TopicExchange topic() {
            return new TopicExchange("tut.topic");
        }

        @Bean
        public Tutorial4Sender sender4() {
            return new Tutorial4Sender();
        }

        //匿名队列
        @Bean(name = "autoDeleteQueue1")
        public Queue autoDeleteQueue1() {
            return new AnonymousQueue();
        }

        @Bean(name = "autoDeleteQueue2")
        public Queue autuDeleteQueue2() {
            return new AnonymousQueue();
        }

        @Bean
        public Binding binding1(TopicExchange topicExchange, @Qualifier("autoDeleteQueue1") Queue autoDeleteQueue1) {
            return BindingBuilder.bind(autoDeleteQueue1)
                    .to(topicExchange).with("*.orange.*");
        }

        @Bean
        public Binding binding2(TopicExchange topicExchange, @Qualifier("autoDeleteQueue2") Queue autoDeleteQueue2) {
            return BindingBuilder.bind(autoDeleteQueue2)
                    .to(topicExchange).with("*.*.rabbit");
        }

        @Bean
        public Binding binding3(TopicExchange topicExchange, @Qualifier("autoDeleteQueue2") Queue autoDeleteQueue2) {
            return BindingBuilder.bind(autoDeleteQueue2)
                    .to(topicExchange).with("lazy.#");
        }

        @Bean
        public Tutorial4Receiver receiver() {
            return new Tutorial4Receiver();
        }
    }


    @Profile("rpc")
    private static class ClientConfig {

        @Bean
        public DirectExchange exchange() {
            return new DirectExchange("tut.rpc");
        }

        @Bean
        public TutorialClient client() {
            return new TutorialClient();
        }
    }

    @Profile("rpc")
    private static class ServerConfig {

        @Bean
        public Queue queue() {
            return new Queue("tut.rpc.requests");
        }

        @Bean
        public DirectExchange exchange() {
            return new DirectExchange("tut.rpc");
        }

        @Bean
        public Binding binding(DirectExchange exchange, Queue queue) {
            return BindingBuilder.bind(queue)
                    .to(exchange).with("rpc");
        }

        @Bean
        public TutorialServer server() {
            return new TutorialServer();
        }
    }
}
