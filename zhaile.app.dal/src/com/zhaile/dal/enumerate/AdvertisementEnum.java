package com.zhaile.dal.enumerate;

import java.util.List;

import com.google.common.collect.Lists;

public enum AdvertisementEnum {
	跑马灯("0","跑马灯"),
	友情链接("1","友情链接");
	
	private String code;
	private String desc;
	
	private AdvertisementEnum(String code,String desc){
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
	
	public static List<AdvertisementEnum> getAll(){
		return Lists.newArrayList(AdvertisementEnum.values());
	}
	
	public static AdvertisementEnum getByCode(String code){
		for(AdvertisementEnum ad : getAll()){
			if(ad.code.equals(code)){
				return ad;
			}
		}
		return null;
	}
	
	public static AdvertisementEnum getByDesc(String desc){
		for(AdvertisementEnum ad : getAll()){
			if(ad.desc.equals(desc)){
				return ad;
			}
		}
		return null;
	}
	
	public static AdvertisementEnum getByText(String text){
		if(getByCode(text) == null) {
			return getByDesc(text);
		}
		return getByCode(text);
	}
}
