package com.zhaile.biz.web.form;

import com.zhaile.dal.query.condition.SmsTaskQueryCondition;

public class SmsQueryFormBean extends QueryFormBean {
	
	private String mobile;
	private String status;
	private String type;
	
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public SmsTaskQueryCondition getSmsTaskQueryCondition(){
		SmsTaskQueryCondition queryCondition = new SmsTaskQueryCondition();
		queryCondition.gmtModifyStart(modifyStart).gmtModifyEnd(modifyEnd).status(status).mobile(mobile).type(type);
		return queryCondition;
	}
}
