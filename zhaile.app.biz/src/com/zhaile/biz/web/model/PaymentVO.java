package com.zhaile.biz.web.model;

import java.io.Serializable;
import java.util.List;

import com.zhaile.dal.model.PaymentDO;

public class PaymentVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5478243059164331589L;
	private PaymentDO payment;
	private CustomerVO customer;
	private List<OrderVO> orderList;
	
	public PaymentDO getPayment() {
		return payment;
	}
	public void setPayment(PaymentDO payment) {
		this.payment = payment;
	}
	public CustomerVO getCustomer() {
		return customer;
	}
	public void setCustomer(CustomerVO customer) {
		this.customer = customer;
	}
	public List<OrderVO> getOrderList() {
		return orderList;
	}
	public void setOrderList(List<OrderVO> orderList) {
		this.orderList = orderList;
	}
}
