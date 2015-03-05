package com.zhaile.dal.query.condition;

import java.util.Date;

import com.victor.framework.common.tools.DateTools;
import com.victor.framework.dal.basic.QueryCondition;

public class CustomerFavQueryCondition extends QueryCondition {	
	
	public CustomerFavQueryCondition customerId(Long customerId) {
		put("customerId",customerId);
		return this;
	}
	
	public CustomerFavQueryCondition shopId(Long shopId) {
		put("shopId",shopId);
		return this;
	}
	
	public CustomerFavQueryCondition prodId(Long prodId) {
		put("prodId",prodId);
		return this;
	}
	
	@Override
	public CustomerFavQueryCondition gmtModifyStart(Date from){
		put("gmtModifyStart", DateTools.getDayBegin(from));
		return this;
	}
	
	@Override
	public CustomerFavQueryCondition gmtModifyEnd(Date to){
		put("gmtModifyEnd", DateTools.getDayEnd(to));
		return this;
	}
	
	@Override
	public CustomerFavQueryCondition start(int start){
		put("start", start);
		return this;
	}
	
	@Override
	public CustomerFavQueryCondition pageSize(int pageSize){
		put("pageSize", pageSize);
		return this;
	}
}
