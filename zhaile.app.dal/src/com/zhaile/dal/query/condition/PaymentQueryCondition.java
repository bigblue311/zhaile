package com.zhaile.dal.query.condition;

import java.util.Date;

import com.victor.framework.common.tools.DateTools;
import com.victor.framework.dal.basic.QueryCondition;

public class PaymentQueryCondition extends QueryCondition {
	public PaymentQueryCondition id(Long id) {
		put("id",id);
		return this;
	}
	
	public PaymentQueryCondition customerId(Long customerId) {
		put("customerId",customerId);
		return this;
	}
	
	public PaymentQueryCondition customerName(String customerName) {
		put("customerName",customerName);
		return this;
	}
	
	public PaymentQueryCondition shopName(String shopName) {
		put("shopName",shopName);
		return this;
	}
	
	public PaymentQueryCondition shopId(Long... shopId) {
		if(shopId != null) {
			put("shopId",shopId);
		}
		return this;
	}
	
	public PaymentQueryCondition prodName(String prodName) {
		put("prodName",prodName);
		return this;
	}
	
	public PaymentQueryCondition status(String... status) {
		put("status",status);
		return this;
	}
	
	public PaymentQueryCondition employeeId(Long employeeId){
		put("employeeId",employeeId);
		return this;
	}
	
	public PaymentQueryCondition source(String source){
		put("source",source);
		return this;
	}
	
	public PaymentQueryCondition smsStatus(String smsStatus){
		put("smsStatus",smsStatus);
		return this;
	}
	
	public PaymentQueryCondition smsMobile(String smsMobile){
		put("smsMobile",smsMobile);
		return this;
	}
	
	@Override
	public PaymentQueryCondition gmtModifyStart(Date from){
		put("gmtModifyStart", DateTools.getDayBegin(from));
		return this;
	}
	
	@Override
	public PaymentQueryCondition gmtModifyEnd(Date to){
		put("gmtModifyEnd", DateTools.getDayEnd(to));
		return this;
	}
	
	@Override
	public PaymentQueryCondition start(int start){
		put("start", start);
		return this;
	}
	
	@Override
	public PaymentQueryCondition pageSize(int pageSize){
		put("pageSize", pageSize);
		return this;
	}
}
