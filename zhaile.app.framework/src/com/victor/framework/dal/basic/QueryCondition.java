package com.victor.framework.dal.basic;

import java.util.Date;
import java.util.Map;

import com.google.common.collect.Maps;

public abstract class QueryCondition {
	private Map<String,Object> queryMap = Maps.newHashMap();
	
	public QueryCondition(){
		queryMap.put("start", 0);
		queryMap.put("pageSize", 5000);
		queryMap.put("enable", "0");
	}
	
	public final void put(String key, Object value) {
		queryMap.put(key, value);
	}

	public Map<String, Object> getQueryMap() {
		return queryMap;
	}
	
	public void setQueryMap(Map<String,Object> queryMap){
		this.queryMap = queryMap;
	}
	
	public void clearEnable(){
		this.queryMap.remove("enable");
	}

	public abstract QueryCondition gmtModifyStart(Date from);
	
	public abstract QueryCondition gmtModifyEnd(Date to);
	
	public abstract QueryCondition start(int start);
	
	public abstract QueryCondition pageSize(int pageSize);
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Paging getPaging(int totalSize,int barSize){
		int start = Integer.parseInt(queryMap.get("start").toString());
		int pageSize = Integer.parseInt(queryMap.get("pageSize").toString());
		return new Paging(start,pageSize,totalSize,barSize, queryMap);
	}
}
