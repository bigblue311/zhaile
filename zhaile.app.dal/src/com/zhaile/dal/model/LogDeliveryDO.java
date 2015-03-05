package com.zhaile.dal.model;

import java.io.Serializable;

import com.victor.framework.dal.basic.EntityDO;
import com.zhaile.dal.enumerate.DeliveryStatusEnum;

public class LogDeliveryDO extends EntityDO implements Serializable {
	private static final long serialVersionUID = 6175574160998780685L;
	private Long logId;
	private String deliveryStatus;
	private Long paymentId;
	
	public void translate(){
		this.deliveryStatus = DeliveryStatusEnum.getByCode(this.deliveryStatus)==null?this.deliveryStatus:DeliveryStatusEnum.getByCode(this.deliveryStatus).getDesc();
	}
	
	public void format(){
		this.deliveryStatus = DeliveryStatusEnum.getByDesc(this.deliveryStatus)==null?this.deliveryStatus:DeliveryStatusEnum.getByDesc(this.deliveryStatus).getCode();
	}
	
	public Long getLogId() {
		return logId;
	}
	public void setLogId(Long logId) {
		this.logId = logId;
	}
	public String getDeliveryStatus() {
		return deliveryStatus;
	}
	public void setDeliveryStatus(String deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}
	public Long getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}
}
