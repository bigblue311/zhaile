package com.zhaile.dal.enumerate;

import java.util.List;

import com.google.common.collect.Lists;

public enum DateUnitEnum {
	秒("second","秒", 1000l),
	分钟("minute","分钟", 60*1000l),
	小时("hour","小时", 60*60*1000l),
	天("day","天", 24*60*60*1000l),
	星期("week","星期", 7*24*60*60*1000l),
	月("month","月",30*24*60*60*1000l),
	季度("season","季度",3*30*24*60*60*1000l),
	年("year","年",365*24*60*60*1000l);
	
	private String code;
	private String desc;
	private Long duration;
	
	private DateUnitEnum(String code,String desc, Long duration){
		this.code = code;
		this.desc = desc;
		this.duration = duration;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
	
	public Long getDuration(){
		return duration;
	}
	
	public static List<DateUnitEnum> getAll(){
		return Lists.newArrayList(DateUnitEnum.values());
	}
	
	public static DateUnitEnum getByCode(String code){
		for(DateUnitEnum source : getAll()){
			if(source.code.equals(code)){
				return source;
			}
		}
		return null;
	}
	
	public static DateUnitEnum getByDesc(String desc){
		for(DateUnitEnum source : getAll()){
			if(source.desc.equals(desc)){
				return source;
			}
		}
		return null;
	}
	
	public static DateUnitEnum getByText(String text){
		if(getByCode(text) == null) {
			return getByDesc(text);
		}
		return getByCode(text);
	}
}
