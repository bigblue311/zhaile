package com.zhaile.dal.model;

import java.io.Serializable;

import com.victor.framework.dal.basic.EntityDO;

public class CouponDO extends EntityDO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3996770664364155501L;
	private Long paymentId;			//支付ID
	private Long couponCardId;		//卡的ID
	private Long customerId;		//客户ID
	private Long shopId;			//店铺ID
	private Long logId;				//行为日志ID
	private Double income;			//收入
	private Double outcome;			//支出
	
	public Long getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}
	public Long getCouponCardId() {
		return couponCardId;
	}
	public void setCouponCardId(Long couponCardId) {
		this.couponCardId = couponCardId;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public Long getShopId() {
		return shopId;
	}
	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}
	public Long getLogId() {
		return logId;
	}
	public void setLogId(Long logId) {
		this.logId = logId;
	}
	public Double getIncome() {
		return income;
	}
	public void setIncome(Double income) {
		this.income = income;
	}
	public Double getOutcome() {
		return outcome;
	}
	public void setOutcome(Double outcome) {
		this.outcome = outcome;
	}
}
