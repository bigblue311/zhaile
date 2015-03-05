package com.zhaile.dal.model;

import java.io.Serializable;
import java.util.Date;

import com.victor.framework.dal.basic.EntityDO;

public class FlashGoDO extends EntityDO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2854970104265417337L;
	
	private Long prodId;		//商品ID
	private Date gmtOpen;		//活动开始时间
	private Date gmtClose;		//活动结束时间
	private Double price;		//价格
	private int limitBuy;		//每人限购
	private int total;			//商品总数
	private String adImg;		//活动图片
	private int sold;			//卖出了几个
	
	public Long getProdId() {
		return prodId;
	}
	public void setProdId(Long prodId) {
		this.prodId = prodId;
	}
	public Date getGmtOpen() {
		return gmtOpen;
	}
	public void setGmtOpen(Date gmtOpen) {
		this.gmtOpen = gmtOpen;
	}
	public Date getGmtClose() {
		return gmtClose;
	}
	public void setGmtClose(Date gmtClose) {
		this.gmtClose = gmtClose;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public int getLimitBuy() {
		return limitBuy;
	}
	public void setLimitBuy(int limitBuy) {
		this.limitBuy = limitBuy;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public String getAdImg() {
		return adImg;
	}
	public void setAdImg(String adImg) {
		this.adImg = adImg;
	}
	public int getSold() {
		return sold;
	}
	public void setSold(int sold) {
		this.sold = sold;
	}
}
