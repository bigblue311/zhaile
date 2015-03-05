package com.zhaile.dal.enumerate;

import java.util.List;

import com.google.common.collect.Lists;

public enum EnableEnum {
	有效("0","有效"),
	无效("1","无效");
	
	private String code;
	private String desc;
	
	private EnableEnum(String code,String desc){
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
	
	public static List<EnableEnum> getAll(){
		return Lists.newArrayList(EnableEnum.values());
	}
	
	public static EnableEnum getByCode(String code){
		for(EnableEnum source : getAll()){
			if(source.code.equals(code)){
				return source;
			}
		}
		return null;
	}
	
	public static EnableEnum getByDesc(String desc){
		for(EnableEnum source : getAll()){
			if(source.desc.equals(desc)){
				return source;
			}
		}
		return null;
	}
	
	public static EnableEnum getByText(String text){
		if(getByCode(text) == null) {
			return getByDesc(text);
		}
		return getByCode(text);
	}
}
