package com.zhaile.biz.web.model;

import java.io.Serializable;

import com.zhaile.dal.model.EmployeeDO;
import com.zhaile.dal.model.OrderDO;
import com.zhaile.dal.model.PaymentDO;

public class OrderVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7720212540207504429L;
	private PaymentDO payment;
	private OrderDO order;
	private CustomerVO customer;
	private ProductVO prod;
	private EmployeeDO employee;
	
	public PaymentDO getPayment() {
		return payment;
	}
	public void setPayment(PaymentDO payment) {
		this.payment = payment;
	}
	public OrderDO getOrder() {
		return order;
	}
	public void setOrder(OrderDO order) {
		this.order = order;
	}
	public ProductVO getProd() {
		return prod;
	}
	public void setProd(ProductVO prod) {
		this.prod = prod;
	}
	public CustomerVO getCustomer() {
		return customer;
	}
	public void setCustomer(CustomerVO customer) {
		this.customer = customer;
	}
	public EmployeeDO getEmployee() {
		return employee;
	}
	public void setEmployee(EmployeeDO employee) {
		this.employee = employee;
	}
}
