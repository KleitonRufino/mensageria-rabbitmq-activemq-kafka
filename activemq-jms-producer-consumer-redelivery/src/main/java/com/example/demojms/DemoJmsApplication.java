package com.example.demojms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class DemoJmsApplication {

	/*
	 * docker run -it --rm -p 8161:8161 -p 61616:61616 vromero/activemq-artemis
	 * http://localhost:8161/ login: artemis - simetraehcapa
	 * POST http://localhost:8080/send?message=msg
	 */
	public static void main(String[] args) {
		SpringApplication.run(DemoJmsApplication.class, args);
	}

	@Autowired
	private ActiveMQSender mqSender;

	@RequestMapping(value = "/send", method = RequestMethod.POST)
	public String query(@RequestParam("message") String message) {
		try {
			mqSender.sendMessage("queue.sample", message);
			return "OK";
		} catch (Exception e) {
			return "NOK";
		}
	}

}
