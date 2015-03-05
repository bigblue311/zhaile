package com.zhaile.dal.cache.impl;

import java.util.Collection;
import java.util.List;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.victor.framework.common.shared.Result;
import com.victor.framework.common.tools.CollectionTools;
import com.victor.framework.common.tools.LogTools;
import com.victor.framework.dal.cache.StaticCache;
import com.zhaile.dal.cache.HeadImgCache;
import com.zhaile.dal.model.HeadImageDO;

public class HeadImgCacheImpl extends StaticCache<HeadImageDO> implements HeadImgCache {

	private static LogTools log = new LogTools(HeadImgCache.class);
	
	public HeadImgCacheImpl() {
		super(HeadImageDO.class.getSimpleName());
	}

	@Override
	public void reload() {
		reloadCache();
	}

	@Override
	public HeadImageDO getCahce(Long id) {
		if(id==null) return null;
		return (HeadImageDO)super.getCache(id);
	}

	@Override
	public void reloadCache() {
		Result<List<HeadImageDO>> result = super.queryForList("getAll");
		if(result.isSuccess() && CollectionTools.isNotEmpty(result.getDataObject())){
			super.clearCache();
			for(HeadImageDO headImg: result.getDataObject()){
				super.updateCache(headImg.getId(), headImg);
			}
		} else {
			log.error("加载缓存数据失败");
		}
	}

	@Override
	public Collection<HeadImageDO> values() {
		return	Collections2.transform(super.cacheValues(), 
		new Function<Object, HeadImageDO>() {
			@Override
			public HeadImageDO apply(final Object obj) {
				return (HeadImageDO)obj;
			}
		});
	}
	
	@Override
	public Collection<Object> getAll() {
		return super.cacheValues();
	}

}
