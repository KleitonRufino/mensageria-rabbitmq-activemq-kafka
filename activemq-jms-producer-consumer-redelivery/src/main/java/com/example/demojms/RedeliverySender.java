package com.example.demojms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.demojms.config.JmsConfig;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by jt on 2019-07-17.
 */
@RequiredArgsConstructor
@Slf4j
@Component
public class RedeliverySender {

	private final JmsTemplate jmsTemplate;
	
	@Scheduled(fixedRate = 10000)
	public void sendMessageError() {

		log.info("Enviando um mensagem de erro");
		
		String msg = "error-";
		jmsTemplate.send(JmsConfig.MY_QUEUE_REDELIVERY, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(msg);
            }
        });

		log.info("Mensagem enviada!");

	}

	
}
