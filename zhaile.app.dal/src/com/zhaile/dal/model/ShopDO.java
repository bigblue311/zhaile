package com.zhaile.dal.model;

import java.io.Serializable;
import java.text.ParseException;
import java.util.List;

import com.google.common.collect.Lists;
import com.victor.framework.common.tools.DateTools;
import com.victor.framework.common.tools.LogTools;
import com.victor.framework.common.tools.StringTools;
import com.victor.framework.dal.basic.EntityDO;
import com.zhaile.dal.enumerate.EnableEnum;
import com.zhaile.dal.enumerate.ForeignKeyEnum;

public class ShopDO extends EntityDO implements Serializable{
	private static final long serialVersionUID = 8782020674448342235L;
	
	private String name;		//店铺名称
	private String gmtOpen;		//营业开始时间
	private String gmtClose;	//营业结束时间
	private Long customerId;	//用户ID
	private String description;	//店铺描述
	private String shopImage;	//店铺图片
	private Double charge;		//外卖费
	private String alphabet;	//字母
	private String pinyin;		//拼音
	private String enable;		//是否在线 0：在线 1：下架
	private Double lng;			//百度地图坐标
	private Double lat;			//百度地图坐标
	private String cid1;		//手机推送绑定ID
	private String cid2;		//手机推送绑定ID
	private String cid3;		//手机推送绑定ID
	private String cid4;		//手机推送绑定ID
	private String cid5;		//手机推送绑定ID
	private Double distance;	//起送距离
	private Double price;		//起送价格
	private String licenseType;	//证件类型
	private String licenseImg;	//证件图片
	
	private final LogTools log = new LogTools(ShopDO.class);
	
	public ForeignKeyEnum getForeignKeyType(){
		return ForeignKeyEnum.商家;
	}
	
	public boolean isValid(){
		try {
			if(enable.equals(EnableEnum.有效.getCode())){
				return DateTools.inTime(gmtOpen, gmtClose);
			}
			return false;
		} catch (ParseException e) {
			log.error("获取店铺是否有效异常",this,e);
			return false;
		}
	}
	
	public boolean isNew(){
		try {
			return DateTools.daysBetween(super.getGmtCreate(), DateTools.today())<5;
		} catch (ParseException e) {
			log.error("获取是否为新店铺异常",this,e);
			return false;
		}
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Double getCharge() {
		return charge;
	}
	public void setCharge(Double charge) {
		this.charge = charge;
	}

	public String getShopImage() {
		if(StringTools.isEmpty(shopImage)){
			return "/img/default_zhaile.jpg";
		}
		return shopImage;
	}
	public void setShopImage(String shopImage) {
		this.shopImage = shopImage;
	}

	public String getAlphabet() {
		return alphabet;
	}

	public void setAlphabet(String alphabet) {
		this.alphabet = alphabet;
	}

	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	public String getEnable() {
		return enable;
	}

	public void setEnable(String enable) {
		this.enable = enable;
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
	
	public String getLicenseType() {
		return licenseType;
	}

	public void setLicenseType(String licenseType) {
		this.licenseType = licenseType;
	}

	public String getLicenseImg() {
		return licenseImg;
	}

	public void setLicenseImg(String licenseImg) {
		this.licenseImg = licenseImg;
	}

	public List<String> getCid(){
		List<String> cid = Lists.newArrayList();
		if(StringTools.isNotEmpty(cid1)) cid.add(cid1);
		if(StringTools.isNotEmpty(cid2)) cid.add(cid2);
		if(StringTools.isNotEmpty(cid3)) cid.add(cid3);
		if(StringTools.isNotEmpty(cid4)) cid.add(cid4);
		if(StringTools.isNotEmpty(cid5)) cid.add(cid5);
		return cid;
	}
}
