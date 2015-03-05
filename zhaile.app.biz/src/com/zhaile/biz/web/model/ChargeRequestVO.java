package com.zhaile.biz.web.model;

import java.util.Collection;
import java.util.Map;

import com.zhaile.dal.model.CustomerDO;
import com.zhaile.dal.model.ShopDO;

public class ChargeRequestVO {
	private CustomerDO loginUser;
	private Collection<ShopDO> list;
	private int defaultCharge;
	private Long zhaileShopId; 
	private Boolean firstFree;
	private Map<Long,Double> distanceMap;
	 
	public CustomerDO getLoginUser() {
		return loginUser;
	}
	public void setLoginUser(CustomerDO loginUser) {
		this.loginUser = loginUser;
	}
	public Collection<ShopDO> getList() {
		return list;
	}
	public void setList(Collection<ShopDO> list) {
		this.list = list;
	}
	public int getDefaultCharge() {
		return defaultCharge;
	}
	public void setDefaultCharge(int defaultCharge) {
		this.defaultCharge = defaultCharge;
	}
	public Long getZhaileShopId() {
		return zhaileShopId;
	}
	public void setZhaileShopId(Long zhaileShopId) {
		this.zhaileShopId = zhaileShopId;
	}
	public Boolean getFirstFree() {
		return firstFree;
	}
	public void setFirstFree(Boolean firstFree) {
		this.firstFree = firstFree;
	}
	public Map<Long, Double> getDistanceMap() {
		return distanceMap;
	}
	public void setDistanceMap(Map<Long, Double> distanceMap) {
		this.distanceMap = distanceMap;
	}
}
