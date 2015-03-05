package com.zhaile.dal.enumerate;

import java.util.List;

import com.google.common.collect.Lists;

public enum ServiceTypeEnum {
	宅乐外送("0","宅乐外送"),
	自取("1","自取"),
	店家外送("2","店家外送");
	
	private String code;
	private String desc;
	
	private ServiceTypeEnum(String code,String desc){
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
	
	public static List<ServiceTypeEnum> getAll(){
		return Lists.newArrayList(ServiceTypeEnum.values());
	}
	
	public static ServiceTypeEnum getByCode(String code){
		for(ServiceTypeEnum serviceType : getAll()){
			if(serviceType.code.equals(code)){
				return serviceType;
			}
		}
		return null;
	}
	
	public static ServiceTypeEnum getByDesc(String desc){
		for(ServiceTypeEnum serviceType : getAll()){
			if(serviceType.desc.equals(desc)){
				return serviceType;
			}
		}
		return null;
	}
	
	public static ServiceTypeEnum getByText(String text){
		if(getByCode(text) == null) {
			return getByDesc(text);
		}
		return getByCode(text);
	}
}
