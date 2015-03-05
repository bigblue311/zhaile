package com.zhaile.biz.web.model;

import com.zhaile.dal.model.CouponMetaDO;
import com.zhaile.dal.model.ShopDO;

public class CouponMetaVO {
	private CouponMetaDO couponMeta;
	private ShopDO shop;
	public CouponMetaDO getCouponMeta() {
		return couponMeta;
	}
	public void setCouponMeta(CouponMetaDO couponMeta) {
		this.couponMeta = couponMeta;
	}
	public ShopDO getShop() {
		return shop;
	}
	public void setShop(ShopDO shop) {
		this.shop = shop;
	}
}
