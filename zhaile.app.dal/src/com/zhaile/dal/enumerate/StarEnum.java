package com.zhaile.dal.enumerate;

import java.util.List;

import com.google.common.collect.Lists;

public enum StarEnum{
	零星("0","零星"),
	一星("1","一星"),
	二星("2","二星"),
	三星("3","三星"),
	四星("4","四星"),
	五星("5","五星");
	
	private String code;
	private String desc;
	
	private StarEnum(String code,String desc){
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
	
	public static List<StarEnum> getAll(){
		return Lists.newArrayList(StarEnum.values());
	}
	
	public static StarEnum getByCode(String code){
		for(StarEnum star : getAll()){
			if(star.code.equals(code)){
				return star;
			}
		}
		return null;
	}
	
	public static StarEnum getByDesc(String desc){
		for(StarEnum star : getAll()){
			if(star.desc.equals(desc)){
				return star;
			}
		}
		return null;
	}
	
	public static StarEnum getByText(String text){
		if(getByCode(text) == null) {
			return getByDesc(text);
		}
		return getByCode(text);
	}
}
