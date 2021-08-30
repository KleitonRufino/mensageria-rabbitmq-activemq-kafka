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

@Component
@Slf4j
public class RedeliveryListener {

  
    @JmsListener(destination = JmsConfig.MY_QUEUE_REDELIVERY)
    public void onMessage(Message message,
                          Session session,
                          @Header(name = "JMSXDeliveryCount", defaultValue = "1") String redeliveryCount,
                          @Header(name = JmsHeaders.MESSAGE_ID, defaultValue = "1") String messageId) throws JMSException {
        try {
            String msg = ((TextMessage) message).getText();
            log.info("Recebendo mensagem {} na queue {} [RedeliveryCount={}, MessageID={}]", msg, JmsConfig.MY_QUEUE_REDELIVERY, redeliveryCount, messageId);

            if (message != null && msg.startsWith("error-")) {
                throw new Exception("Forcando problemas de leitura de mensagem jms para mensagem lida na queue " + JmsConfig.MY_QUEUE_REDELIVERY);
            }

            session.commit();
        } catch (Exception e) {
            log.error("Problemas para consumir mensagem na queue {}.", JmsConfig.MY_QUEUE_REDELIVERY);
            session.rollback();
        }
    }
    
}