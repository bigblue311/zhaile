package com.zhaile.web.webpage.screen;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.zhaile.biz.common.Cache;
import com.zhaile.biz.web.manager.ShopManager;
import com.zhaile.biz.web.model.ShopVO;
import com.zhaile.dal.model.CustomerDO;
import com.zhaile.search.engine.db.DBSearchFacade;
import com.zhaile.web.common.screen.DefaultLayout;

public class Shop extends DefaultLayout {
	
	@Autowired
	private Cache cache;
	
	@Autowired
	private ShopManager shopManager;
	
	@Autowired
	private DBSearchFacade searchFacade;
	
	public void execute(@Param("id") Long id,@Param("adId") Long adId,Context context){
		super.load(context,null);
		if(adId!=null){
			cache.getAdvertisementcache().click(adId);
		}
		ShopVO shopVO = shopManager.getShopVOById(id);
		CustomerDO loginUser = super.getCustomerDO(null);
		if(loginUser!=null) {
			shopVO.setIsFav(shopManager.isFav(id, loginUser.getId()));
		}
		context.put("shopVO", shopVO);
		if(shopVO ==null || shopVO.getShopDO() ==null) {
			String title = "富阳宅乐网";
			context.put("title", title);
		} else {
			String title = "富阳宅乐网-"+shopVO.getShopDO().getName();
			context.put("title", title);
		}
		putResult(searchFacade.findProduct(id),context);
	}
}
