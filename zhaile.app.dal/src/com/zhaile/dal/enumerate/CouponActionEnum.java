package com.zhaile.dal.enumerate;

import java.util.List;

import com.google.common.collect.Lists;

public enum CouponActionEnum{
	增加("0","获得"),
	减少("1","兑换");
	
	private String code;
	private String desc;
	
	private CouponActionEnum(String code,String desc){
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
	
	public static List<CouponActionEnum> getAll(){
		return Lists.newArrayList(CouponActionEnum.values());
	}
	
	public static CouponActionEnum getByCode(String code){
		for(CouponActionEnum creditAction : getAll()){
			if(creditAction.code.equals(code)){
				return creditAction;
			}
		}
		return null;
	}
	
	public static CouponActionEnum getByDesc(String desc){
		for(CouponActionEnum creditAction : getAll()){
			if(creditAction.desc.equals(desc)){
				return creditAction;
			}
		}
		return null;
	}
	
	public static CouponActionEnum getByText(String text){
		if(getByCode(text) == null) {
			return getByDesc(text);
		}
		return getByCode(text);
	}
}
