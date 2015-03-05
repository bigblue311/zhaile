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

public class Free extends DefaultLayout {
	
	@Autowired
	private Cache cache;
	
	@Autowired
	private ShopManager shopManager;
	
	public void execute(@Param("page") Integer page,
						Context context) {
    	super.load(context,null);
    	
    	cache.getAdvertisementcache().click(cache.getAdvertisementcache().getAd(26).getId());

    	context.put("menuN","26");
    	context.put("pageTarget","free");
    	context.put("title", "富阳宅乐网-免外卖费店铺");
    	
    	Double lng = super.getLng();
		Double lat = super.getLat();
		if(page == null) page = 1;
    	
		Paging<ShopVO> shopList = shopManager.getShopVOByCateId(ShopCategoryEnum.商家自送.getId(),lng,lat,page,10);
		
		context.put("shopList", shopList);
		context.put("ShopCategoryEnum", ShopCategoryEnum.getAll());
		context.put("SelectedCate", ShopCategoryEnum.商家自送.getId());
    }
}
