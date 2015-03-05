package com.zhaile.biz.web.model;

import java.util.Date;

import com.zhaile.dal.model.CouponCardDO;
import com.zhaile.dal.model.CouponMetaDO;

public class CouponCardVO {
	private CouponCardDO couponCard;
	private CouponMetaDO couponMeta;
	private CustomerVO customer;
	private Date validFrom;
	private Date validTo;
	public CouponMetaDO getCouponMeta() {
		return couponMeta;
	}
	public void setCouponMeta(CouponMetaDO couponMeta) {
		this.couponMeta = couponMeta;
	}
	public CouponCardDO getCouponCard() {
		return couponCard;
	}
	public void setCouponCard(CouponCardDO couponCard) {
		this.couponCard = couponCard;
	}
	public CustomerVO getCustomer() {
		return customer;
	}
	public void setCustomer(CustomerVO customer) {
		this.customer = customer;
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
}
