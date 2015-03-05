package com.zhaile.web.webpage.screen;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.victor.framework.dal.basic.Paging;
import com.zhaile.biz.web.manager.ShopManager;
import com.zhaile.biz.web.model.ShopVO;
import com.zhaile.dal.enumerate.ShopCategoryEnum;
import com.zhaile.web.common.screen.DefaultLayout;

public class Shops extends DefaultLayout {
	
	@Autowired
	private ShopManager shopManager;
	
	public void execute(@Param("page") Integer page,
						@Param("shopCateId") Long shopCateId,
						Context context) {
    	super.load(context,null);
    	
    	context.put("pageTarget","shops");
    	context.put("title", "富阳宅乐网-附近的店铺");
    	
    	Double lng = super.getLng();
		Double lat = super.getLat();
		if(page == null) page = 1;
		Paging<ShopVO> shopList = Paging.emptyPage();
		if(shopCateId == null){
			shopList = shopManager.getAllByDistance(lng, lat, page, 10);
		} else {
			shopList = shopManager.getShopVOByCateId(shopCateId,lng,lat,page,10);
		}
		
		context.put("shopList", shopList);
		context.put("ShopCategoryEnum", ShopCategoryEnum.getAll());
		context.put("SelectedCate", shopCateId);
    }
}
