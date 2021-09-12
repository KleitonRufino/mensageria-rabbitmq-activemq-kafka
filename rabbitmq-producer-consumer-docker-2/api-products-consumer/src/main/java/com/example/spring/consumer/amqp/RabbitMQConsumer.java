package com.example.spring.consumer.amqp;

import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.spring.consumer.dto.MessageQueue;
import com.example.spring.consumer.service.ConsumerService;

@Component
public class RabbitMQConsumer {

	@Autowired
	private ConsumerService consumerService;

	@RabbitListener(queues = "${spring.rabbitmq.request.routing-key.producer}")
	public void consumer(MessageQueue message) {
		try {
			consumerService.action(message);
		} catch (Exception ex) {
			throw new AmqpRejectAndDontRequeueException(ex);
		}
	}
}
