package com.example.spring.consumer.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring.consumer.dto.EventHistoryDTO;
import com.example.spring.consumer.dto.SubscriptionDTO;
import com.example.spring.consumer.service.implementation.AssinaturaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Assinatura Consumer endpoint")
@RestController
@RequestMapping("/assinatura")
public class AssinaturaController {


	@Autowired
	private AssinaturaService assinaturaService;
	
	@Operation(summary = "Find all subscriptions")
	@GetMapping
	public List<SubscriptionDTO> findAllSubscription() {
		return assinaturaService.findAllSubscription();
	}

	@Operation(summary = "Find all historics")
	@GetMapping("/historic")
	public List<EventHistoryDTO> findAllHistoric() {
		return assinaturaService.findAllHistoric();
	}

}
