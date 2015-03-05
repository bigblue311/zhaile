package com.zhaile.dal.enumerate;

import java.util.List;

import com.google.common.collect.Lists;

public enum CreditActionEnum{
	增加("0","获得"),
	减少("1","兑换");
	
	private String code;
	private String desc;
	
	private CreditActionEnum(String code,String desc){
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
	
	public static List<CreditActionEnum> getAll(){
		return Lists.newArrayList(CreditActionEnum.values());
	}
	
	public static CreditActionEnum getByCode(String code){
		for(CreditActionEnum creditAction : getAll()){
			if(creditAction.code.equals(code)){
				return creditAction;
			}
		}
		return null;
	}
	
	public static CreditActionEnum getByDesc(String desc){
		for(CreditActionEnum creditAction : getAll()){
			if(creditAction.desc.equals(desc)){
				return creditAction;
			}
		}
		return null;
	}
	
	public static CreditActionEnum getByText(String text){
		if(getByCode(text) == null) {
			return getByDesc(text);
		}
		return getByCode(text);
	}
}
