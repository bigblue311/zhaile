package com.zhaile.dal.model.vo;

import java.text.ParseException;
import java.util.Date;

import com.victor.framework.common.tools.DateTools;
import com.zhaile.dal.enumerate.MapMarkerStatusEnum;
import com.zhaile.dal.model.MapMarkerDO;

public class MapMarkerFormBean {
	private String content;
	private String validFrom;
	private String validTo;
	private String contact;
	private Double lng;
	private Double lat;
	private String title;
	private String icon;
	private String address;
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getValidFrom() {
		return validFrom;
	}
	public void setValidFrom(String validFrom) {
		this.validFrom = validFrom;
	}
	public String getValidTo() {
		return validTo;
	}
	public void setValidTo(String validTo) {
		this.validTo = validTo;
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
	
	public MapMarkerDO toDO() throws ParseException{
		MapMarkerDO mapMarkerDO = new MapMarkerDO();
		mapMarkerDO.setContent(content);
		mapMarkerDO.setTitle(title);
		mapMarkerDO.setContact(contact);
		mapMarkerDO.setLng(lng);
		mapMarkerDO.setLat(lat);
		mapMarkerDO.setIcon(icon);
		mapMarkerDO.setAddress(address);
		Date validFromD = DateTools.getDayBegin(DateTools.StringToDate(validFrom));
		Date validToD = DateTools.getDayEnd(DateTools.StringToDate(validTo));
		if(validFromD == null || validToD == null) return null;
		mapMarkerDO.setValidFrom(validFromD);
		mapMarkerDO.setValidTo(validToD);
		if(validFromD.after(validToD)) return null;
		int dateBetween = DateTools.daysBetween(validFromD, validToD);
		mapMarkerDO.setCharge(dateBetween*20d);
		mapMarkerDO.setStatus(MapMarkerStatusEnum.未处理.getCode());
		return mapMarkerDO;
	}
}
