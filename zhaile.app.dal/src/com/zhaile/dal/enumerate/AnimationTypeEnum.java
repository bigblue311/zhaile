package com.zhaile.dal.enumerate;

import java.util.List;

import com.google.common.collect.Lists;

public enum AnimationTypeEnum{
	无动画("0","无动画"),
	从左往右飞入("1","从左往右飞入"),
	从右往左飞入("2","从右往左飞入"),
	从上往下飞入("3","从上往下飞入"),
	从下往上飞入("4","从下往上飞入"),
	渐入("5","渐入");
	
	private String code;
	private String desc;
	
	private AnimationTypeEnum(String code,String desc){
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
	
	public static List<AnimationTypeEnum> getAll(){
		return Lists.newArrayList(AnimationTypeEnum.values());
	}
	
	public static AnimationTypeEnum getByCode(String code){
		for(AnimationTypeEnum animationType : getAll()){
			if(animationType.code.equals(code)){
				return animationType;
			}
		}
		return null;
	}
	
	public static AnimationTypeEnum getByDesc(String desc){
		for(AnimationTypeEnum animationType : getAll()){
			if(animationType.desc.equals(desc)){
				return animationType;
			}
		}
		return null;
	}
	
	public static AnimationTypeEnum getByText(String text){
		if(getByCode(text) == null) {
			return getByDesc(text);
		}
		return getByCode(text);
	}
}
