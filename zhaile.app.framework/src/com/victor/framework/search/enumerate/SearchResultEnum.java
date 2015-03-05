package com.victor.framework.search.enumerate;

import java.util.List;

import com.google.common.collect.Lists;

public enum SearchResultEnum {
	买家("1","买家"),
	商家("2","商家"),
	商品("3","商品");
	
	private String code;
	private String desc;
	
	private SearchResultEnum(String code,String desc){
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
	
	public static List<SearchResultEnum> getAll(){
		return Lists.newArrayList(SearchResultEnum.values());
	}
	
	public static SearchResultEnum getByCode(String code){
		for(SearchResultEnum searchResult : getAll()){
			if(searchResult.code.equals(code)){
				return searchResult;
			}
		}
		return null;
	}
	
	public static SearchResultEnum getByDesc(String desc){
		for(SearchResultEnum searchResult : getAll()){
			if(searchResult.desc.equals(desc)){
				return searchResult;
			}
		}
		return null;
	}
	
	public static SearchResultEnum getByText(String text){
		if(getByCode(text) == null) {
			return getByDesc(text);
		}
		return getByCode(text);
	}
}
