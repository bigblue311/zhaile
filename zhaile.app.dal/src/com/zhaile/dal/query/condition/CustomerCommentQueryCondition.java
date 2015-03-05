package com.zhaile.dal.query.condition;

import java.util.Date;

import com.victor.framework.common.tools.DateTools;
import com.victor.framework.dal.basic.QueryCondition;

public class CustomerCommentQueryCondition extends QueryCondition {

	public CustomerCommentQueryCondition customerId(Long customerId){
		put("customerId", customerId);
		return this;
	}
	
	public CustomerCommentQueryCondition prodId(Long prodId){
		if(prodId!=null){
			put("prodId", prodId);
		}
		return this;
	}
	
	public CustomerCommentQueryCondition shopId(Long shopId){
		if(shopId!=null) {
			put("shopId", shopId);
		}
		return this;
	}
	
	public CustomerCommentQueryCondition shopIds(Long... shopIds){
		if(shopIds!=null) {
			put("shopIds", shopIds);
		}
		return this;
	}
	
	public CustomerCommentQueryCondition good(){
		put("good", true);
		return this;
	}
	
	public CustomerCommentQueryCondition bad(){
		put("bad", true);
		return this;
	}
	
	@Override
	public CustomerCommentQueryCondition gmtModifyStart(Date from){
		put("gmtModifyStart", DateTools.getDayBegin(from));
		return this;
	}
	
	@Override
	public CustomerCommentQueryCondition gmtModifyEnd(Date to){
		put("gmtModifyEnd", DateTools.getDayEnd(to));
		return this;
	}
	
	@Override
	public CustomerCommentQueryCondition start(int start){
		put("start", start);
		return this;
	}
	
	@Override
	public CustomerCommentQueryCondition pageSize(int pageSize){
		put("pageSize", pageSize);
		return this;
	}
}
