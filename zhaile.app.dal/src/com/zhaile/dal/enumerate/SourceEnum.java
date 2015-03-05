package com.zhaile.dal.enumerate;

import java.util.List;

import com.google.common.collect.Lists;

public enum SourceEnum {
	宅乐网("0","宅乐网"),
	宅乐微信("1","宅乐微信"),
	移动APP("2","移动APP");
	
	private String code;
	private String desc;
	
	private SourceEnum(String code,String desc){
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
	
	public static List<SourceEnum> getAll(){
		return Lists.newArrayList(SourceEnum.values());
	}
	
	public static SourceEnum getByCode(String code){
		for(SourceEnum source : getAll()){
			if(source.code.equals(code)){
				return source;
			}
		}
		return null;
	}
	
	public static SourceEnum getByDesc(String desc){
		for(SourceEnum source : getAll()){
			if(source.desc.equals(desc)){
				return source;
			}
		}
		return null;
	}
	
	public static SourceEnum getByText(String text){
		if(getByCode(text) == null) {
			return getByDesc(text);
		}
		return getByCode(text);
	}
}
