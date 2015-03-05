package com.zhaile.dal.enumerate;

import java.util.List;

import com.google.common.collect.Lists;

public enum PaymentTypeEnum {
	货到付款("0","货到付款"),
	支付宝("1","支付宝");
	
	private String code;
	private String desc;
	
	private PaymentTypeEnum(String code,String desc){
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
	
	public static List<PaymentTypeEnum> getAll(){
		return Lists.newArrayList(PaymentTypeEnum.values());
	}
	
	public static PaymentTypeEnum getByCode(String code){
		for(PaymentTypeEnum paymentType : getAll()){
			if(paymentType.code.equals(code)){
				return paymentType;
			}
		}
		return null;
	}
	
	public static PaymentTypeEnum getByDesc(String desc){
		for(PaymentTypeEnum paymentType : getAll()){
			if(paymentType.desc.equals(desc)){
				return paymentType;
			}
		}
		return null;
	}
	
	public static PaymentTypeEnum getByText(String text){
		if(getByCode(text) == null) {
			return getByDesc(text);
		}
		return getByCode(text);
	}
}
