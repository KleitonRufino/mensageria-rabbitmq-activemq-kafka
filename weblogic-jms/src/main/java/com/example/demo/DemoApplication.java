package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication  {

	/**
	 * POST http://localhost:8080/queue { "id": 22, "message":"Message" }
	 * 
	 */
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	
}
