package com.zhaile.dal.enumerate;

import java.util.List;

import com.google.common.collect.Lists;

public enum MapMarkerStatusEnum {
	未处理("0","未处理"),
	已确定("1","已确定"),
	已取消("2","已取消");
	
	private String code;
	private String desc;
	
	private MapMarkerStatusEnum(String code,String desc){
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
	
	public static List<MapMarkerStatusEnum> getAll(){
		return Lists.newArrayList(MapMarkerStatusEnum.values());
	}
	
	public static MapMarkerStatusEnum getByCode(String code){
		for(MapMarkerStatusEnum foreignKey : getAll()){
			if(foreignKey.code.equals(code)){
				return foreignKey;
			}
		}
		return null;
	}
	
	public static MapMarkerStatusEnum getByDesc(String desc){
		for(MapMarkerStatusEnum foreignKey : getAll()){
			if(foreignKey.desc.equals(desc)){
				return foreignKey;
			}
		}
		return null;
	}
	
	public static MapMarkerStatusEnum getByText(String text){
		if(getByCode(text) == null) {
			return getByDesc(text);
		}
		return getByCode(text);
	}
}
