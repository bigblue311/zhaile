package com.zhaile.dal.query.condition;

import java.util.Date;

import com.victor.framework.common.tools.DateTools;
import com.victor.framework.dal.basic.QueryCondition;

public class MapMarkerQueryCondition extends QueryCondition {

	public MapMarkerQueryCondition status(String status){
		put("status",status);
		return this;
	}
	
	public MapMarkerQueryCondition contact(String contact){
		put("contact",contact);
		return this;
	}
	
	public MapMarkerQueryCondition validFrom(Date from){
		put("validFrom", DateTools.getDayBegin(from));
		return this;
	}
	
	public MapMarkerQueryCondition validTo(Date to){
		put("validTo", DateTools.getDayEnd(to));
		return this;
	}
	
	@Override
	public MapMarkerQueryCondition gmtModifyStart(Date from){
		put("gmtModifyStart", DateTools.getDayBegin(from));
		return this;
	}
	
	@Override
	public MapMarkerQueryCondition gmtModifyEnd(Date to){
		put("gmtModifyEnd", DateTools.getDayEnd(to));
		return this;
	}

	@Override
	public MapMarkerQueryCondition start(int start) {
		put("start", start);
		return this;
	}

	@Override
	public MapMarkerQueryCondition pageSize(int pageSize) {
		put("pageSize", pageSize);
		return this;
	}

}
