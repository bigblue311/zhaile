package com.zhaile.biz.web.form;

import com.victor.framework.common.tools.StringTools;
import com.zhaile.dal.query.condition.LogQueryCondition;

public class SearchLogQueryFormBean extends QueryFormBean{
	private Long customerId;
	private String keyword;
	private int page = 1;
	private int pageSize = 20;
	
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	
	public LogQueryCondition getLogQueryCondition(){
		LogQueryCondition queryCondition = new LogQueryCondition();
		if(StringTools.isNotEmpty(keyword)){
			queryCondition.keyword(keyword);
		}
		if(customerId!=null){
			queryCondition.customerId(customerId);
		}
		if(modifyStart!=null){
			queryCondition.gmtModifyStart(modifyStart);
		}
		if(modifyEnd!=null){
			queryCondition.gmtModifyEnd(modifyEnd);
		}
		queryCondition.pageSize(pageSize).start((page-1)*pageSize);
		return queryCondition;
	}
}
