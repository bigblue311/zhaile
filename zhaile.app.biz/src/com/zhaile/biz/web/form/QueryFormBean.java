package com.zhaile.biz.web.form;

import java.util.Date;

import com.victor.framework.common.tools.DateTools;

public class QueryFormBean {
	protected Date   modifyStart;
	protected Date   modifyEnd;
	protected String gmtModifyStart;
	protected String gmtModifyEnd;
	
	public Date getModifyStart() {
		return modifyStart;
	}
	public void setModifyStart(Date modifyStart) {
		this.modifyStart = modifyStart;
	}
	public Date getModifyEnd() {
		return modifyEnd;
	}
	public void setModifyEnd(Date modifyEnd) {
		this.modifyEnd = modifyEnd;
	}
	public String getGmtModifyStart() {
		return gmtModifyStart;
	}
	public void setGmtModifyStart(String gmtModifyStart) {
		this.modifyStart = StringToDate(gmtModifyStart);
		this.gmtModifyStart = gmtModifyStart;
	}
	public String getGmtModifyEnd() {
		return gmtModifyEnd;
	}
	public void setGmtModifyEnd(String gmtModifyEnd) {
		this.modifyEnd = StringToDate(gmtModifyEnd);
		this.gmtModifyEnd = gmtModifyEnd;
	}
	
	protected Date StringToDate(String date){
		Date toDate = DateTools.today();
		try {
			toDate = DateTools.StringToDate(date);
		} catch (Exception e) {
			toDate = DateTools.today();
		}
		return toDate==null?DateTools.today():toDate;
	}
}
