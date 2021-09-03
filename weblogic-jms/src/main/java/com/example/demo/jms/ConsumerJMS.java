package com.example.demo.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.support.JmsHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import com.example.demo.dto.CustomMsg;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ConsumerJMS {

	@Value("${spring.jms.template.default-destination}")
	private String queue;
	
	@JmsListener(destination = "${spring.jms.template.default-destination}")
	public void listenToMessages(Message message, Session session,
			@Header(name = "JMSXDeliveryCount", defaultValue = "1") String redeliveryCount,
			@Header(name = JmsHeaders.MESSAGE_ID, defaultValue = "1") String messageId) throws JMSException  {
		
		try {
			log.info(String.format("#### -> CONSUMINDO MENSAGEM-> %s", message));
			CustomMsg msg = message.getBody(CustomMsg.class);
			log.info("Recebendo mensagem {} na queue {} [RedeliveryCount={}, MessageID={}]", msg.getMessage(), queue,
					redeliveryCount, messageId);
			if (message != null && msg.getMessage().startsWith("error-")) {
				throw new Exception(
						"Forcando problemas de leitura de mensagem jms para mensagem lida na queue " + queue);
			}
			session.commit();
		} catch (Exception e) {
			session.rollback();
		}
	
	}
}
