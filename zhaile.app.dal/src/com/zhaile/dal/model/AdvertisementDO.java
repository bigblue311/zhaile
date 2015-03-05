package com.zhaile.dal.model;

import java.io.Serializable;
import java.util.Date;

import com.victor.framework.common.tools.DateTools;
import com.victor.framework.common.tools.LogTools;
import com.victor.framework.dal.basic.EntityDO;
import com.zhaile.dal.enumerate.AdvertisementEnum;

public class AdvertisementDO extends EntityDO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1561246779287066288L;
	
	private String content;		//广告内容
	private String link;		//广告连接
	private Date validFrom;		//开始日期
	private Date validTo;		//结束日期
	private String gmtOpen;		//每天开始时间
	private String gmtClose;	//每天结束时间
	private String type;		//广告类型
	private int position;		//位置
	private String imgSrc;		//图片路径
	private int clickCount;		//点击次数
	
	private final LogTools log = new LogTools(AdvertisementDO.class);
	
	public void translate(){
		this.type = AdvertisementEnum.getByCode(this.type)==null?this.type:AdvertisementEnum.getByCode(this.type).getDesc();
	}
	
	public void format(){
		this.type = AdvertisementEnum.getByDesc(this.type)==null?this.type:AdvertisementEnum.getByDesc(this.type).getCode();
	}
	
	public boolean isValid(){
		try {
			return DateTools.inRange(validFrom, validTo) && DateTools.inTime(gmtOpen, gmtClose);
		} catch (Exception e) {
			log.error("获取商品是否有效异常",this,e);
			return true;
		}
	}
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}

	public String getImgSrc() {
		return imgSrc;
	}

	public void setImgSrc(String imgSrc) {
		this.imgSrc = imgSrc;
	}
	public int getClickCount() {
		return clickCount;
	}
	public void setClickCount(int clickCount) {
		this.clickCount = clickCount;
	}
}
