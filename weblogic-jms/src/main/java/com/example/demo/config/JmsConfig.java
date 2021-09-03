package com.example.demo.config;

import java.util.Properties;

import javax.jms.ConnectionFactory;
import javax.jms.QueueConnectionFactory;
import javax.naming.Context;
import javax.naming.NamingException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.jms.support.destination.JndiDestinationResolver;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.jndi.JndiTemplate;

@Configuration
public class JmsConfig {
	
	@Value("${spring.jms.template.default-destination}")
	private String queue;
	
	@Value("${spring.jms.jndi-name}")
	private String connectionFactory;
	
	@Value("${spring.jms.broker-url}")
	private String brokerUrl;

	@Value("${spring.jms.context-factory}")
	private String contextFactory;
	
	@Bean
	public MessageConverter jacksonJmsMessageConverter() {
		MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		converter.setTargetType(MessageType.TEXT);
		converter.setTypeIdPropertyName("_type");
		return converter;
	}

	@Bean
	public ConnectionFactory connectionFactory() throws NamingException {
		JndiObjectFactoryBean jndiObjectFactoryBean = new JndiObjectFactoryBean();
		jndiObjectFactoryBean.setJndiName(connectionFactory);

		jndiObjectFactoryBean.setJndiEnvironment(getEnvProperties());
		jndiObjectFactoryBean.afterPropertiesSet();

		return (QueueConnectionFactory) jndiObjectFactoryBean.getObject();
	}

	@Bean
	public JmsTemplate jmsTemplate() throws NamingException {
		JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory());
		jmsTemplate.setDestinationResolver(jmsDestinationResolver());
		return jmsTemplate;
	}
	
	@Bean
	public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(ConnectionFactory connectionFactory) {

		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory);
		factory.setSessionTransacted(true);
		JndiDestinationResolver jndiDestinationResolver = new JndiDestinationResolver();

		jndiDestinationResolver.setJndiEnvironment(getEnvProperties());
		factory.setDestinationResolver(jndiDestinationResolver);
		return factory;
	}

	@Bean
	public JndiTemplate jndiTemplate() {
		JndiTemplate jndiTemplate = new JndiTemplate();
		jndiTemplate.setEnvironment(getEnvProperties());
		return jndiTemplate;
	}

	@Bean
	public JndiDestinationResolver jmsDestinationResolver() {
		JndiDestinationResolver destResolver = new JndiDestinationResolver();
		destResolver.setJndiTemplate(jndiTemplate());
		destResolver.setCache(true);
		return destResolver;
	}

	@Bean
	public JndiObjectFactoryBean queue() {
		JndiObjectFactoryBean jmsQueue = new JndiObjectFactoryBean();
		jmsQueue.setJndiTemplate(jndiTemplate());
		jmsQueue.setJndiName(queue);
		return jmsQueue;
	}

	Properties getEnvProperties() {
		Properties env = new Properties();
		env.put(Context.INITIAL_CONTEXT_FACTORY, contextFactory);
		env.put(Context.PROVIDER_URL, brokerUrl);
		return env;
	}

}
