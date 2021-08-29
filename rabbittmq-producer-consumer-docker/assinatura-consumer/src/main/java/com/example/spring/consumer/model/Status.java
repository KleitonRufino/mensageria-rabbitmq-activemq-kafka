package com.example.spring.consumer.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "status")
public class Status {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "status", nullable = false)
	@Enumerated(EnumType.STRING)
	private StatusEnum status;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public StatusEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Status [id=" + id + ", status=" + status + "]";
	}

}
