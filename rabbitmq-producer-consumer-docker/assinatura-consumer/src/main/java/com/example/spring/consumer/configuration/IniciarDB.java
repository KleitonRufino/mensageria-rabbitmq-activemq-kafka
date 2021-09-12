package com.example.spring.consumer.configuration;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.example.spring.consumer.model.Status;
import com.example.spring.consumer.model.StatusEnum;
import com.example.spring.consumer.repository.StatusRepository;

@Configuration
public class IniciarDB implements CommandLineRunner {

	@Autowired
	private StatusRepository statusRepository;

//	@Autowired
//	private EventHistoryRepository eventHistoryRepository;
//
//	@Autowired
//	private SubscriptionRepository subscriptionRepository;

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub

//		eventHistoryRepository.deleteAll();
//    	subscriptionRepository.deleteAll();
//		statusRepository.deleteAll();
		List<Status> list = statusRepository.findAll();
		if (list == null || list.size() == 0) {
			Status ativo = new Status();
			ativo.setStatus(StatusEnum.ATIVO);
			statusRepository.save(ativo);

			Status cancelado = new Status();
			cancelado.setStatus(StatusEnum.CANCELADO);
			 statusRepository.save(cancelado);
		}
//		Subscription subscription = new Subscription();
//		subscription.setId("1234");
//		subscription.setStatus(ativo);
//		subscription.setCreatedAt(new Date());
//		subscription.setUpdatedAt(new Date());
//		subscriptionRepository.save(subscription);

//		EventHistory eventHistory = new EventHistory();
//		eventHistory.setSubscription(subscription);
//		eventHistory.setCreatedAt(new Date());
//		eventHistory.setType(Notification.SUBSCRIPTION_PURCHASED);
//		eventHistoryRepository.save(eventHistory);

	}

}
