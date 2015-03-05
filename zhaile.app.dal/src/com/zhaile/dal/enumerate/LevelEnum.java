package com.zhaile.dal.enumerate;

import java.util.List;

import com.google.common.collect.Lists;

public enum LevelEnum{
	喜欢("0","喜欢"),
	一般("1","一般"),
	不喜欢("2","不喜欢");
	
	private String code;
	private String desc;
	
	private LevelEnum(String code,String desc){
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
	
	public static List<LevelEnum> getAll(){
		return Lists.newArrayList(LevelEnum.values());
	}
	
	public static LevelEnum getByCode(String code){
		for(LevelEnum level : getAll()){
			if(level.code.equals(code)){
				return level;
			}
		}
		return null;
	}
	
	public static LevelEnum getByDesc(String desc){
		for(LevelEnum level : getAll()){
			if(level.desc.equals(desc)){
				return level;
			}
		}
		return null;
	}
	
	public static LevelEnum getByText(String text){
		if(getByCode(text) == null) {
			return getByDesc(text);
		}
		return getByCode(text);
	}
}
