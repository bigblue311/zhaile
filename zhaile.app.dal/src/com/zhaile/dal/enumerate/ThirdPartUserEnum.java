package com.zhaile.dal.enumerate;

import java.util.List;

import com.google.common.collect.Lists;

public enum ThirdPartUserEnum{
	QQ("0","QQ"),
	淘宝("1","TAOBAO"),
	微信("2","WECHAT"),
	百度("3","百度"),
	APP("4","APP");
	
	private String code;
	private String desc;
	
	private ThirdPartUserEnum(String code,String desc){
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
	
	public static List<ThirdPartUserEnum> getAll(){
		return Lists.newArrayList(ThirdPartUserEnum.values());
	}
	
	public static ThirdPartUserEnum getByCode(String code){
		for(ThirdPartUserEnum user : getAll()){
			if(user.code.equals(code)){
				return user;
			}
		}
		return null;
	}
	
	public static ThirdPartUserEnum getByDesc(String desc){
		for(ThirdPartUserEnum user : getAll()){
			if(user.desc.equals(desc)){
				return user;
			}
		}
		return null;
	}
	
	public static ThirdPartUserEnum getByText(String text){
		if(getByCode(text) == null) {
			return getByDesc(text);
		}
		return getByCode(text);
	}
}
