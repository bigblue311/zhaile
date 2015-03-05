package com.zhaile.dal.query.condition;

import java.util.Date;

import com.victor.framework.common.tools.DateTools;
import com.victor.framework.dal.basic.QueryCondition;
import com.zhaile.dal.enumerate.EnableEnum;

public class ShoppingCarQueryCondition extends QueryCondition {
	public ShoppingCarQueryCondition customerId(Long customerId) {
		put("customerId",customerId);
		return this;
	}
	
	public ShoppingCarQueryCondition prodId(Long prodId) {
		put("prodId",prodId);
		return this;
	}
	
	public ShoppingCarQueryCondition shopId(Long shopId) {
		put("shopId",shopId);
		return this;
	}
	
	public ShoppingCarQueryCondition valid(EnableEnum valid) {
		put("valid",valid.getCode());
		return this;
	}
	
	@Override
	public ShoppingCarQueryCondition gmtModifyStart(Date from){
		put("gmtModifyStart", DateTools.getDayBegin(from));
		return this;
	}
	
	@Override
	public ShoppingCarQueryCondition gmtModifyEnd(Date to){
		put("gmtModifyEnd", DateTools.getDayEnd(to));
		return this;
	}
	
	@Override
	public ShoppingCarQueryCondition start(int start){
		put("start", start);
		return this;
	}
	
	@Override
	public ShoppingCarQueryCondition pageSize(int pageSize){
		put("pageSize", pageSize);
		return this;
	}
}
