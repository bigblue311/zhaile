package com.zhaile.dal.model;

import java.io.Serializable;

import com.victor.framework.dal.basic.EntityDO;
import com.zhaile.dal.enumerate.ForeignKeyEnum;

public class ShopTagDO extends EntityDO implements Serializable{
	private static final long serialVersionUID = -6421300134031362526L;
	private Long foreignId;			//外键
	private String foreignKeyType;	//外键类型
	private String content;			//内容
	private Long createdById;		//创建人ID
	private String createdByType;	//创建人类型
	private Long searchCount;		//被搜索次数
	
	public void translate(){
		this.foreignKeyType = ForeignKeyEnum.getByCode(this.foreignKeyType)==null?this.foreignKeyType:ForeignKeyEnum.getByCode(this.foreignKeyType).getDesc();
		this.createdByType = ForeignKeyEnum.getByCode(this.createdByType)==null?this.createdByType:ForeignKeyEnum.getByCode(this.createdByType).getDesc();
	}
	
	public void format(){
		this.foreignKeyType = ForeignKeyEnum.getByDesc(this.foreignKeyType)==null?this.foreignKeyType:ForeignKeyEnum.getByDesc(this.foreignKeyType).getCode();
		this.createdByType = ForeignKeyEnum.getByDesc(this.createdByType)==null?this.createdByType:ForeignKeyEnum.getByDesc(this.createdByType).getCode();
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Long getCreatedById() {
		return createdById;
	}
	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}
	public String getCreatedByType() {
		return createdByType;
	}
	public void setCreatedByType(String createdByType) {
		this.createdByType = createdByType;
	}
	public Long getSearchCount() {
		return searchCount;
	}
	public void setSearchCount(Long searchCount) {
		this.searchCount = searchCount;
	}
}
