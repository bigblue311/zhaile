package com.zhaile.dal.model;

import java.io.Serializable;

import com.victor.framework.dal.basic.EntityDO;

public class SystemConfigDO extends EntityDO implements Serializable{
	private static final long serialVersionUID = 4651348486645150524L;
	private String key; 	//KEY
	private String value;	//value
	private Long foreignId;	//外键ID
	private String foreignKeyType;	//外键类型
	private String description;	//描述
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Long getForeignId() {
		return foreignId;
	}
	public void setForeignId(Long foreignId) {
		this.foreignId = foreignId;
	}
	public String getForeignKeyType() {
		return foreignKeyType;
	}
	public void setForeignKeyType(String foreignKeyType) {
		this.foreignKeyType = foreignKeyType;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
