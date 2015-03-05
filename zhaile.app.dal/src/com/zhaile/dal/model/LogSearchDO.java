package com.zhaile.dal.model;

import java.io.Serializable;

import com.victor.framework.dal.basic.EntityDO;

public class LogSearchDO extends EntityDO implements Serializable {
	private static final long serialVersionUID = -4344779893245171075L;
	private Long logId;		//日志ID
	private String keyword;	//关键词
	private Long tagId;		//标签ID
	
	public Long getLogId() {
		return logId;
	}
	public void setLogId(Long logId) {
		this.logId = logId;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public Long getTagId() {
		return tagId;
	}
	public void setTagId(Long tagId) {
		this.tagId = tagId;
	}
}
