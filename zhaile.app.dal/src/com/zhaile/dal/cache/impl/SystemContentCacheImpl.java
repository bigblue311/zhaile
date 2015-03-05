package com.zhaile.dal.cache.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import com.victor.framework.common.shared.Result;
import com.victor.framework.common.tools.CollectionTools;
import com.victor.framework.common.tools.LogTools;
import com.victor.framework.dal.cache.StaticCache;
import com.zhaile.dal.cache.SystemContentCache;
import com.zhaile.dal.model.SystemContentDO;

public class SystemContentCacheImpl extends StaticCache<SystemContentDO> implements SystemContentCache {

	private static LogTools log = new LogTools(SystemContentCache.class);
	
	private static final Map<String, SystemContentDO> kvMap = Maps.newConcurrentMap();
	
	public SystemContentCacheImpl() {
		super(SystemContentDO.class.getSimpleName());
	}

	@Override
	public void reload() {
		reloadCache();
	}

	@Override
	public SystemContentDO getContent(String position) {
		return kvMap.get(position);
	}

	@Override
	public void reloadCache() {
		Result<List<SystemContentDO>> result = super.queryForList("getAll");
		if(result.isSuccess() && CollectionTools.isNotEmpty(result.getDataObject())){
			super.clearCache();
			kvMap.clear();
			for(SystemContentDO systemContentDO: result.getDataObject()){
				super.updateCache(systemContentDO.getId(), systemContentDO);
				String userKey = systemContentDO.getPosition();
				kvMap.put(userKey, systemContentDO);
			}
		} else {
			log.error("加载缓存数据失败");
		}
	}
	
	@Override
	public Collection<Object> getAll() {
		return super.cacheValues();
	}
}
