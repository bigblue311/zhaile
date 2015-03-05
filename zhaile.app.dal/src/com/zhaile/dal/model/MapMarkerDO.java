package com.zhaile.dal.model;

import java.io.Serializable;
import java.util.Date;

import com.victor.framework.dal.basic.EntityDO;
import com.zhaile.dal.enumerate.MapIconEnum;
import com.zhaile.dal.enumerate.MapMarkerStatusEnum;

public class MapMarkerDO extends EntityDO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5336209978402527206L;
	private String content;
	private Date validFrom;
	private Date validTo;
	private Double charge;
	private String status;
	private String contact;
	private Double lng;
	private Double lat;
	private String title;
	private String icon;
	private String address;
	
	public void translate(){
		this.status = MapMarkerStatusEnum.getByCode(this.status)==null?this.status:MapMarkerStatusEnum.getByCode(this.status).getDesc();
		this.icon = MapIconEnum.getByCode(this.icon)==null?this.icon:MapIconEnum.getByCode(this.icon).getDesc();
	}
	
	public void format(){
		this.status = MapMarkerStatusEnum.getByDesc(this.status)==null?this.status:MapMarkerStatusEnum.getByDesc(this.status).getCode();
		this.icon = MapIconEnum.getByDesc(this.icon)==null?this.icon:MapIconEnum.getByDesc(this.icon).getCode();
	}
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
}
