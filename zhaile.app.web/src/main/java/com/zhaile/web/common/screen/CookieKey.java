package com.zhaile.web.common.screen;

public class CookieKey {
	public static final String MAP_MYPOINT_LNG = "map.myPoint.lng";
	public static final String MAP_MYPOINT_LAT = "map.myPoint.lat";
	
	public static final String MAP_SHOP_SHOP_SHOPID(Long shopId){
		return "map.shop."+shopId;
	}
}
