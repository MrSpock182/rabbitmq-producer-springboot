package com.example.spring.producer.service;

import com.example.spring.producer.dto.MessageQueue;

public interface AmqpService {
    void sendToConsumer(MessageQueue message);
}
