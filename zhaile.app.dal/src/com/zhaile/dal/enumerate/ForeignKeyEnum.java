package com.zhaile.dal.enumerate;

import java.util.List;

import com.google.common.collect.Lists;

public enum ForeignKeyEnum {
	系统("0","系统"),
	用户("1","用户"),
	商家("2","商家"),
	商品("3","商品"),
	IP("4","IP"),
	员工("5","员工");
	
	private String code;
	private String desc;
	
	private ForeignKeyEnum(String code,String desc){
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
	
	public static List<ForeignKeyEnum> getAll(){
		return Lists.newArrayList(ForeignKeyEnum.values());
	}
	
	public static ForeignKeyEnum getByCode(String code){
		for(ForeignKeyEnum foreignKey : getAll()){
			if(foreignKey.code.equals(code)){
				return foreignKey;
			}
		}
		return null;
	}
	
	public static ForeignKeyEnum getByDesc(String desc){
		for(ForeignKeyEnum foreignKey : getAll()){
			if(foreignKey.desc.equals(desc)){
				return foreignKey;
			}
		}
		return null;
	}
	
	public static ForeignKeyEnum getByText(String text){
		if(getByCode(text) == null) {
			return getByDesc(text);
		}
		return getByCode(text);
	}
}
