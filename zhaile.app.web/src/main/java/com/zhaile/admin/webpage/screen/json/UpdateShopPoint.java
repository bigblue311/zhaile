package com.zhaile.admin.webpage.screen.json;

import java.io.IOException;


import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.zhaile.admin.webpage.filter.AdminLoginFilter;
import com.zhaile.dal.dao.ShopDAO;
import com.zhaile.dal.model.ShopDO;

public class UpdateShopPoint extends AdminLoginFilter {
	
	@Autowired
	private ShopDAO shopDAO;
	
	public Boolean execute(@Param("id") Long id,@Param("lng") Double lng,@Param("lat") Double lat) throws IOException {
		ShopDO shop = new ShopDO();
		shop.setId(id);
		shop.setLng(lng);
		shop.setLat(lat);
		return shopDAO.update(shop).getDataObject();
    }
}
