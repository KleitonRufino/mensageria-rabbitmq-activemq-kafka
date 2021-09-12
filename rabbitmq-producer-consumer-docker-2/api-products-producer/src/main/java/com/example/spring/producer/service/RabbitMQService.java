package com.example.spring.producer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.spring.producer.amqp.ProducerRabbitMQ;
import com.example.spring.producer.dto.MessageQueue;

@Service
public class RabbitMQService {

    @Autowired
    private ProducerRabbitMQ amqp;

    public void sendToConsumer(MessageQueue message) {
    	System.out.println("PRODUZINDO MSG: " + message);
        amqp.producer(message);
    }
}
