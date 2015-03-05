package com.zhaile.dal.model;

import java.io.Serializable;

import com.victor.framework.dal.basic.EntityDO;

public class ShopCategoryDO extends EntityDO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2299150249124019661L;
	
	private Long shopId;	//店铺ID
	private Long cateId;	//店铺分类ID
	
	public Long getShopId() {
		return shopId;
	}
	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}
	public Long getCateId() {
		return cateId;
	}
	public void setCateId(Long cateId) {
		this.cateId = cateId;
	}
}
