package com.victor.framework.search.basic;

import java.util.Date;

import com.victor.framework.common.tools.DateTools;
import com.victor.framework.dal.basic.QueryCondition;
import com.victor.framework.search.enumerate.SearchResultEnum;

public class SearchQuery extends QueryCondition {
	public SearchQuery(SearchResultEnum searchResultEnum){
		this.searchResultEnum = searchResultEnum;
		put("start", 0);
		put("pageSize", 5000);
	}
	
	private SearchResultEnum searchResultEnum;
	
	public SearchResultEnum getSearchResultEnum() {
		return searchResultEnum;
	}
	
	public SearchQuery shopId(Long shopId){
		put("shopId", shopId);
		return this;
	}
	
	public SearchQuery customerId(Long customerId){
		put("customerId", customerId);
		return this;
	}
	public SearchQuery keyword(String keyword) {
		put("keyword",keyword.replace("'", ""));
		return this;
	}
	public SearchQuery tagId(Long tagId) {
		put("tagId",tagId);
		return this;
	}
	public SearchQuery categoryId(Long categoryId) {
		put("categoryId",categoryId);
		return this;
	}
	@Override
	public SearchQuery gmtModifyStart(Date from){
		put("gmtModifyStart", DateTools.getDayBegin(from));
		return this;
	}
	
	@Override
	public SearchQuery gmtModifyEnd(Date to){
		put("gmtModifyEnd", DateTools.getDayEnd(to));
		return this;
	}
	
	@Override
	public SearchQuery start(int start){
		put("start", start);
		return this;
	}
	
	@Override
	public SearchQuery pageSize(int pageSize){
		put("pageSize", pageSize);
		return this;
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("[searchResultEnum:");
		sb.append(this.getSearchResultEnum().getCode());
		for(String key : this.getQueryMap().keySet()){
			sb.append(",");
			sb.append(key);
			sb.append(":");
			Object value = this.getQueryMap().get(key);
			if(value instanceof Date){
				sb.append(DateTools.DateToString((Date)value));
			} else if(value == null){ 
				sb.append("");
			}else {
				sb.append(value.toString());
			}
		}
		sb.append("]");
		return sb.toString();
	}
}
