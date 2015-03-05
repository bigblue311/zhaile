package com.zhaile.biz.web.admin.model;

import java.util.List;

import com.google.common.collect.Lists;
import com.victor.framework.common.tools.PinYinTools;
import com.victor.framework.common.tools.StringTools;
import com.zhaile.dal.enumerate.ForeignKeyEnum;
import com.zhaile.dal.model.CustomerDO;
import com.zhaile.dal.model.PeopleContactDO;
import com.zhaile.dal.model.ShopDO;

public class ShopDetailVO {
	private Long id;
	private String shopName;
	private String shopStatus;
	private Double shopCharge;
	private String shopImage;
	private String licenseImg;
	private String licenseType;
	private String gmtOpen;
	private String gmtClose;	
	private String shopDesc;
	private Long   contactId;
	private Long   customerId;
	private String contactName;
	private String contactMobile;
	private String contactPhone;
	private String contactAddress;
	private String contactGender;
	private Double lng;
	private Double lat;
	private String cid1;		//手机推送绑定ID
	private String cid2;		//手机推送绑定ID
	private String cid3;		//手机推送绑定ID
	private String cid4;		//手机推送绑定ID
	private String cid5;		//手机推送绑定ID
	private String shopCategory;
	private Double distance;	//起送距离
	private Double price;		//起送价格
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getContactId() {
		return contactId;
	}
	public void setContactId(Long contactId) {
		this.contactId = contactId;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getShopStatus() {
		return shopStatus;
	}
	public void setShopStatus(String shopStatus) {
		this.shopStatus = shopStatus;
	}
	public String getGmtOpen() {
		return gmtOpen;
	}
	public void setGmtOpen(String gmtOpen) {
		this.gmtOpen = gmtOpen;
	}
	public String getGmtClose() {
		return gmtClose;
	}
	public void setGmtClose(String gmtClose) {
		this.gmtClose = gmtClose;
	}
	public String getShopDesc() {
		return shopDesc;
	}
	public void setShopDesc(String shopDesc) {
		this.shopDesc = shopDesc;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public String getContactMobile() {
		return contactMobile;
	}
	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	public String getContactAddress() {
		return contactAddress;
	}
	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}
	public String getContactGender() {
		return contactGender;
	}
	public void setContactGender(String contactGender) {
		this.contactGender = contactGender;
	}	
	public Double getShopCharge() {
		return shopCharge;
	}
	public void setShopCharge(Double shopCharge) {
		this.shopCharge = shopCharge;
	}
	public String getShopImage() {
		return shopImage;
	}
	public void setShopImage(String shopImage) {
		this.shopImage = shopImage;
	}
	public String getLicenseImg() {
		return licenseImg;
	}
	public void setLicenseImg(String licenseImg) {
		this.licenseImg = licenseImg;
	}
	public String getLicenseType() {
		return licenseType;
	}
	public void setLicenseType(String licenseType) {
		this.licenseType = licenseType;
	}
	public Double getLng() {
		return lng;
	}
	public void setLng(Double lng) {
		this.lng = lng;
	}
	public Double getLat() {
		return lat;
	}
	public void setLat(Double lat) {
		this.lat = lat;
	}
	public String getCid1() {
		return cid1;
	}
	public void setCid1(String cid1) {
		this.cid1 = cid1;
	}
	public String getCid2() {
		return cid2;
	}
	public void setCid2(String cid2) {
		this.cid2 = cid2;
	}
	public String getCid3() {
		return cid3;
	}
	public void setCid3(String cid3) {
		this.cid3 = cid3;
	}
	public String getCid4() {
		return cid4;
	}
	public void setCid4(String cid4) {
		this.cid4 = cid4;
	}
	public String getCid5() {
		return cid5;
	}
	public void setCid5(String cid5) {
		this.cid5 = cid5;
	}
	public String getShopCategory() {
		return shopCategory;
	}
	public void setShopCategory(String shopCategory) {
		this.shopCategory = shopCategory;
	}
	public Double getDistance() {
		return distance;
	}
	public void setDistance(Double distance) {
		this.distance = distance;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	
	public ShopDO getShopDO(){
		ShopDO shop = new ShopDO();
		shop.setId(id);
		shop.setCustomerId(customerId);
		shop.setCharge(shopCharge);
		shop.setName(shopName);
		shop.setEnable(shopStatus);
		shop.setGmtOpen(gmtOpen);
		shop.setGmtClose(gmtClose);
		shop.setDescription(shopDesc);
		shop.setShopImage(shopImage);
		shop.setLat(lat);
		shop.setLng(lng);
		shop.setCid1(cid1);
		shop.setCid2(cid2);
		shop.setCid3(cid3);
		shop.setCid4(cid4);
		shop.setCid5(cid5);
		shop.setDistance(distance);
		shop.setPrice(price);
		shop.setLicenseImg(licenseImg);
		shop.setLicenseType(licenseType);
		return shop;
	}
	
	public CustomerDO getCustomerDO(){
		CustomerDO customer = new CustomerDO();
		customer.setId(customerId);
		if(StringTools.isNotEmpty(shopName)){
			customer.setName(PinYinTools.getPinYin(shopName).toLowerCase());
		}
		customer.setMobile(contactMobile);
		return customer;
	}
	
	public PeopleContactDO getPeopleContactDO(){
		PeopleContactDO peopleContact = new PeopleContactDO();
		peopleContact.setId(contactId);
		peopleContact.setName(contactName);
		peopleContact.setMobile(contactMobile);
		peopleContact.setPhone(contactPhone);
		peopleContact.setAddress(contactAddress);
		peopleContact.setGender(contactGender);
		peopleContact.setForeignId(id);
		peopleContact.setForeignKeyType(ForeignKeyEnum.商家.getCode());
		return peopleContact;
	}
	
	public List<Long> getShopCates(){
		if(shopCategory == null) {
			return Lists.newArrayList();
		}
		String[] spt = shopCategory.split(",");
		List<Long> list = Lists.newArrayList();
		for(String each:spt){
			if(StringTools.isNotEmpty(each)){
				try{
					list.add(Long.parseLong(each));
				} catch(Exception ex){
					continue;
				}
			}
		}
		return list;
		
	}
}
