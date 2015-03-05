package com.zhaile.biz.web.admin.model;

import java.io.Serializable;
import java.util.List;
import com.zhaile.dal.model.ShopDO;

public class Authentication implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1678079742913244369L;
	
	public static final String ROLE_ADMIN = "ADMIN";
	public static final String ROLE_SUPER = "SUPER";
	public static final String ROLE_EMPLOYEE = "EMPLOYEE";
	public static final String ROLE_SHOP = "SHOP";
	
	private String loginId;
	private String role;
	private List<ShopDO> shops;
	private Boolean isZhaile = false;
	
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public List<ShopDO> getShops() {
		return shops;
	}
	public void setShops(List<ShopDO> shops) {
		this.shops = shops;
	}
	public Boolean getIsZhaile() {
		return isZhaile;
	}
	public void setIsZhaile(Boolean isZhaile) {
		this.isZhaile = isZhaile;
	}
}
