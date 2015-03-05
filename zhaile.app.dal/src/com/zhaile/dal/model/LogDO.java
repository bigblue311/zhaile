package com.zhaile.dal.model;

import java.io.Serializable;
import java.util.Date;

import com.victor.framework.dal.basic.EntityDO;
import com.zhaile.dal.enumerate.LogOpEnum;

public class LogDO extends EntityDO implements Serializable{
	private static final long serialVersionUID = -4563535323567146203L;
	private Long customerId;	//客户ID
	private Date opTime;		//操作日期
	private String opType;		//操作类型
	
	public void translate(){
		this.opType = LogOpEnum.getByCode(this.opType)==null?this.opType:LogOpEnum.getByCode(this.opType).getDesc();
	}
	
	public void format(){
		this.opType = LogOpEnum.getByDesc(this.opType)==null?this.opType:LogOpEnum.getByDesc(this.opType).getCode();
	}
	
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public Date getOpTime() {
		return opTime;
	}
	public void setOpTime(Date opTime) {
		this.opTime = opTime;
	}
	public String getOpType() {
		return opType;
	}
	public void setOpType(String opType) {
		this.opType = opType;
	}
}
