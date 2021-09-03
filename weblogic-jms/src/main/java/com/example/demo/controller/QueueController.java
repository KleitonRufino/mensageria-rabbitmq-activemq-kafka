package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.CustomMsg;
import com.example.demo.jms.ProducerJMS;

@RestController
@RequestMapping("/queue")
public class QueueController {

	@Autowired
	private ProducerJMS producerJMS;
	
	@PostMapping
	public String postMessage(@RequestBody CustomMsg customMsg) {
		producerJMS.sendMessage(customMsg);
		return "Message Sent!";
	}

}
