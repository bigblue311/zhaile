package com.zhaile.dal.cache.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import com.victor.framework.common.shared.Result;
import com.victor.framework.common.tools.CollectionTools;
import com.victor.framework.common.tools.LogTools;
import com.victor.framework.dal.cache.StaticCache;
import com.zhaile.dal.cache.SystemConfigCache;
import com.zhaile.dal.model.SystemConfigDO;

public class SystemConfigCacheImpl extends StaticCache<SystemConfigDO> implements SystemConfigCache {

	private static LogTools log = new LogTools(SystemConfigCache.class);
	
	private static final String ON = "ON";
	private static final Map<String, SystemConfigDO> kvMap = Maps.newConcurrentMap();
	
	public SystemConfigCacheImpl() {
		super(SystemConfigDO.class.getSimpleName());
	}

	@Override
	public void reload() {
		reloadCache();
	}

	@Override
	public SystemConfigDO getConfig(String key) {
		return kvMap.get(key);
	}

	@Override
	public void reloadCache() {
		Result<List<SystemConfigDO>> result = super.queryForList("getAll");
		if(result.isSuccess() && CollectionTools.isNotEmpty(result.getDataObject())){
			super.clearCache();
			kvMap.clear();
			for(SystemConfigDO systemConfig: result.getDataObject()){
				super.updateCache(systemConfig.getId(), systemConfig);
				String userKey = systemConfig.getKey();
				kvMap.put(userKey, systemConfig);
			}
		} else {
			log.error("加载缓存数据失败");
		}
	}
	
	@Override
	public Collection<Object> getAll() {
		return super.cacheValues();
	}

	@Override
	public boolean getSwitch(String name) {
		SystemConfigDO switchDO = this.getConfig(name);
		if(switchDO==null){
			return false;
		}
		return ON.equals(switchDO.getValue());
	}

}
