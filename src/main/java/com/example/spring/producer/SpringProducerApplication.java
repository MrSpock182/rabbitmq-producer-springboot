package com.example.spring.producer;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@EnableRabbit
@SpringBootApplication
@ComponentScan(value="com.example.spring")
public class SpringProducerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringProducerApplication.class, args);
	}

}
