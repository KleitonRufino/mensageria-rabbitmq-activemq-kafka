package com.example.spring.consumer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.spring.consumer.dto.Action;
import com.example.spring.consumer.dto.MessageQueue;
import com.example.spring.consumer.dto.ProductVO;
import com.example.spring.consumer.model.Product;
import com.example.spring.consumer.repository.ProductRepository;

@Service
public class ConsumerService {

	@Autowired
	private ProductRepository productRepository;
	
    public void action(MessageQueue message) {
        System.out.println("CONSUMINDO MSG: " + message.toString());
        if(Action.SAVE.equals(message.getAction()) && message.getProductVO() != null)
        	this.productRepository.save(getModel(message.getProductVO()));
        else if(Action.DELETE.equals(message.getAction()) && message.getProductVO() != null && message.getProductVO().getId() != null)
        	this.productRepository.deleteById(message.getProductVO().getId());
    }
    
	private Product getModel(ProductVO vo) {
		Product model = new Product();
		model.setId(vo.getId());
		model.setName(vo.getName());
		model.setDescription(vo.getDescription());
		model.setPrice(vo.getPrice());
		return model;
	}
}
