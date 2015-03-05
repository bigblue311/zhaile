package com.zhaile.dal.query.condition;

import java.util.Date;

import com.victor.framework.common.tools.DateTools;
import com.victor.framework.dal.basic.QueryCondition;

public class FlashGoQueryCondition extends QueryCondition{

	public FlashGoQueryCondition gmtOpenStart(Date from){
		put("gmtOpenStart", DateTools.getDayBegin(from));
		return this;
	}
	
	public FlashGoQueryCondition gmtOpenEnd(Date to){
		put("gmtOpenEnd", DateTools.getDayEnd(to));
		return this;
	}
	
	@Override
	public FlashGoQueryCondition gmtModifyStart(Date from) {
		put("gmtModifyStart", DateTools.getDayBegin(from));
		return this;
	}

	@Override
	public FlashGoQueryCondition gmtModifyEnd(Date to) {
		put("gmtModifyEnd", DateTools.getDayEnd(to));
		return this;
	}

	@Override
	public FlashGoQueryCondition start(int start) {
		put("start", start);
		return this;
	}

	@Override
	public FlashGoQueryCondition pageSize(int pageSize) {
		put("pageSize", pageSize);
		return this;
	}

}
