package com.example.demojms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MessageListenerComponent implements ApplicationRunner {

    private JmsTemplate jmsTemplate;
    private JmsTemplate jmsTemplateTopic;

    @Autowired
    public MessageListenerComponent(JmsTemplate jmsTemplate, JmsTemplate jmsTemplateTopic) {
        this.jmsTemplate = jmsTemplate;
        this.jmsTemplateTopic = jmsTemplateTopic;
    }

    @JmsListener(destination = "queue.sample")
    public void onReceiverQueue(String str) {
        log.info("### -> QUEUE RECEIVING " + str);
    }

    @JmsListener(destination = "topic.sample", containerFactory = "jmsFactoryTopic")
    public void onReceiverTopic(String str) {
    	   log.info("### -> TOPIC RECEIVING " + str);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        jmsTemplate.convertAndSend("queue.sample", "{user: 'wolmir', usando: 'fila'}");
        jmsTemplateTopic.convertAndSend("topic.sample", "{user: 'wolmir', usando: 'tópico'}");
    }

}
