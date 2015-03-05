package com.zhaile.dal.model;

import java.io.Serializable;

import com.victor.framework.dal.basic.EntityDO;

public class CustomerFavDO extends EntityDO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3004304336997919940L;
	private Long customerId;		//客户ID
	private Long shopId;			//店铺ID
	private Long prodId;			//产品ID
	
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
	public Long getProdId() {
		return prodId;
	}
	public void setProdId(Long prodId) {
		this.prodId = prodId;
	}
}
