package com.zhaile.dal.enumerate;

import java.util.List;

import com.google.common.collect.Lists;

public enum SmsStatusEnum {
	未发送("0","未发送"),
	准备发送("1","准备发送"),
	发送成功("2","发送成功"),
	发送失败("3","发送失败"),
	已过期("4","已过期"),
	设备异常("5","设备异常");
	
	private String code;
	private String desc;
	
	private SmsStatusEnum(String code,String desc){
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
	
	public static List<SmsStatusEnum> getAll(){
		return Lists.newArrayList(SmsStatusEnum.values());
	}
	
	public static SmsStatusEnum getByCode(String code){
		for(SmsStatusEnum smsStatus : getAll()){
			if(smsStatus.code.equals(code)){
				return smsStatus;
			}
		}
		return null;
	}
	
	public static SmsStatusEnum getByDesc(String desc){
		for(SmsStatusEnum smsStatus : getAll()){
			if(smsStatus.desc.equals(desc)){
				return smsStatus;
			}
		}
		return null;
	}
	
	public static SmsStatusEnum getByText(String text){
		if(getByCode(text) == null) {
			return getByDesc(text);
		}
		return getByCode(text);
	}
}
