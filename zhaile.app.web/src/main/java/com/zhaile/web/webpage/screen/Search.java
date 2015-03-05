package com.zhaile.web.webpage.screen;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.victor.framework.common.tools.StringTools;
import com.zhaile.biz.common.Cache;
import com.zhaile.dal.model.CustomerDO;
import com.zhaile.search.engine.db.DBSearchFacade;
import com.zhaile.web.common.screen.DefaultLayout;

public class Search extends DefaultLayout {
	
	@Autowired
	private Cache cache;
	
	@Autowired
	private DBSearchFacade searchFacade;
	
	public void execute(@Param("keyword") String keyword,
						@Param("shopId") Long shopId,
						@Param("categoryId") Long categoryId,
						@Param("adId") Long adId,Context context) {
		super.load(context,null);
		if(adId!=null){
			cache.getAdvertisementcache().click(adId);
		}
		context.put("keyword", keyword);
		context.put("shopId", shopId);
		context.put("categoryId", categoryId);
		String title = "富阳宅乐网-搜索";
		if(keyword!=null){
			title += keyword;
		}
		context.put("title", title);
		CustomerDO loginUser = super.getCustomerDO(null);
		Long customerId = 0l;
		if(loginUser != null) {
			customerId = loginUser.getId();
		}
		if(StringTools.isNotEmpty(keyword) && categoryId==null && shopId==null){
			putResult(searchFacade.search(customerId, keyword),context);
			return;
		}
		if(StringTools.isNotEmpty(keyword) && categoryId!=null && shopId==null){
			putResult(searchFacade.search(customerId,categoryId, keyword),context);
			return;
		}
		if(StringTools.isNotEmpty(keyword) && categoryId==null && shopId!=null){
			putResult(searchFacade.findProduct(shopId, keyword),context);
			return;
		}
		if(StringTools.isNotEmpty(keyword) && categoryId!=null && shopId!=null){
			putResult(searchFacade.findProduct(shopId,categoryId, keyword),context);
			return;
		}
		if(StringTools.isEmpty(keyword) && categoryId==null && shopId!=null){
			putResult(searchFacade.findProduct(shopId),context);
			return;
		}
		if(StringTools.isEmpty(keyword) && categoryId!=null && shopId!=null){
			putResult(searchFacade.findProduct(shopId, categoryId),context);
			return;
		}
		if(StringTools.isEmpty(keyword) && categoryId==null && shopId==null){
			putResult(searchFacade.random(120,cache.getValidLuckyCategoryId()),context);
			return;
		}
		if(StringTools.isEmpty(keyword) && categoryId!=null && shopId==null){
			putResult(searchFacade.search(categoryId),context);
			return;
		}
	}
}
