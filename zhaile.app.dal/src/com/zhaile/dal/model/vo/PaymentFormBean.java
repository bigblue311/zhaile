package com.zhaile.dal.model.vo;

import com.victor.framework.common.tools.SecurityTools;
import com.victor.framework.common.tools.StringTools;
import com.zhaile.dal.enumerate.PaymentStatusEnum;
import com.zhaile.dal.enumerate.PaymentTypeEnum;
import com.zhaile.dal.model.MapPoiDO;
import com.zhaile.dal.model.PaymentDO;

public class PaymentFormBean {
	private Double charge;
	private String comment;
	private String contact;
	private String smsMobile;
	private String source;
	private String mapAdd;
	private Double lng;			//地图坐标lng
	private Double lat;			//地图坐标lat
	private String ip;			//请求的IP
	private String paymentType;	//支付方式
	
	public PaymentDO getPaymentDO(){
		PaymentDO paymentDO = new PaymentDO();
		paymentDO.setCharge(charge);
		paymentDO.setComment(comment);
		paymentDO.setContact(contact);
		paymentDO.setSmsMobile(smsMobile);
		paymentDO.setSource(source);
		paymentDO.setMapAdd(mapAdd);
		paymentDO.setLng(lng);
		paymentDO.setLat(lat);
		paymentDO.setIp(ip);
		paymentDO.setPaymentType(paymentType);
		//为了保证数据的完整性，强制初始值
		if(StringTools.isEmpty(paymentDO.getPaymentType())){
			paymentDO.setPaymentType(PaymentTypeEnum.货到付款.getCode());
		}
		if(paymentDO.getPaymentType().equals(PaymentTypeEnum.货到付款.getCode())){
			paymentDO.setPaymentStatus(PaymentStatusEnum.货到付款.getCode());
		} else {
			paymentDO.setPaymentStatus(PaymentStatusEnum.未支付.getCode());
		}
		return paymentDO;
	}
	
	public MapPoiDO getMapPoiDO(){
		MapPoiDO mapPoiDO = new MapPoiDO();
		mapPoiDO.setLat(lat);
		mapPoiDO.setLng(lng);
		mapPoiDO.setIp(ip);
		return mapPoiDO;
	}
	
	public Double getCharge() {
		return charge;
	}
	public void setCharge(Double charge) {
		this.charge = charge;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		SecurityTools.injectionFilter(comment);
		this.comment = comment;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		SecurityTools.injectionFilter(contact);
		this.contact = contact;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getSmsMobile() {
		return smsMobile;
	}
	public void setSmsMobile(String smsMobile) {
		this.smsMobile = smsMobile;
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
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
}
