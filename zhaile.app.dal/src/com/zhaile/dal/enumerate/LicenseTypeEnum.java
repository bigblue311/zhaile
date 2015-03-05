package com.zhaile.dal.enumerate;

import java.util.List;

import com.google.common.collect.Lists;

public enum LicenseTypeEnum {
	企业注册("0","企业注册"),
	个人注册("1","个人注册");
	
	private String code;
	private String desc;
	
	private LicenseTypeEnum(String code,String desc){
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
	
	public static List<LicenseTypeEnum> getAll(){
		return Lists.newArrayList(LicenseTypeEnum.values());
	}
	
	public static LicenseTypeEnum getByCode(String code){
		for(LicenseTypeEnum licenseType : getAll()){
			if(licenseType.code.equals(code)){
				return licenseType;
			}
		}
		return null;
	}
	
	public static LicenseTypeEnum getByDesc(String desc){
		for(LicenseTypeEnum licenseType : getAll()){
			if(licenseType.desc.equals(desc)){
				return licenseType;
			}
		}
		return null;
	}
	
	public static LicenseTypeEnum getByText(String text){
		if(getByCode(text) == null) {
			return getByDesc(text);
		}
		return getByCode(text);
	}
}
