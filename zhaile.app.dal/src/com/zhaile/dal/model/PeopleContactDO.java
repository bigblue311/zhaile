package com.zhaile.dal.model;

import java.io.Serializable;

import com.victor.framework.common.tools.StringTools;
import com.victor.framework.dal.basic.EntityDO;
import com.zhaile.dal.enumerate.ForeignKeyEnum;
import com.zhaile.dal.enumerate.GenderEnum;

public class PeopleContactDO extends EntityDO implements Serializable{
	private static final long serialVersionUID = -752473204835980820L;
	private Long foreignId;		//外键ID
	private String mobile;		//手机
	private String address;		//地址
	private String phone;		//电话
	private String foreignKeyType;	//联系人类型 0-客户 1-店家
	private String name;		//称呼
	private String gender;		//性别 0-先生 1-女士
	private String foreignKey;	//外键
	
	public void translate(){
		this.foreignKeyType = ForeignKeyEnum.getByCode(this.foreignKeyType)==null?this.foreignKeyType:ForeignKeyEnum.getByCode(this.foreignKeyType).getDesc();
		this.gender = GenderEnum.getByCode(this.gender)==null?this.gender:GenderEnum.getByCode(this.gender).getDesc();
	}
	
	public void format(){
		this.foreignKeyType = ForeignKeyEnum.getByDesc(this.foreignKeyType)==null?this.foreignKeyType:ForeignKeyEnum.getByDesc(this.foreignKeyType).getCode();
		this.gender = GenderEnum.getByDesc(this.gender)==null?this.gender:GenderEnum.getByDesc(this.gender).getCode();
	}
	
	public Long getForeignId() {
		return foreignId;
	}
	public void setForeignId(Long foreignId) {
		this.foreignId = foreignId;
	}
	public String getMobile() {
		return StringTools.isEmpty(mobile)?"":mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getAddress() {
		return StringTools.isEmpty(address)?"":address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return StringTools.isEmpty(phone)?"":phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getForeignKeyType() {
		return foreignKeyType;
	}
	public void setForeignKeyType(String foreignKeyType) {
		this.foreignKeyType = foreignKeyType;
	}
	public String getName() {
		return StringTools.isEmpty(name)?"":name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return StringTools.isEmpty(gender)?"0":gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getForeignKey() {
		return foreignKey;
	}

	public void setForeignKey(String foreignKey) {
		this.foreignKey = foreignKey;
	}
	
	public String toRevAddress(){
		String sex = GenderEnum.getByCode(this.gender)==null?"":GenderEnum.getByCode(this.gender).getDesc();
		String mobilephone = StringTools.isEmpty(mobile)?"":mobile + "(手机)";
		String telephone = StringTools.isEmpty(phone)?"":phone + "(座机)";
		String contact = address + " " + name + " " + sex + " " + mobilephone + " " + telephone + " ";
		return contact;
	}
}
