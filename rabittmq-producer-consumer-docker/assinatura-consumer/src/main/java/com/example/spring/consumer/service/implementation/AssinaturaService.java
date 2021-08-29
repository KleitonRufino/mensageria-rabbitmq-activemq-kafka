package com.example.spring.consumer.service.implementation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.spring.consumer.dto.EventHistoryDTO;
import com.example.spring.consumer.dto.MessageQueue;
import com.example.spring.consumer.dto.SubscriptionDTO;
import com.example.spring.consumer.model.EventHistory;
import com.example.spring.consumer.model.Notification;
import com.example.spring.consumer.model.Status;
import com.example.spring.consumer.model.StatusEnum;
import com.example.spring.consumer.model.Subscription;
import com.example.spring.consumer.repository.EventHistoryRepository;
import com.example.spring.consumer.repository.StatusRepository;
import com.example.spring.consumer.repository.SubscriptionRepository;

@Service
public class AssinaturaService {

	@Autowired
	private SubscriptionRepository subscriptionRepository;

	@Autowired
	private EventHistoryRepository eventHistoryRepository;
	
	@Autowired
	private StatusRepository statusRepository;
	
	private int count = 0; 
	
	private List<Status> status = new ArrayList<Status>();
	
	public AssinaturaService() {
		
	}
	
	public List<EventHistoryDTO> findAllHistoric(){
		List<EventHistory> eventHistories = eventHistoryRepository.findAll();
		List<EventHistoryDTO> eventHistoryDTOs = new ArrayList<EventHistoryDTO>();
		if(eventHistories != null && eventHistories.size() > 0) {
			eventHistories.forEach(o -> {
				EventHistoryDTO dto  = new EventHistoryDTO();
				dto.setSubscription(o.getSubscription().getId());
				dto.setNotification(o.getType().toString());
				dto.setCreatedAt(o.getCreatedAt());
				eventHistoryDTOs.add(dto);
			});
		}
		return eventHistoryDTOs;
	}

	public List<SubscriptionDTO> findAllSubscription(){
		List<Subscription> subscriptions = subscriptionRepository.findAll();
		List<SubscriptionDTO> subscriptionDTOs = new ArrayList<SubscriptionDTO>();
		if(subscriptions != null && subscriptions.size() > 0) {
			subscriptions.forEach(o -> {
				SubscriptionDTO dto  = new SubscriptionDTO();
				dto.setSubscription(o.getId());
				dto.setStatus(o.getStatus().getStatus().toString());
				subscriptionDTOs.add(dto);
			});
		}
		return subscriptionDTOs;
	}
	
	private void preencherStatus() {
		if(count == 0) {
			status = statusRepository.findAll();
			count = count++;
		}
		System.out.println("STATUS - " + status.toString());
	}
		
	public void gerenciarAssinatura(MessageQueue message) {
		preencherStatus();
		Subscription subscription = subscriptionRepository.findById(message.getSubscription()).orElse(null);
		if (Notification.SUBSCRIPTION_PURCHASED.equals(message.getNotification()) && subscription == null) {
			
			System.out.println("CRIANDO SUBSCRIPTION: " + message.getSubscription());
			
			
			subscription = new Subscription();
			subscription.setId(message.getSubscription());
			subscription.setStatus(getStatus(message.getNotification()));
			subscription.setCreatedAt(new Date());
			subscription.setUpdatedAt(new Date());
			subscription = subscriptionRepository.save(subscription);
			
			EventHistory eventHistory = new EventHistory();
			eventHistory.setSubscription(subscription);
			eventHistory.setCreatedAt(new Date());
			eventHistory.setType(Notification.SUBSCRIPTION_PURCHASED);
			eventHistoryRepository.save(eventHistory);
			
		} else if (Notification.SUBSCRIPTION_RESTARTED.equals(message.getNotification()) 
				&& subscription != null
				&& subscription.getStatus().getStatus().equals(StatusEnum.CANCELADO) ) {
			
			System.out.println("RESTARTANDO SUBSCRIPTION: " + message.getSubscription());
			
			
			subscription.setStatus(getStatus(message.getNotification()));
			subscription.setUpdatedAt(new Date());
			subscription = subscriptionRepository.save(subscription);
			
			EventHistory eventHistory = new EventHistory();
			eventHistory.setSubscription(subscription);
			eventHistory.setCreatedAt(new Date());
			eventHistory.setType(Notification.SUBSCRIPTION_RESTARTED);
			eventHistoryRepository.save(eventHistory);

		} else if (Notification.SUBSCRIPTION_CANCELED.equals(message.getNotification()) 
				&& subscription != null 
				&& subscription.getStatus().getStatus().equals(StatusEnum.ATIVO)) {

			System.out.println("CANCELANDO SUBSCRIPTION: " + message.getSubscription());
			
			subscription.setStatus(getStatus(message.getNotification()));
			subscription.setUpdatedAt(new Date());
			subscription = subscriptionRepository.save(subscription);
			
			EventHistory eventHistory = new EventHistory();
			eventHistory.setSubscription(subscription);
			eventHistory.setCreatedAt(new Date());
			eventHistory.setType(Notification.SUBSCRIPTION_CANCELED);
			eventHistoryRepository.save(eventHistory);
			
		}else {
			System.out.println("NADA A FAZER");
		}
	}
	
	private Status getStatus(Notification notification) {
		if(Notification.SUBSCRIPTION_PURCHASED.equals(notification) || Notification.SUBSCRIPTION_RESTARTED.equals(notification)) {
			return status.stream().filter(o -> StatusEnum.ATIVO.equals(o.getStatus())).findFirst().get();
		}
		return status.stream().filter(o -> StatusEnum.CANCELADO.equals(o.getStatus())).findFirst().get();
	}

}
