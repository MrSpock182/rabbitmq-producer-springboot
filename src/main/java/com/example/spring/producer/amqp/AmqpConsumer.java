package com.example.spring.producer.amqp;

public interface AmqpConsumer<T> {
    void consumer(T t);
}
