package com.example.spring.producer.api;

import com.example.spring.producer.dto.Message;
import com.example.spring.producer.service.AmqpService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class AmqpApi {

    @Autowired
    private AmqpService service;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.request.routing-key.producer}")
    private String queue;

    @Value("${spring.rabbitmq.request.exchenge.producer}")
    private String exchange;

    @Value("${spring.rabbitmq.request.parking-lot.producer}")
    private String parkingLot;

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("/send")
    public void sendToConsumer(@RequestBody Message message) {
        service.sendToConsumer(message);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/test")
    public void getToConsumer() {
        Object object = rabbitTemplate.receiveAndConvert(parkingLot);
        rabbitTemplate.convertAndSend(exchange, queue, object);

        System.out.println("Realizando teste");
    }

}
