package com.zhaile.dal.query.condition;

import java.util.Date;

import com.victor.framework.common.tools.DateTools;
import com.victor.framework.common.tools.StringTools;
import com.victor.framework.dal.basic.QueryCondition;

public class SmsTaskQueryCondition extends QueryCondition {

	@Override
	public SmsTaskQueryCondition gmtModifyStart(Date from){
		if(from != null){
			put("gmtModifyStart", DateTools.getDayBegin(from));
		}
		return this;
	}
	
	@Override
	public SmsTaskQueryCondition gmtModifyEnd(Date to){
		if(to != null){
			put("gmtModifyEnd", DateTools.getDayEnd(to));
		}
		return this;
	}
	
	@Override
	public SmsTaskQueryCondition start(int start){
		put("start", start);
		return this;
	}
	
	@Override
	public SmsTaskQueryCondition pageSize(int pageSize){
		put("pageSize", pageSize);
		return this;
	}
	
	public SmsTaskQueryCondition status(String status){
		if(StringTools.isNotEmpty(status)){
			put("status",status);
		}
		return this;
	}
	
	public SmsTaskQueryCondition mobile(String mobile){
		if(StringTools.isNotEmpty(mobile)){
			put("mobile",mobile);
		}
		return this;
	}
	
	public SmsTaskQueryCondition type(String type){
		if(StringTools.isNotEmpty(type)){
			put("type",type);
		}
		return this;
	}
}
