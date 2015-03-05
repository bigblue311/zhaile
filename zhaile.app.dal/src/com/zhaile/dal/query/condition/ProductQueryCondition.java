package com.zhaile.dal.query.condition;

import java.util.Date;

import com.victor.framework.common.tools.DateTools;
import com.victor.framework.dal.basic.QueryCondition;

public class ProductQueryCondition extends QueryCondition {
	
	public ProductQueryCondition shopId(Long shopId) {
		put("shopId",shopId);
		return this;
	}
	
	
	public ProductQueryCondition keyword(String keyword) {
		put("keyword",keyword);
		return this;
	}
	
	public ProductQueryCondition valid(Boolean valid) {
		put("valid",valid);
		return this;
	}
	
	@Override
	public ProductQueryCondition gmtModifyStart(Date from){
		put("gmtModifyStart", DateTools.getDayBegin(from));
		return this;
	}
	
	@Override
	public ProductQueryCondition gmtModifyEnd(Date to){
		put("gmtModifyEnd", DateTools.getDayEnd(to));
		return this;
	}
	
	@Override
	public ProductQueryCondition start(int start){
		put("start", start);
		return this;
	}
	
	@Override
	public ProductQueryCondition pageSize(int pageSize){
		put("pageSize", pageSize);
		return this;
	}
}
