package com.zhaile.dal.enumerate;

import java.util.List;

import com.google.common.collect.Lists;

public enum GenderEnum{
	先生("0","先生"),
	女士("1","女士");
	
	private String code;
	private String desc;
	
	private GenderEnum(String code,String desc){
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
	
	public static List<GenderEnum> getAll(){
		return Lists.newArrayList(GenderEnum.values());
	}
	
	public static GenderEnum getByCode(String code){
		for(GenderEnum gender : getAll()){
			if(gender.code.equals(code)){
				return gender;
			}
		}
		return null;
	}
	
	public static GenderEnum getByDesc(String desc){
		for(GenderEnum gender : getAll()){
			if(gender.desc.equals(desc)){
				return gender;
			}
		}
		return null;
	}
	
	public static GenderEnum getByText(String text){
		if(getByCode(text) == null) {
			return getByDesc(text);
		}
		return getByCode(text);
	}
}
