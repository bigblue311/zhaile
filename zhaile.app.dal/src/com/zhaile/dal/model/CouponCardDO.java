package com.zhaile.dal.model;

import java.io.Serializable;
import java.util.Date;

import com.victor.framework.dal.basic.EntityDO;

public class CouponCardDO extends EntityDO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -118388717794497117L;
	private String enable;		//是否有效 0：有效1：无效
	private String locked;		//是否锁定 0：是 1： 否
	private Date validFrom;		//有效期开始
	private Date validTo;		//有效期结束
	private Long metaId;		//发卡序列
	private Long customerId;	//客户ID
	private Double balance;		//余额
	private Integer countDown;	//计数
	private String cardId;		//实体卡ID
	public String getEnable() {
		return enable;
	}
	public void setEnable(String enable) {
		this.enable = enable;
	}
	public Date getValidFrom() {
		return validFrom;
	}
	public void setValidFrom(Date validFrom) {
		this.validFrom = validFrom;
	}
	public Date getValidTo() {
		return validTo;
	}
	public void setValidTo(Date validTo) {
		this.validTo = validTo;
	}
	public Long getMetaId() {
		return metaId;
	}
	public void setMetaId(Long metaId) {
		this.metaId = metaId;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	public String getCardId() {
		return cardId;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	public String getLocked() {
		return locked;
	}
	public void setLocked(String locked) {
		this.locked = locked;
	}
	public Integer getCountDown() {
		return countDown;
	}
	public void setCountDown(Integer countDown) {
		this.countDown = countDown;
	}
}
