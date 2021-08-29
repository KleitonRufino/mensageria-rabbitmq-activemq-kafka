package com.example.spring.producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringProducerApplication {
		/**
		 * POST http://localhost:8081/send
		 * body { "text": "text" }
		 * body to get error { "text": "error" }
		 * */
	public static void main(String[] args) {
		SpringApplication.run(SpringProducerApplication.class, args);
	}

}
