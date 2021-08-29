package com.example.spring.consumer.dto;

import com.example.spring.consumer.model.Notification;

public class MessageQueue {

	private String subscription;
	private String notification_type;

	public String getSubscription() {
		return subscription;
	}

	public void setSubscription(String subscription) {
		this.subscription = subscription;
	}

	public String getNotification_type() {
		return notification_type;
	}

	public void setNotification_type(String notification_type) {
		this.notification_type = notification_type;
	}

	public Notification getNotification() {
		return Notification.valueOf(notification_type);
	}

	@Override
	public String toString() {
		return "MessageQueue [subscription=" + subscription + ", notification_type=" + notification_type + "]";
	}

}
