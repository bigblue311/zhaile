package com.zhaile.dal.model;

import java.io.Serializable;

import com.victor.framework.dal.basic.EntityDO;
import com.zhaile.dal.enumerate.CreditActionEnum;

public class LogCreditDO extends EntityDO implements Serializable{
	private static final long serialVersionUID = 4265791004959997335L;
	private Long logId;		//日志ID
	private Integer creditPoint;//积分
	private String userAction;	//行为 0-增加 1-减少
	private String userReason;	//原因
	
	public void translate(){
		this.userAction = CreditActionEnum.getByCode(this.userAction)==null?this.userAction:CreditActionEnum.getByCode(this.userAction).getDesc();
	}
	
	public void format(){
		this.userAction = CreditActionEnum.getByDesc(this.userAction)==null?this.userAction:CreditActionEnum.getByDesc(this.userAction).getCode();
	}
	
	public Long getLogId() {
		return logId;
	}
	public void setLogId(Long logId) {
		this.logId = logId;
	}
	public Integer getCreditPoint() {
		return creditPoint;
	}
	public void setCreditPoint(Integer creditPoint) {
		this.creditPoint = creditPoint;
	}

	public String getUserAction() {
		return userAction;
	}

	public void setUserAction(String userAction) {
		this.userAction = userAction;
	}

	public String getUserReason() {
		return userReason;
	}

	public void setUserReason(String userReason) {
		this.userReason = userReason;
	}
}
