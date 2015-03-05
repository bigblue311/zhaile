package com.zhaile.web.webpage.screen.json;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.google.common.collect.Lists;
import com.victor.framework.common.tools.StringTools;
import com.zhaile.biz.common.Cache;
import com.zhaile.dal.model.ShopDO;
import com.zhaile.web.common.screen.DefaultLayout;

public class GetShops extends DefaultLayout{
	
	@Autowired
	private Cache cache;
	
	public List<ShopDO> execute(@Param("categoryId") String categoryId,@Param("alphabet") String alphabet)throws IOException{
		List<ShopDO> shops = Lists.newArrayList();
		if(StringTools.isAllEmpty(categoryId,alphabet)){
			shops = cache.getCategorycache().getAllShops();
		} else {
			if(StringTools.isNotEmpty(categoryId)){
				shops = cache.getCategorycache().getShops(categoryId);
			}
			if(StringTools.isNotEmpty(alphabet)){
				shops = cache.getCategorycache().getAllShops(alphabet);
			}
		}
		return shops;
	}
}
