package com.zhaile.dal.enumerate;

import java.util.List;

import com.google.common.collect.Lists;

public enum DeliveryStatusEnum {
	未处理("0","未处理"),
	取单中("1","取单中"),
	送达中("2","送达中"),
	已送到("3","已送到"),
	未送到("4","未送到"),
	已取消("5","已取消");
	
	private String code;
	private String desc;
	
	private DeliveryStatusEnum(String code,String desc){
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
	
	public static List<DeliveryStatusEnum> getAll(){
		return Lists.newArrayList(DeliveryStatusEnum.values());
	}
	
	public static DeliveryStatusEnum getByCode(String code){
		for(DeliveryStatusEnum deliveryStatus : getAll()){
			if(deliveryStatus.code.equals(code)){
				return deliveryStatus;
			}
		}
		return null;
	}
	
	public static DeliveryStatusEnum getByDesc(String desc){
		for(DeliveryStatusEnum deliveryStatus : getAll()){
			if(deliveryStatus.desc.equals(desc)){
				return deliveryStatus;
			}
		}
		return null;
	}
	
	public static DeliveryStatusEnum getByText(String text){
		if(getByCode(text) == null) {
			return getByDesc(text);
		}
		return getByCode(text);
	}
}
