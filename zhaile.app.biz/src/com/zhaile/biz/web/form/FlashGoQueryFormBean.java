package com.zhaile.biz.web.form;

import java.util.Date;

import com.zhaile.dal.query.condition.FlashGoQueryCondition;

public class FlashGoQueryFormBean extends QueryFormBean{
	
	protected Date   openStart;
	protected Date   openEnd;
	protected String gmtOpenStart;
	protected String gmtOpenEnd;
	private int page = 1;
	private int pageSize = 7;

	public Date getOpenStart() {
		return openStart;
	}
	public void setOpenStart(Date openStart) {
		this.openStart = openStart;
	}
	public Date getOpenEnd() {
		return openEnd;
	}
	public void setOpenEnd(Date openEnd) {
		this.openEnd = openEnd;
	}
	public String getGmtOpenStart() {
		return gmtOpenStart;
	}
	public void setGmtOpenStart(String gmtOpenStart) {
		this.openStart = StringToDate(gmtOpenStart);
		this.gmtOpenStart = gmtOpenStart;
	}
	public String getGmtOpenEnd() {
		return gmtOpenEnd;
	}
	public void setGmtOpenEnd(String gmtOpenEnd) {
		this.openEnd = StringToDate(gmtOpenEnd);
		this.gmtOpenEnd = gmtOpenEnd;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	
	public FlashGoQueryCondition getFlashGoQueryCondition(){
		FlashGoQueryCondition queryCondition = new FlashGoQueryCondition();
		if(openStart!=null){
			queryCondition.gmtOpenStart(openStart);
		}
		if(openEnd!=null){
			queryCondition.gmtOpenEnd(openEnd);
		}
		queryCondition.pageSize(pageSize).start((page-1)*pageSize);
		return queryCondition;
	}
}
