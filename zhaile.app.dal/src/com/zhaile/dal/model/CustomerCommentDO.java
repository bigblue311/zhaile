package com.zhaile.dal.model;

import java.io.Serializable;

import com.victor.framework.dal.basic.EntityDO;
import com.zhaile.dal.enumerate.StarEnum;
import com.zhaile.dal.enumerate.LevelEnum;

public class CustomerCommentDO extends EntityDO implements Serializable {
	
	private static final long serialVersionUID = 8329796854200515311L;
	private Long customerId;		//客户ID
	private String star;			//打分 1-5
	private String content;			//评价内容
	private String likeLevel;		//评级 0-喜欢 1-一般 2-不喜欢
	private Long prodId;			//产品ID
	private Long shopId;			//店铺ID
	private String ip;
	
	public void translate(){
		this.star = StarEnum.getByCode(this.star)==null?this.star:StarEnum.getByCode(this.star).getDesc();
		this.likeLevel = LevelEnum.getByCode(this.likeLevel)==null?this.likeLevel:LevelEnum.getByCode(this.likeLevel).getDesc();
	}
	
	public void format(){
		this.star = StarEnum.getByDesc(this.star)==null?this.star:StarEnum.getByDesc(this.star).getCode();
		this.likeLevel = LevelEnum.getByDesc(this.likeLevel)==null?this.likeLevel:LevelEnum.getByDesc(this.likeLevel).getCode();
	}
	
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public String getStar() {
		return star;
	}
	public void setStar(String star) {
		this.star = star;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getLikeLevel() {
		return likeLevel;
	}
	public void setLikeLevel(String likeLevel) {
		this.likeLevel = likeLevel;
	}
	public Long getProdId() {
		return prodId;
	}
	public void setProdId(Long prodId) {
		this.prodId = prodId;
	}
	public Long getShopId() {
		return shopId;
	}
	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
}
