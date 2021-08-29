package com.example.spring.consumer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.spring.consumer.model.EventHistory;

public interface EventHistoryRepository extends JpaRepository<EventHistory, Integer>{

}
