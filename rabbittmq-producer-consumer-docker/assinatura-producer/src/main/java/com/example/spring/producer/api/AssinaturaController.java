package com.example.spring.producer.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring.producer.dto.MessageQueue;
import com.example.spring.producer.service.AmqpService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Assinatura producer endpoint")
@RestController
@RequestMapping("/assinatura")
public class AssinaturaController {

	@Autowired
	private AmqpService service;

	@Operation(summary = "Send a subscription")
	@ResponseStatus(HttpStatus.ACCEPTED)
	@PostMapping
	public ResponseEntity<?> sendToConsumer(@RequestBody MessageQueue message) {
		service.sendToConsumer(message);
		return ResponseEntity.ok().build();
	}

}
