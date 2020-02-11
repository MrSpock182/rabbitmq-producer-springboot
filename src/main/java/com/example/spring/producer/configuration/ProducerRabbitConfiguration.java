package com.example.spring.producer.configuration;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ProducerRabbitConfiguration {

    @Value("${spring.rabbitmq.request.exchenge.producer}")
    private String exchange;

    @Value("${spring.rabbitmq.request.routing-key.producer}")
    private String routingKey;

    @Value("${spring.rabbitmq.request.dead-letter.producer}")
    private String deadLetter;

    @Value("${spring.rabbitmq.request.parking-lot.producer}")
    private String parkingLot;

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(exchange);
    }

//    @Bean
//    DirectExchange directExchange() {
//        return new DirectExchange(exchange);
//    }

    @Bean
    Queue queue() {
        return QueueBuilder.durable(routingKey)
                .deadLetterExchange(exchange)
                .deadLetterRoutingKey(deadLetter)
                .build();
    }

    @Bean
    Queue deadLetter() {
        return QueueBuilder.durable(deadLetter)
                .deadLetterExchange(exchange)
                .deadLetterRoutingKey(routingKey)
                .ttl(5000)
                .build();
    }

    @Bean
    Queue parkinglotQueue() {
        return new Queue(parkingLot);
    }

    @Bean
    Binding bindingQueue() {
        return BindingBuilder.bind(queue()).to(exchange()).with(routingKey);
    }

    @Bean
    Binding bindingDeadLetter(){
        return BindingBuilder.bind(deadLetter()).to(exchange()).with(deadLetter);
    }

    @Bean
    Binding bindingParkingLot() {
        return BindingBuilder.bind(parkinglotQueue()).to(exchange()).with(parkingLot);
    }

}
