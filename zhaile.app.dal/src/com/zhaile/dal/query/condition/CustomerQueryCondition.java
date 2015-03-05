package com.zhaile.dal.query.condition;

import java.util.Date;

import com.victor.framework.common.tools.DateTools;
import com.victor.framework.dal.basic.QueryCondition;

public class CustomerQueryCondition extends QueryCondition {	
	
	public CustomerQueryCondition name(String name) {
		put("name",name);
		return this;
	}
	
	public CustomerQueryCondition email(String email) {
		put("email",email);
		return this;
	}
	
	public CustomerQueryCondition mobile(String mobile) {
		put("mobile",mobile);
		return this;
	}
	
	@Override
	public CustomerQueryCondition gmtModifyStart(Date from){
		put("gmtModifyStart", DateTools.getDayBegin(from));
		return this;
	}
	
	@Override
	public CustomerQueryCondition gmtModifyEnd(Date to){
		put("gmtModifyEnd", DateTools.getDayEnd(to));
		return this;
	}
	
	@Override
	public CustomerQueryCondition start(int start){
		put("start", start);
		return this;
	}
	
	@Override
	public CustomerQueryCondition pageSize(int pageSize){
		put("pageSize", pageSize);
		return this;
	}
}
