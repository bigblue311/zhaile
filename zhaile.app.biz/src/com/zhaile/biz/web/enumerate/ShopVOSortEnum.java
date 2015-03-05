package com.zhaile.biz.web.enumerate;

import java.util.List;

import com.google.common.collect.Lists;

public enum ShopVOSortEnum {
	距离("0","距离"),
	成交量最高("1","成交量最高"),
	好评最多("2","好评最多"),
	最受关注("3","最受关注"),
	字母顺序("4","字母顺序");
	
	
	private String code;
	private String desc;
	
	private ShopVOSortEnum(String code,String desc){
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
	
	public static List<ShopVOSortEnum> getAll(){
		return Lists.newArrayList(ShopVOSortEnum.values());
	}
	
	public static ShopVOSortEnum getByCode(String code){
		for(ShopVOSortEnum sortEnum : getAll()){
			if(sortEnum.code.equals(code)){
				return sortEnum;
			}
		}
		return null;
	}
	
	public static ShopVOSortEnum getByDesc(String desc){
		for(ShopVOSortEnum sortEnum : getAll()){
			if(sortEnum.desc.equals(desc)){
				return sortEnum;
			}
		}
		return null;
	}
	
	public static ShopVOSortEnum getByText(String text){
		if(getByCode(text) == null) {
			return getByDesc(text);
		}
		return getByCode(text);
	}
}
