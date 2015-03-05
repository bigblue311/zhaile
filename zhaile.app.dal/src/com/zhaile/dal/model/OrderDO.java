package com.zhaile.dal.model;

import java.io.Serializable;
import com.victor.framework.dal.basic.EntityDO;

public class OrderDO extends EntityDO implements Serializable {
	private static final long serialVersionUID = 6674895132030815209L;
	private Long customerId;	//客户ID
	private Long prodId;		//产品ID
	private Integer quantity;	//数量
	private Double price;		//总价
	private Long paymentId;		//支付ID
	private Long shopId;		//店铺ID
	
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public Long getProdId() {
		return prodId;
	}
	public void setProdId(Long prodId) {
		this.prodId = prodId;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Long getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}
	public Long getShopId() {
		return shopId;
	}
	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}
}
