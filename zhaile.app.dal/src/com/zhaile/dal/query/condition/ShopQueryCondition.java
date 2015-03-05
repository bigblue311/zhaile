package com.zhaile.dal.query.condition;

import java.util.Date;

import com.victor.framework.common.tools.DateTools;
import com.victor.framework.dal.basic.QueryCondition;

public class ShopQueryCondition extends QueryCondition {
	
	public ShopQueryCondition customerId(Long customerId) {
		put("customerId",customerId);
		return this;
	}
	
	public ShopQueryCondition categoryId(Long categoryId) {
		put("categoryId",categoryId);
		return this;
	}
	
	public ShopQueryCondition ids(Long[] ids){
		put("ids",ids);
		return this;
	}
	
	public ShopQueryCondition keyword(String keyword) {
		put("keyword",keyword);
		return this;
	}
	
	public ShopQueryCondition valid(Boolean valid) {
		put("valid",valid);
		return this;
	}
	
	public ShopQueryCondition alphabet(String alphabet) {
		put("alphabet",alphabet);
		return this;
	}
	
	@Override
	public ShopQueryCondition gmtModifyStart(Date from){
		put("gmtModifyStart", DateTools.getDayBegin(from));
		return this;
	}
	
	@Override
	public ShopQueryCondition gmtModifyEnd(Date to){
		put("gmtModifyEnd", DateTools.getDayEnd(to));
		return this;
	}
	
	@Override
	public ShopQueryCondition start(int start){
		put("start", start);
		return this;
	}
	
	@Override
	public ShopQueryCondition pageSize(int pageSize){
		put("pageSize", pageSize);
		return this;
	}
}
