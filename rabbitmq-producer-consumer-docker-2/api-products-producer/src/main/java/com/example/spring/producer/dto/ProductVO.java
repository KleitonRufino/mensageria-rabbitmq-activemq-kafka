package com.example.spring.producer.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import org.springframework.hateoas.RepresentationModel;

public class ProductVO extends RepresentationModel<ProductVO> implements Serializable {

	private static final long serialVersionUID = 4585212527599491027L;
	private String id;
	private String name;
	private String description;
	private BigDecimal price;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "ProductVO [id=" + id + ", name=" + name + ", description=" + description + ", price=" + price + "]";
	}

}