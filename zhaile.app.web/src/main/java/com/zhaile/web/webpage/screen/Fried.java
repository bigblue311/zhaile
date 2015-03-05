package com.zhaile.web.webpage.screen;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.victor.framework.dal.basic.Paging;
import com.zhaile.biz.common.Cache;
import com.zhaile.biz.web.manager.ShopManager;
import com.zhaile.biz.web.model.ShopVO;
import com.zhaile.dal.enumerate.ShopCategoryEnum;
import com.zhaile.web.common.screen.DefaultLayout;

public class Fried extends DefaultLayout {
	
	@Autowired
	private Cache cache;
	
	@Autowired
	private ShopManager shopManager;
	
	public void execute(@Param("page") Integer page,
						Context context) {
    	super.load(context,null);
    	
    	cache.getAdvertisementcache().click(cache.getAdvertisementcache().getAd(28).getId());
    	
    	context.put("menuN","28");
    	context.put("pageTarget","fried");
    	context.put("title", "富阳宅乐网-中式炒菜菜店铺");
    	
    	Double lng = super.getLng();
		Double lat = super.getLat();
		if(page == null) page = 1;
    	
		Paging<ShopVO> shopList = shopManager.getShopVOByCateId(ShopCategoryEnum.中式炒菜.getId(),lng,lat,page,10);
		
		context.put("shopList", shopList);
		context.put("ShopCategoryEnum", ShopCategoryEnum.getAll());
		context.put("SelectedCate", ShopCategoryEnum.中式炒菜.getId());
    }
}
