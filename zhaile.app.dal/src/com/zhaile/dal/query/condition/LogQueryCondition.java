package com.zhaile.dal.query.condition;

import java.util.Date;

import com.victor.framework.common.tools.DateTools;
import com.victor.framework.dal.basic.QueryCondition;

public class LogQueryCondition extends QueryCondition {	
	public LogQueryCondition customerId(Long customerId) {
		put("customerId",customerId);
		return this;
	}
	
	public LogQueryCondition keyword(String keyword) {
		put("keyword",keyword);
		return this;
	}
	
	public LogQueryCondition opType(String opType) {
		put("OpType",opType);
		return this;
	}
	
	@Override
	public LogQueryCondition gmtModifyStart(Date from){
		put("gmtModifyStart", DateTools.getDayBegin(from));
		return this;
	}
	
	@Override
	public LogQueryCondition gmtModifyEnd(Date to){
		put("gmtModifyEnd", DateTools.getDayEnd(to));
		return this;
	}
	
	@Override
	public LogQueryCondition start(int start){
		put("start", start);
		return this;
	}
	
	@Override
	public LogQueryCondition pageSize(int pageSize){
		put("pageSize", pageSize);
		return this;
	}
}
