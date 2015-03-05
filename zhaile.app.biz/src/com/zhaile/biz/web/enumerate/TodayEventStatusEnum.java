package com.zhaile.biz.web.enumerate;

import java.util.List;

import com.google.common.collect.Lists;

public enum TodayEventStatusEnum {
	无效("-1","无效",false),
	未开始("0","未开始",true),
	进行中("1","进行中",true),
	售罄("2","售罄",false),
	已结束("3","已结束",false);
	
	private String code;
	private String desc;
	private Boolean valid;
	
	private TodayEventStatusEnum(String code,String desc,Boolean valid){
		this.code = code;
		this.desc = desc;
		this.valid = valid;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
	
	public Boolean getValid() {
		return valid;
	}

	public static List<TodayEventStatusEnum> getAll(){
		return Lists.newArrayList(TodayEventStatusEnum.values());
	}
	
	public static TodayEventStatusEnum getByCode(String code){
		for(TodayEventStatusEnum statusEnum : getAll()){
			if(statusEnum.code.equals(code)){
				return statusEnum;
			}
		}
		return null;
	}
	
	public static TodayEventStatusEnum getByDesc(String desc){
		for(TodayEventStatusEnum statusEnum : getAll()){
			if(statusEnum.desc.equals(desc)){
				return statusEnum;
			}
		}
		return null;
	}
	
	public static TodayEventStatusEnum getByText(String text){
		if(getByCode(text) == null) {
			return getByDesc(text);
		}
		return getByCode(text);
	}
}
