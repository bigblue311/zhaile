package com.zhaile.biz.web.model;

import java.io.Serializable;
import java.util.List;

import com.zhaile.dal.model.CustomerCreditDO;
import com.zhaile.dal.model.CustomerDO;
import com.zhaile.dal.model.LogCreditDO;
import com.zhaile.dal.model.PeopleContactDO;
import com.zhaile.dal.model.ProductDO;
import com.zhaile.dal.model.ShopDO;

public class CustomerVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9190058749188087666L;
	private CustomerDO customer;
	private List<PeopleContactDO> contactList;
	private CustomerCreditDO credit;
	private List<LogCreditDO> creditLogList;
	private List<ShopDO> shopList;
	private List<ProductDO> prodList;
	
	public CustomerDO getCustomer() {
		return customer;
	}
	public void setCustomer(CustomerDO customer) {
		this.customer = customer;
	}
	public List<PeopleContactDO> getContactList() {
		return contactList;
	}
	public void setContactList(List<PeopleContactDO> contactList) {
		this.contactList = contactList;
	}
	public CustomerCreditDO getCredit() {
		return credit;
	}
	public void setCredit(CustomerCreditDO credit) {
		this.credit = credit;
	}
	public List<LogCreditDO> getCreditLogList() {
		return creditLogList;
	}
	public void setCreditLogList(List<LogCreditDO> creditLogList) {
		this.creditLogList = creditLogList;
	}
	public List<ShopDO> getShopList() {
		return shopList;
	}
	public void setShopList(List<ShopDO> shopList) {
		this.shopList = shopList;
	}
	public List<ProductDO> getProdList() {
		return prodList;
	}
	public void setProdList(List<ProductDO> prodList) {
		this.prodList = prodList;
	}
}
