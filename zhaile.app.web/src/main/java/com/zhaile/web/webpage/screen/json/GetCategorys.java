package com.zhaile.web.webpage.screen.json;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.google.common.collect.Lists;
import com.zhaile.biz.common.Cache;
import com.zhaile.dal.model.CategoryDO;
import com.zhaile.web.common.screen.DefaultLayout;

public class GetCategorys extends DefaultLayout{
	
	@Autowired
	private Cache cache;
	
	public List<CategoryDO> execute(@Param("shopId") Long shopId) throws IOException{
		List<CategoryDO> all = Lists.newArrayList();
		if(shopId==null){
			all = convert((cache.getCategorycache().getAll()));
		} else {
			all = cache.getCategorycache().getByShopId(shopId);
		}
		return all;
	}
	
	private List<CategoryDO> convert(Collection<Object> coll) {
		List<CategoryDO> all = Lists.newArrayList();
		if(coll==null) {
			return all;
		}
		for(Object obj : coll){
			all.add((CategoryDO)obj);
		}
		return all;
	}
}
