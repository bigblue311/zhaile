package com.zhaile.web.webpage.screen;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Context;
import com.zhaile.biz.common.Cache;
import com.zhaile.biz.web.manager.ShopManager;
import com.zhaile.dal.model.AdvertisementDO;
import com.zhaile.dal.model.ProductDO;
import com.zhaile.web.common.screen.DefaultLayout;

public class Lucky extends DefaultLayout {
	
	@Autowired
	private Cache cache;
	
	@Autowired
	private ShopManager shopManager;
	
	public void execute(Context context) {
		AdvertisementDO ad = cache.getValidLucky();
		context.put("title", "富阳宅乐网-宅乐推荐");
    	super.load(context,null);
		cache.getAdvertisementcache().click(ad.getId());
    	try {
			context.put("title", "富阳宅乐网-宅乐推荐"+ad.getContent());
			String[] productIds = ad.getLink().split(",");
			List<ProductDO> prodList = shopManager.getProdByIds(convert(productIds));
			context.put("prodList", prodList);
		} catch (Exception e) {
			//do nothing
		}
    }
	
	private Long[] convert(String[] id){
		Long[] result = new Long[id.length];
		for(int i =0;i<id.length;i++){
			result[i] = Long.parseLong(id[i]);
		}
		return result;
	}
}
