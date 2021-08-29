package com.example.springboot_kafka;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class Producer {

	private static final Logger logger = LoggerFactory.getLogger(Producer.class);
	// private static final String TOPIC = "users";
	@Value("${topic.name.producer}")
	private String TOPIC;

	@Autowired
	private KafkaTemplate<String, User> template;

	public void sendMessage(User user) {
		logger.info(String.format("#### -> Producing message -> %s", user.toString()));
		ProducerRecord<String, User> record = new ProducerRecord<String, User>(TOPIC, user);
		this.template.send(record);
	}

}
