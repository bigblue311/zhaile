package com.zhaile.biz.web.form;

import com.victor.framework.common.tools.StringTools;
import com.zhaile.dal.query.condition.OrderQueryCondition;

public class OrderQueryFormBean extends QueryFormBean{
	private String customerName;
	private String shopName;
	private String status;
	private Long   employeeId;
	private String source;
	
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Long getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	
	public OrderQueryCondition getOrderQueryCondition(){
		OrderQueryCondition queryCondition = new OrderQueryCondition();
		if(StringTools.isNotEmpty(customerName)){
			queryCondition.customerName(customerName);
		}
		if(StringTools.isNotEmpty(status)){
			queryCondition.status(status);
		}
		if(StringTools.isNotEmpty(source)){
			queryCondition.source(source);
		}
		if(StringTools.isNotEmpty(shopName)){
			queryCondition.shopName(shopName);
		}
		if(employeeId!=null){
			queryCondition.employeeId(employeeId);
		}
		if(modifyStart!=null){
			queryCondition.gmtModifyStart(modifyStart);
		}
		if(modifyEnd!=null){
			queryCondition.gmtModifyEnd(modifyEnd);
		}
		return queryCondition;
	}
}
