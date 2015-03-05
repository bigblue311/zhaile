package com.zhaile.dal.enumerate;

import java.util.List;

import com.google.common.collect.Lists;

public enum PaymentStatusEnum {
	货到付款("999","货到付款"),
	未支付("0","未支付"),
	支付成功("1","支付成功"),
	支付失败("2","支付失败");
	
	private String code;
	private String desc;
	
	private PaymentStatusEnum(String code,String desc){
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
	
	public static List<PaymentStatusEnum> getAll(){
		return Lists.newArrayList(PaymentStatusEnum.values());
	}
	
	public static PaymentStatusEnum getByCode(String code){
		for(PaymentStatusEnum paymentStatus : getAll()){
			if(paymentStatus.code.equals(code)){
				return paymentStatus;
			}
		}
		return null;
	}
	
	public static PaymentStatusEnum getByDesc(String desc){
		for(PaymentStatusEnum paymentStatus : getAll()){
			if(paymentStatus.desc.equals(desc)){
				return paymentStatus;
			}
		}
		return null;
	}
	
	public static PaymentStatusEnum getByText(String text){
		if(getByCode(text) == null) {
			return getByDesc(text);
		}
		return getByCode(text);
	}
}
