package com.zhaile.biz.web.form;

import com.victor.framework.common.tools.StringTools;
import com.zhaile.dal.query.condition.PaymentQueryCondition;

public class PaymentQueryFormBean extends QueryFormBean{
	private Long id;
	private String customerName;
	private String shopName;
	private String prodName;
	private String status;
	private Long employeeId;
	private String source;
	private int page = 1;
	private int pageSize = 20;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getProdName() {
		return prodName;
	}
	public void setProdName(String prodName) {
		this.prodName = prodName;
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
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	
	public PaymentQueryCondition getPaymentQueryCondition(){
		PaymentQueryCondition queryCondition = new PaymentQueryCondition();
		if(StringTools.isNotEmpty(customerName)){
			queryCondition.customerName(customerName);
		}
		if(StringTools.isNotEmpty(shopName)){
			queryCondition.shopName(shopName);
		}
		if(StringTools.isNotEmpty(prodName)){
			queryCondition.prodName(prodName);
		}
		if(StringTools.isNotEmpty(status)){
			queryCondition.status(status);
		}
		if(StringTools.isNotEmpty(source)){
			queryCondition.source(source);
		}
		if(id!=null){
			queryCondition.id(id);
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
		queryCondition.pageSize(pageSize).start((page-1)*pageSize);
		return queryCondition;
	}
}
