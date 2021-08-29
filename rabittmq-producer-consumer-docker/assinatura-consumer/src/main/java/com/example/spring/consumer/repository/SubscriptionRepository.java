package com.example.spring.consumer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.spring.consumer.model.Subscription;

public interface SubscriptionRepository extends JpaRepository<Subscription, String>{

}
