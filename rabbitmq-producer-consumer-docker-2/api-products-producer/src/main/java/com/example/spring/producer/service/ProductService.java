package com.example.spring.producer.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.spring.producer.dto.Action;
import com.example.spring.producer.dto.MessageQueue;
import com.example.spring.producer.dto.ProductVO;
import com.example.spring.producer.exception.ResourceEmptyException;
import com.example.spring.producer.exception.ResourceNotFoundException;
import com.example.spring.producer.model.Product;
import com.example.spring.producer.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository repository;
	@Autowired
	private RabbitMQService rabbitMQService;

	public void insert(ProductVO vo) {
		if (vo.getName() == null || vo.getName().isBlank() || vo.getDescription() == null
				|| vo.getDescription().isBlank() || vo.getPrice() == null)
			throw new ResourceEmptyException("Preencha os valores obrigatorios: name, description e price");
		this.rabbitMQService.sendToConsumer(new MessageQueue(vo, Action.SAVE));
	}

	public void put(String id, ProductVO vo) {
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Nenhum produto encontrado"));
		vo.setId(entity.getId());
		insert(vo);
	}

	public List<ProductVO> findAll() {
		var products = repository.findAll();
		return products.stream().map(o -> getVO(o)).collect(Collectors.toList());
	}

	public List<ProductVO> findAll(String q, BigDecimal minPrice, BigDecimal maxPrice) {
		if (q != null && !q.isBlank() && minPrice != null && maxPrice != null) {
			var products = repository.finByNameOrDescriptionAndMinPriceAndMaxPrice(q.toUpperCase(), minPrice, maxPrice);
			return products.stream().map(o -> getVO(o)).collect(Collectors.toList());
		} else if (q != null && !q.isBlank() && minPrice != null && maxPrice == null) {
			var products = repository.finByNameOrDescriptionAndMinPrice(q.toUpperCase(), minPrice);
			return products.stream().map(o -> getVO(o)).collect(Collectors.toList());
		} else if (q != null && !q.isBlank() && minPrice == null && maxPrice != null) {
			var products = repository.finByNameOrDescriptionAndMaxPrice(q.toUpperCase(), maxPrice);
			return products.stream().map(o -> getVO(o)).collect(Collectors.toList());
		} else if (q != null && !q.isBlank() && minPrice == null && maxPrice == null) {
			var products = repository.findByNameOrDescription(q.toUpperCase());
			return products.stream().map(o -> getVO(o)).collect(Collectors.toList());
		} else if ((q == null || q.isBlank()) && minPrice != null && maxPrice != null) {
			var products = repository.finByMinPriceAndMaxPrice(minPrice, maxPrice);
			return products.stream().map(o -> getVO(o)).collect(Collectors.toList());
		} else if ((q == null || q.isBlank()) && minPrice != null && maxPrice == null) {
			var products = repository.finByMinPrice(minPrice);
			return products.stream().map(o -> getVO(o)).collect(Collectors.toList());
		} else if ((q == null || q.isBlank()) && minPrice == null && maxPrice != null) {
			var products = repository.finByMaxPrice(maxPrice);
			return products.stream().map(o -> getVO(o)).collect(Collectors.toList());
		}
		return new ArrayList<ProductVO>();
	}

	public ProductVO findById(String id) {
		var model = repository.findById(id);
		if (!model.isPresent())
			throw new ResourceNotFoundException("Nenhum produto encontrado");
		return getVO(model.get());
	}

	public void delete(String id) {
		var model = repository.findById(id);
		if (!model.isPresent())
			throw new ResourceNotFoundException("Nenhum produto encontrado");
		ProductVO vo = getVO(model.get());
		this.rabbitMQService.sendToConsumer(new MessageQueue(vo, Action.DELETE));
	}

	public static ProductVO getVO(Product model) {
		ProductVO vo = new ProductVO();
		vo.setId(model.getId());
		vo.setName(model.getName());
		vo.setDescription(model.getDescription());
		vo.setPrice(model.getPrice());
		return vo;
	}
}
