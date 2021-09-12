package com.example.spring.consumer.dto;

import java.io.Serializable;

public class MessageQueue implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1886682895618568415L;
	private ProductVO productVO;
	private Action action;

	public MessageQueue() {
		// TODO Auto-generated constructor stub
	}

	public MessageQueue(ProductVO productVO, Action action) {
		super();
		this.productVO = productVO;
		this.action = action;
	}

	public ProductVO getProductVO() {
		return productVO;
	}

	public void setProductVO(ProductVO productVO) {
		this.productVO = productVO;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	@Override
	public String toString() {
		return "MessageQueue [productVO=" + productVO + ", action=" + action + "]";
	}

}
