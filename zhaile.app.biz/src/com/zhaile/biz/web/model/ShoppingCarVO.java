package com.zhaile.biz.web.model;

import java.io.Serializable;

import com.zhaile.dal.model.ShoppingCarDO;

public class ShoppingCarVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 941789920139816134L;
	
	private ShoppingCarDO shopCar;
	private ProductVO productVO;
	private Double totalPrice;
	private Double credit;
	private Boolean isFlashGo = false;
	private int limitBuy = 999; //库存数量
	
	public ShoppingCarDO getShopCar() {
		return shopCar;
	}
	public void setShopCar(ShoppingCarDO shopCar) {
		this.shopCar = shopCar;
	}
	public ProductVO getProductVO() {
		return productVO;
	}
	public void setProductVO(ProductVO productVO) {
		this.productVO = productVO;
	}
	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public void setCredit(Double credit) {
		this.credit = credit;
	}
	public Double getTotalPrice() {
		return totalPrice;
	}
	public Double getCredit() {
		return credit;
	}
	public Boolean getIsFlashGo() {
		return isFlashGo;
	}
	public void setIsFlashGo(Boolean isFlashGo) {
		this.isFlashGo = isFlashGo;
	}
	public int getLimitBuy() {
		return limitBuy;
	}
	public void setLimitBuy(int limitBuy) {
		this.limitBuy = limitBuy;
	}
}
