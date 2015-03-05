package com.victor.framework.search.enumerate;

import java.util.List;

import com.google.common.collect.Lists;

public enum SearchOrderEnum {
	顺序("asc","顺序"),
	逆序("desc","逆序");
	
	private String code;
	private String desc;
	
	private SearchOrderEnum(String code,String desc){
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
	
	public static List<SearchOrderEnum> getAll(){
		return Lists.newArrayList(SearchOrderEnum.values());
	}
	
	public static SearchOrderEnum getByCode(String code){
		for(SearchOrderEnum searchOrder : getAll()){
			if(searchOrder.code.equals(code)){
				return searchOrder;
			}
		}
		return null;
	}
	
	public static SearchOrderEnum getByDesc(String desc){
		for(SearchOrderEnum searchOrder : getAll()){
			if(searchOrder.desc.equals(desc)){
				return searchOrder;
			}
		}
		return null;
	}
	
	public static SearchOrderEnum getByText(String text){
		if(getByCode(text) == null) {
			return getByDesc(text);
		}
		return getByCode(text);
	}
}
