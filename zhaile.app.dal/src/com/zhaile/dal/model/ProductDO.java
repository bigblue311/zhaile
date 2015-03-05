package com.zhaile.dal.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;

import com.victor.framework.common.tools.BigDecimalTools;
import com.victor.framework.common.tools.DateTools;
import com.victor.framework.common.tools.LogTools;
import com.victor.framework.common.tools.StringTools;
import com.victor.framework.dal.basic.EntityDO;
import com.zhaile.dal.enumerate.ForeignKeyEnum;

public class ProductDO extends EntityDO implements Serializable{	
	private static final long serialVersionUID = 8997810580276732087L;
	private String name;		//名称
	private Double price;		//单价
	private Double	discount;	//折扣
	private Date discountFrom;  //折扣开始时间
	private Date discountEnd;	//折扣结束时间
	private String imgS;		//小图片路径
	private String imgM;		//中等图片路径
	private String imgL;		//大型图片路径
	private Date validFrom;		//有效日期开始
	private Date validTo;		//有效日期结束
	private String description;	//描述
	private String title;		//标题
	private String gmtOpen;		//营业时间
	private String gmtClose;	//营业结束时间
	private Long shopId;		//店家ID
	private Long categoryId;	//类别ID
	private String pinyin;		//拼音
	private String enable;		//是否在线 0：在线 1：下架
	
	private final LogTools log = new LogTools(ProductDO.class);
	
	public ForeignKeyEnum getForeignKeyType(){
		return ForeignKeyEnum.商品;
	}
	
	public BigDecimal getPriceAfterDiscount(){
		try {
			if (getPrice() == null) {
				return null;
			}
			if (isInDiscount()) {
				return BigDecimalTools.getBigDecimal(BigDecimalTools.getBigDecimal(new BigDecimal(getPrice())).multiply(
						new BigDecimal(discount)));
			}
			return new BigDecimal(getPrice());
		} catch (Exception e) {
			log.error("获取折扣价格异常",this,e);
			return new BigDecimal(getPrice());
		}
	}
	
	public boolean isValid(){
		try {
			return DateTools.inRange(validFrom, validTo) && DateTools.inTime(gmtOpen, gmtClose);
		} catch (Exception e) {
			log.error("获取商品是否有效异常",this,e);
			e.printStackTrace();
			return true;
		}
	}
	
	public boolean isInDiscount(){
		if(discount == null) {
			return false;
		}
		return DateTools.inRange(discountFrom, discountEnd);
	}
	
	public boolean isNew(){
		try {
			return DateTools.daysBetween(super.getGmtCreate(), DateTools.today())<5;
		} catch (ParseException e) {
			log.error("获取是否为新商品异常",this,e);
			return false;
		}
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Double getDiscount() {
		return discount;
	}
	public void setDiscount(Double discount) {
		this.discount = discount;
	}
	public Date getDiscountFrom() {
		return discountFrom;
	}
	public void setDiscountFrom(Date discountFrom) {
		this.discountFrom = discountFrom;
	}
	public Date getDiscountEnd() {
		return discountEnd;
	}
	public void setDiscountEnd(Date discountEnd) {
		this.discountEnd = discountEnd;
	}
	public String getImgS() {
		if(StringTools.isEmpty(imgS)){
			return "/img/default_zhaile.jpg";
		}
		return imgS;
	}
	public void setImgS(String imgS) {
		this.imgS = imgS;
	}
	public String getImgM() {
		return imgM;
	}
	public void setImgM(String imgM) {
		this.imgM = imgM;
	}
	public String getImgL() {
		return imgL;
	}
	public void setImgL(String imgL) {
		this.imgL = imgL;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	public Long getShopId() {
		return shopId;
	}
	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
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
}
