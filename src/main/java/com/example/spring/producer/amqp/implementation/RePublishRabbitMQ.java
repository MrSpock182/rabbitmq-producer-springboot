package com.example.spring.producer.amqp.implementation;

import com.example.spring.producer.amqp.AmqpConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class RePublishRabbitMQ implements AmqpConsumer<Message> {

    private Logger logger = LoggerFactory.getLogger(RePublishRabbitMQ.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.request.exchenge.producer}")
    private String exchange;

    @Value("${spring.rabbitmq.request.routing-key.producer}")
    private String queue;

    @Value("${spring.rabbitmq.request.parking-lot.producer}")
    private String parkingLot;

    private static final String X_RETRIES_HEADER = "x-retries";


    @Override
    @RabbitListener(queues = "${spring.rabbitmq.request.dead-letter.producer}")
    public void consumer(Message message) {
        Map<String, Object> headers = message.getMessageProperties().getHeaders();
        Integer retriesHeader = (Integer) headers.get(X_RETRIES_HEADER);

        if (retriesHeader == null) {
            retriesHeader = 0;
        }

        if (retriesHeader < 3) {
            headers.put(X_RETRIES_HEADER, retriesHeader + 1);
            this.rabbitTemplate.send(exchange, queue, message);

            System.out.println("Tentou:" + retriesHeader);
        } else {
            this.rabbitTemplate.send(parkingLot, message);
            System.out.println("Enviou para parkinglot");
        }
    }

}
