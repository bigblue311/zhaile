package com.zhaile.web.webpage.screen.json;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.victor.framework.dal.basic.Paging;
import com.zhaile.biz.web.manager.ShopManager;
import com.zhaile.biz.web.model.ShopVO;
import com.zhaile.web.common.screen.DefaultLayout;

public class Shops extends DefaultLayout {
	
	@Autowired
	private ShopManager shopManager;
	
	public Paging<ShopVO> execute(@Param("page") Integer page,
						@Param("pageSize") Integer pageSize,
						@Param("shopCateId") Long shopCateId,
						Context context) throws IOException {
    	Double lng = super.getLng();
		Double lat = super.getLat();
		if(page == null) page = 1;
		if(pageSize == null || pageSize<=0) pageSize = 10;
		Paging<ShopVO> shopList = Paging.emptyPage();
		if(shopCateId == null){
			shopList = shopManager.getAllByDistance(lng, lat, page, pageSize);
		} else {
			shopList = shopManager.getShopVOByCateId(shopCateId,lng,lat,page,pageSize);
		}
		return shopList;
    }
}
