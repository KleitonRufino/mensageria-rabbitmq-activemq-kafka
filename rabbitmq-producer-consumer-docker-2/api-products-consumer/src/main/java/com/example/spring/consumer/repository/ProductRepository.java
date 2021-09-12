package com.example.spring.consumer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.spring.consumer.model.Product;

public interface ProductRepository extends JpaRepository<Product, String> {

}
