package com.example.demojms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.support.JmsHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import com.example.demojms.config.JmsConfig;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class DeadLetterListener {

	
	@JmsListener(destination = JmsConfig.MY_QUEUE_DEADLETTER)
	public void onMessage(Message message, Session session,
			@Header(name = "JMSXDeliveryCount", defaultValue = "1") String redeliveryCount,
			@Header(name = JmsHeaders.MESSAGE_ID, defaultValue = "1") String messageId) throws JMSException {
		String msg = ((TextMessage) message).getText();
		log.info("DEAD LETTER MSG: " + msg);
	}

}