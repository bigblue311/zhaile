package com.zhaile.dal.enumerate;

import java.util.List;

import com.google.common.collect.Lists;

public enum SmsTypeEnum {
	客户订单短信("0","客户订单短信"),
	商家小票短信("1","商家小票短信"),
	找回密码验证码("2","找回密码验证码");
	
	private String code;
	private String desc;
	
	private SmsTypeEnum(String code,String desc){
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
	
	public static List<SmsTypeEnum> getAll(){
		return Lists.newArrayList(SmsTypeEnum.values());
	}
	
	public static SmsTypeEnum getByCode(String code){
		for(SmsTypeEnum smsStatus : getAll()){
			if(smsStatus.code.equals(code)){
				return smsStatus;
			}
		}
		return null;
	}
	
	public static SmsTypeEnum getByDesc(String desc){
		for(SmsTypeEnum smsStatus : getAll()){
			if(smsStatus.desc.equals(desc)){
				return smsStatus;
			}
		}
		return null;
	}
	
	public static SmsTypeEnum getByText(String text){
		if(getByCode(text) == null) {
			return getByDesc(text);
		}
		return getByCode(text);
	}
}
