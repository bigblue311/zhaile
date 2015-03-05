package com.zhaile.biz.web.form;

import java.util.Date;

import com.victor.framework.common.tools.StringTools;
import com.zhaile.dal.query.condition.MapMarkerQueryCondition;

public class MapMarkerQueryFormBean extends QueryFormBean{
	private Long id;
	private String status;
	private String contact;
	protected Date   validFrom;
	protected Date   validTo;
	protected String validFromStr;
	protected String validToStr;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public Date getValidFrom() {
		return validFrom;
	}
	public void setValidFrom(Date validFrom) {
		this.validFrom = validFrom;
	}
	public Date getValidTo() {
		return validTo;
	}
	public void setValidTo(Date validTo) {
		this.validTo = validTo;
	}
	public String getValidFromStr() {
		return validFromStr;
	}
	public void setValidFromStr(String validFromStr) {
		this.validFrom = StringToDate(validFromStr);
		this.validFromStr = validFromStr;
	}
	public String getValidToStr() {
		return validToStr;
	}
	public void setValidToStr(String validToStr) {
		this.validTo = StringToDate(validToStr);
		this.validToStr = validToStr;
	}
	public MapMarkerQueryCondition getMapMarkerQueryCondition(){
		MapMarkerQueryCondition queryCondition = new MapMarkerQueryCondition();
		if(StringTools.isNotEmpty(status)){
			queryCondition.status(status);
		}
		if(StringTools.isNotEmpty(contact)){
			queryCondition.contact(contact);
		}
		if(modifyStart!=null){
			queryCondition.gmtModifyStart(modifyStart);
		}
		if(modifyEnd!=null){
			queryCondition.gmtModifyEnd(modifyEnd);
		}
		if(validFrom!=null){
			queryCondition.validFrom(validFrom);
		}
		if(validTo!=null){
			queryCondition.validTo(validTo);
		}
		return queryCondition;
	}
}
