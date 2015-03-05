package com.victor.framework.batch.thread;

import java.util.List;

import com.google.common.collect.Lists;

public enum ExecutorStatusEnum {
	运行("0","运行"),
	停止("1","停止"),
	异常("2","异常");
	
	private String code;
	private String desc;
	
	private ExecutorStatusEnum(String code,String desc){
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
	
	public static List<ExecutorStatusEnum> getAll(){
		return Lists.newArrayList(ExecutorStatusEnum.values());
	}
	
	public static ExecutorStatusEnum getByCode(String code){
		for(ExecutorStatusEnum ad : getAll()){
			if(ad.code.equals(code)){
				return ad;
			}
		}
		return null;
	}
	
	public static ExecutorStatusEnum getByDesc(String desc){
		for(ExecutorStatusEnum ad : getAll()){
			if(ad.desc.equals(desc)){
				return ad;
			}
		}
		return null;
	}
	
	public static ExecutorStatusEnum getByText(String text){
		if(getByCode(text) == null) {
			return getByDesc(text);
		}
		return getByCode(text);
	}
}
