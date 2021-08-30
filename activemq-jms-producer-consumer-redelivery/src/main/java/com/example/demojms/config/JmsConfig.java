package com.example.demojms.config;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;
import org.apache.activemq.broker.region.policy.RedeliveryPolicyMap;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

@Configuration
@EnableJms
public class JmsConfig {

	public static final String MY_QUEUE_REDELIVERY = "my-queue-redelivery";
	public static final String MY_QUEUE_DEADLETTER = "DLQ";

	@Value("${spring.activemq.broker-url}")
	private String brokerUrl;

	@Value("${spring.activemq.user}")
	private String user;

	@Value("${spring.activemq.password}")
	private String password;

	@Bean
	public ActiveMQConnectionFactory connectionFactory() {
		ActiveMQConnectionFactory connectionFactory = null;

		if ("".equals(user)) {
			connectionFactory = new ActiveMQConnectionFactory(brokerUrl);
		} else {
			connectionFactory = new ActiveMQConnectionFactory(user, password, brokerUrl);
		}
		redeliveryPolicy(connectionFactory.getRedeliveryPolicyMap());
		return connectionFactory;
	}

	private void redeliveryPolicy(RedeliveryPolicyMap redeliveryPolicyMap) {
		ActiveMQQueue queue10s = new ActiveMQQueue(JmsConfig.MY_QUEUE_REDELIVERY);
		RedeliveryPolicy qp10Seconds = new RedeliveryPolicy();
		qp10Seconds.setInitialRedeliveryDelay(10000);
		qp10Seconds.setUseCollisionAvoidance(true);
		qp10Seconds.setRedeliveryDelay(10000);
		qp10Seconds.setUseExponentialBackOff(false);
		qp10Seconds.setMaximumRedeliveries(3);
		qp10Seconds.setDestination(queue10s);
		redeliveryPolicyMap.put(queue10s, qp10Seconds);
	}

	@Bean
	public JmsListenerContainerFactory<?> jmsFactoryTopic(ConnectionFactory connectionFactory,
			DefaultJmsListenerContainerFactoryConfigurer configurer) {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		configurer.configure(factory, connectionFactory);
		factory.setPubSubDomain(true);
		return factory;
	}

	@Bean
	public JmsTemplate jmsTemplate() {
		return new JmsTemplate(connectionFactory());
	}

	@Bean
	public JmsTemplate jmsTemplateTopic() {
		JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory());
		jmsTemplate.setPubSubDomain(true);
		return jmsTemplate;
	}
}
