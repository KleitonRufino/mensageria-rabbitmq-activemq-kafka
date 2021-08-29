package com.example.spring.consumer.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.spring.consumer.dto.MessageQueue;
import com.example.spring.consumer.service.ConsumerService;

@Service
public class ConsumerServiceImpl implements ConsumerService {

	@Autowired
	private AssinaturaService assinaturaService;
	
    @Override
    public void action(MessageQueue message) {
        System.out.println("CONSUMINDO MSG: " + message.toString());
        assinaturaService.gerenciarAssinatura(message);
    }
}
