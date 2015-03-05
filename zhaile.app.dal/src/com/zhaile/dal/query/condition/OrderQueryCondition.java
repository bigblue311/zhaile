package com.zhaile.dal.query.condition;

import java.util.Date;

import com.victor.framework.common.tools.DateTools;
import com.victor.framework.dal.basic.QueryCondition;

public class OrderQueryCondition extends QueryCondition {
	public OrderQueryCondition customerId(Long customerId) {
		put("customerId",customerId);
		return this;
	}
	
	public OrderQueryCondition customerName(String customerName) {
		put("customerName",customerName);
		return this;
	}
	
	public OrderQueryCondition paymentId(Long paymentId){
		put("paymentId", paymentId);
		return this;
	}
	
	public OrderQueryCondition shopId(Long... shopId){
		put("shopId", shopId);
		return this;
	}
	
	public OrderQueryCondition productId(Long productId){
		put("productId", productId);
		return this;
	}
	
	public OrderQueryCondition status(String status){
		put("status", status);
		return this;
	}
	
	public OrderQueryCondition shopName(String shopName){
		put("shopName", shopName);
		return this;
	}
	
	public OrderQueryCondition source(String source){
		put("source", source);
		return this;
	}
	
	public OrderQueryCondition employeeId(Long employeeId){
		put("employeeId", employeeId);
		return this;
	}
	
	@Override
	public OrderQueryCondition gmtModifyStart(Date from){
		put("gmtModifyStart", DateTools.getDayBegin(from));
		return this;
	}
	
	@Override
	public OrderQueryCondition gmtModifyEnd(Date to){
		put("gmtModifyEnd", DateTools.getDayEnd(to));
		return this;
	}
	
	@Override
	public OrderQueryCondition start(int start){
		put("start", start);
		return this;
	}
	
	@Override
	public OrderQueryCondition pageSize(int pageSize){
		put("pageSize", pageSize);
		return this;
	}
}
