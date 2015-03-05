package com.zhaile.web.webpage.screen.json;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.victor.framework.common.tools.SecurityTools;
import com.victor.framework.common.tools.StringTools;
import com.victor.framework.search.basic.SearchResult;
import com.zhaile.biz.common.Cache;
import com.zhaile.dal.model.CustomerDO;
import com.zhaile.search.engine.db.DBSearchFacade;
import com.zhaile.web.common.screen.DefaultLayout;

public class Search extends DefaultLayout{
	
	@Autowired
	private DBSearchFacade searchFacade;
	
	@Autowired
	private Cache cache;
	
	public List<SearchResult<?>> execute(@Param("keyword") String keyword,
						@Param("shopId") Long shopId,
						@Param("adId") Long adId,
						@Param("categoryId") Long categoryId,
						@Param("userId") Long userId)throws IOException{
		//安全过滤
		SecurityTools.injectionFilter(keyword);
		
		CustomerDO loginUser = super.getCustomerDO(userId);
		Long customerId = 0l;
		List<SearchResult<?>> searchResult = null;
		if(loginUser != null) {
			customerId = loginUser.getId();
		}
		if(StringTools.isNotEmpty(keyword) && categoryId==null && shopId==null){
			searchResult = searchFacade.search(customerId, keyword);
		}
		if(StringTools.isNotEmpty(keyword) && categoryId!=null && shopId==null){
			searchResult = searchFacade.search(customerId,categoryId, keyword);
		}
		if(StringTools.isNotEmpty(keyword) && categoryId==null && shopId!=null){
			searchResult = searchFacade.findProduct(shopId, keyword);
		}
		if(StringTools.isNotEmpty(keyword) && categoryId!=null && shopId!=null){
			searchResult = searchFacade.findProduct(shopId,categoryId, keyword);
		}
		if(StringTools.isEmpty(keyword) && categoryId==null && shopId!=null){
			searchResult = searchFacade.findProduct(shopId);
		}
		if(StringTools.isEmpty(keyword) && categoryId!=null && shopId!=null){
			searchResult = searchFacade.findProduct(shopId, categoryId);
		}
		if(StringTools.isEmpty(keyword) && categoryId==null && shopId==null){
			searchResult = searchFacade.random(120,cache.getValidLuckyCategoryId());
		}
		if(StringTools.isEmpty(keyword) && categoryId!=null && shopId==null){
			searchResult = searchFacade.search(categoryId);
		}
		return searchResult;
	}
}
