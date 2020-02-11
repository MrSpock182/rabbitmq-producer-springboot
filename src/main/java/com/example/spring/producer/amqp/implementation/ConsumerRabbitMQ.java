package com.example.spring.producer.amqp.implementation;

import com.example.spring.producer.amqp.AmqpConsumer;
import com.example.spring.producer.dto.Message;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ConsumerRabbitMQ implements AmqpConsumer<Message> {

    @Override
    @RabbitListener(queues = "${spring.rabbitmq.request.routing-key.producer}")
    public void consumer(Message message) {
        if("teste".equalsIgnoreCase(message.getText())) {
            throw new AmqpRejectAndDontRequeueException("Erro");
        }

        System.out.println("Consumiu: " + message.getText());
    }

}
