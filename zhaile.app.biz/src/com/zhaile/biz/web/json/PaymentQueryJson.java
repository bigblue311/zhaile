package com.zhaile.biz.web.json;

import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;
import com.zhaile.dal.model.ShopDO;

public class PaymentQueryJson {
	private Long id;
	private Long customerId;
	private String customerName;
	private Date orderTime;
	private Double totalPrice;
	private Double charge;
	private Double received;
	private String status;
	private String comment;
	private String contact;
	private Long employeeId;
	private String source;
	private String smsStatus;
	private String smsMobile;
	private String smsText;
	private String mapAdd;
	private Double lng;
	private Double lat;
	private String paymentStatus;
	private int shopCount = 0;
	private List<ShopDO> shops;
	
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
	public Date getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}
	public Double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public Double getCharge() {
		return charge;
	}
	public void setCharge(Double charge) {
		this.charge = charge;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
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
	public Double getReceived() {
		return received;
	}
	public void setReceived(Double received) {
		this.received = received;
	}
	public String getSmsStatus() {
		return smsStatus;
	}
	public void setSmsStatus(String smsStatus) {
		this.smsStatus = smsStatus;
	}
	public String getSmsMobile() {
		return smsMobile;
	}
	public void setSmsMobile(String smsMobile) {
		this.smsMobile = smsMobile;
	}
	public String getSmsText() {
		return smsText;
	}
	public void setSmsText(String smsText) {
		this.smsText = smsText;
	}
	public String getMapAdd() {
		return mapAdd;
	}
	public void setMapAdd(String mapAdd) {
		this.mapAdd = mapAdd;
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
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public List<ShopDO> getShops() {
		return shops;
	}
	public void setShops(List<ShopDO> shops) {
		this.shops = shops;
	}
	public int getShopCount() {
		if(shops!=null && !shops.isEmpty()){
			List<Long> filter = Lists.newArrayList();
			for(ShopDO shop : shops){
				if(!filter.contains(shop.getId())){
					filter.add(shop.getId());
				}
			}
			return filter.size();
		}
		return shopCount;
	}
	public void setShopCount(int shopCount) {
		this.shopCount = shopCount;
	}
}
