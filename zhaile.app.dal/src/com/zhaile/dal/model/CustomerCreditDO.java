package com.zhaile.dal.model;

import java.io.Serializable;

import com.victor.framework.dal.basic.EntityDO;

public class CustomerCreditDO extends EntityDO implements Serializable {
	private static final long serialVersionUID = -2235679828141362331L;
	private Long customerId;		//客户ID
	private Integer creditPoints;	//客户积分
	
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public Integer getCreditPoints() {
		return creditPoints;
	}
	public void setCreditPoints(Integer creditPoints) {
		this.creditPoints = creditPoints;
	}
	
	
}
