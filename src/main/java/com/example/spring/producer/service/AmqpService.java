package com.example.spring.producer.service;

import com.example.spring.producer.dto.Message;

public interface AmqpService {
    void sendToConsumer(Message message);
}
