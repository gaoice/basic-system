package com.gaoice.system.common.rabbit;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 定义 rabbit 的 queue 和 exchange
 */
@Configuration
public class RabbitConfiguration {

    @Bean
    public Queue test1Queue() {
        return new Queue("test1Queue");
    }

    @Bean
    public Queue test2Queue() {
        return new Queue("test2Queue");
    }

    /**
     * 不同的队列通过绑定 FanoutExchange，实现接受相同的消息
     */
    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange("fanoutExchange");
    }

    @Bean
    Binding bindingTestQueue(@Qualifier("test1Queue") Queue test1Queue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(test1Queue).to(fanoutExchange);
    }

    @Bean
    Binding bindingTest2Queue(@Qualifier("test2Queue") Queue test2Queue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(test2Queue).to(fanoutExchange);
    }

    /**
     * 不同的队列通过绑定 TopicExchange，实现 routingKey 匹配接收消息
     * "*" 匹配一个单词，a.* 匹配 a.b 而不匹配 a.b.c
     * "#" 匹配零个或多个的单词，a.# 匹配 a , a.b , a.b.c 等以此类推
     */
    @Bean
    TopicExchange topicExchange() {
        return new TopicExchange("topicExchange");
    }

    @Bean
    Binding bindingTestQueueToTopic(@Qualifier("test1Queue") Queue test1Queue, TopicExchange topicExchange) {
        return BindingBuilder.bind(test1Queue).to(topicExchange).with("test1.#");
    }

    @Bean
    Binding bindingTest2QueueToTopic(@Qualifier("test2Queue") Queue test2Queue, TopicExchange topicExchange) {
        return BindingBuilder.bind(test2Queue).to(topicExchange).with("test2.#");
    }

}
