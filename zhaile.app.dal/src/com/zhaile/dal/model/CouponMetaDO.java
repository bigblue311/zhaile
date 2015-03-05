package com.zhaile.dal.model;

import java.io.Serializable;
import java.util.Date;

import com.victor.framework.common.tools.DateTools;
import com.victor.framework.common.tools.StringTools;
import com.victor.framework.dal.basic.EntityDO;
import com.zhaile.dal.enumerate.DateUnitEnum;
import com.zhaile.dal.enumerate.EnableEnum;

public class CouponMetaDO extends EntityDO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6125232145728925503L;
	
	private String enable;		//是否有效 0：有效1：无效
	private int validLength;	//有效期长度
	private String validUnit;	//有效期单位
	private Long shopId;		//店铺ID
	private Double amount;		//初始金额
	private int total;			//总共数量
	private String chargable;	//是否找零 0：找零1：不找零
	private String deductable;	//是否可抵外卖费 0: 可抵 1: 不可抵
	private String topupable;	//是否可充值 0：可充值1：不可充值
	private String refundable;	//是否支持退款 0：可退款1：不可退款
	private int deductCount;	//抵扣次数
	private String couponDesc;	//描述
	private Double fullsent;	//满就送 >0 就是满就送
	private Double sales;		//出售价格 >0 就可以通过购买获得
	private int limitBuy;		//每人限得张数
	private String imgSrc;		//图片路径
	private String name;		//卡的名称
	private String locked;		//是否锁定
	
	
	public boolean isValid(){
		if(locked.equals(EnableEnum.有效.getCode())){
			return false;
		}
		return enable.equals(EnableEnum.有效.getCode());
	}
	
	public String getEnable() {
		return enable;
	}
	public void setEnable(String enable) {
		this.enable = enable;
	}
	public String getLocked() {
		return locked;
	}
	public void setLocked(String locked) {
		this.locked = locked;
	}
	public int getValidLength() {
		return validLength;
	}
	public void setValidLength(int validLength) {
		this.validLength = validLength;
	}
	public String getValidUnit() {
		return validUnit;
	}
	public void setValidUnit(String validUnit) {
		this.validUnit = validUnit;
	}
	public Date getValidFrom(){
		return DateTools.today();
	}
	public Date getValidTo(){
		DateUnitEnum dateUnit = DateUnitEnum.getByCode(validUnit);
		if(dateUnit!=null){
			return DateTools.getDateTime(dateUnit.getDuration()*validLength);
		}
		return DateTools.today();
	}
	public String getDeductable() {
		return deductable;
	}
	public void setDeductable(String deductable) {
		this.deductable = deductable;
	}
	public int getDeductCount() {
		return deductCount;
	}
	public void setDeductCount(int deductCount) {
		this.deductCount = deductCount;
	}
	public Long getShopId() {
		return shopId;
	}
	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public String getChargable() {
		return chargable;
	}
	public void setChargable(String chargable) {
		this.chargable = chargable;
	}
	public String getTopupable() {
		return topupable;
	}
	public void setTopupable(String topupable) {
		this.topupable = topupable;
	}
	public String getRefundable() {
		return refundable;
	}
	public void setRefundable(String refundable) {
		this.refundable = refundable;
	}
	public String getCouponDesc() {
		return couponDesc;
	}
	public void setCouponDesc(String couponDesc) {
		this.couponDesc = couponDesc;
	}
	public Double getFullsent() {
		return fullsent;
	}
	public void setFullsent(Double fullsent) {
		this.fullsent = fullsent;
	}
	public Double getSales() {
		return sales;
	}
	public void setSales(Double sales) {
		this.sales = sales;
	}
	public int getLimitBuy() {
		return limitBuy;
	}
	public void setLimitBuy(int limitBuy) {
		this.limitBuy = limitBuy;
	}
	public String getImgSrc() {
		if(StringTools.isEmpty(imgSrc)){
			return "/img/default_zhaile_card.jpg";
		}
		return imgSrc;
	}
	public void setImgSrc(String imgSrc) {
		this.imgSrc = imgSrc;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
