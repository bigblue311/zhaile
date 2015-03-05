package com.zhaile.dal.enumerate;

import java.util.List;

import com.google.common.collect.Lists;

public enum MapIconEnum {
	金融("0","金融","/img/map/dollar.png"),
	美食("1","美食","/img/map/food.png"),
	房产("2","房产","/img/map/house.png"),
	优惠("3","优惠","/img/map/hui.png"),
	团购("4","团购","/img/map/tuan.png");
	
	private String code;
	private String desc;
	private String url;
	
	private MapIconEnum(String code,String desc, String url){
		this.code = code;
		this.desc = desc;
		this.url = url;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
	
	public String getUrl(){
		return url;
	}
	
	public static List<MapIconEnum> getAll(){
		return Lists.newArrayList(MapIconEnum.values());
	}
	
	public static MapIconEnum getByCode(String code){
		for(MapIconEnum foreignKey : getAll()){
			if(foreignKey.code.equals(code)){
				return foreignKey;
			}
		}
		return null;
	}
	
	public static MapIconEnum getByDesc(String desc){
		for(MapIconEnum foreignKey : getAll()){
			if(foreignKey.desc.equals(desc)){
				return foreignKey;
			}
		}
		return null;
	}
	
	public static MapIconEnum getByText(String text){
		if(getByCode(text) == null) {
			return getByDesc(text);
		}
		return getByCode(text);
	}
}
