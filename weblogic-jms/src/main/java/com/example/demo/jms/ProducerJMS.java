package com.example.demo.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import com.example.demo.dto.CustomMsg;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ProducerJMS {
	
	@Autowired
	private JmsTemplate jmsTemplate;

	@Value("${spring.jms.template.default-destination}")
	private String queue;

	public void sendMessage(CustomMsg msg) {
		log.info(String.format("#### -> PRODUZINDO MENSAGEM -> %s", msg));
		jmsTemplate.convertAndSend(queue, msg);
	}
}
