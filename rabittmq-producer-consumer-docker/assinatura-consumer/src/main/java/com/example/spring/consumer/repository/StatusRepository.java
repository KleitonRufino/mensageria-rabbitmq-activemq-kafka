package com.example.spring.consumer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.spring.consumer.model.Status;

public interface StatusRepository extends JpaRepository<Status, Integer>{

}
