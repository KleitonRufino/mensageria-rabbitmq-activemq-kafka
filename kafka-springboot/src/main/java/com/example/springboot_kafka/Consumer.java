package com.example.springboot_kafka;

import java.io.IOException;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

	private final Logger logger = LoggerFactory.getLogger(Producer.class);
	

	@KafkaListener(topics = "${topic.name.consumer}", groupId = "${group.name.consumer}")
	public void consume(ConsumerRecord<String, String> record) throws IOException {
		logger.info("Tópico: " + record.topic());
		logger.info("key: " + record.key());
		logger.info("Headers: " + record.headers());
		logger.info("Partion: " + record.partition());
		logger.info(String.format("#### -> Consumed message -> %s", record.value()));
	}
}
