package com.zhaile.dal.cache.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import com.victor.framework.common.shared.Result;
import com.victor.framework.common.tools.CollectionTools;
import com.victor.framework.common.tools.LogTools;
import com.victor.framework.dal.cache.StaticCache;
import com.zhaile.dal.cache.EmployeeCache;
import com.zhaile.dal.cache.SystemConfigCache;
import com.zhaile.dal.model.EmployeeDO;

public class EmployeeCacheImpl extends StaticCache<EmployeeDO> implements EmployeeCache {

	private static LogTools log = new LogTools(SystemConfigCache.class);
	
	private static final Map<String, EmployeeDO> kvMap = Maps.newConcurrentMap();
	
	public EmployeeCacheImpl() {
		super(EmployeeDO.class.getSimpleName());
	}

	@Override
	public void reload() {
		reloadCache();
	}

	@Override
	public void reloadCache() {
		Result<List<EmployeeDO>> result = super.queryForList("getAll");
		if(result.isSuccess() && CollectionTools.isNotEmpty(result.getDataObject())){
			super.clearCache();
			kvMap.clear();
			for(EmployeeDO employee: result.getDataObject()){
				super.updateCache(employee.getId(), employee);
				String userKey = employee.getName();
				kvMap.put(userKey, employee);
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
	public EmployeeDO getEmployee(String key) {
		return kvMap.get(key);
	}
	
	@Override
	public EmployeeDO getEmployee(Long id) {
		if(id==null) return null;
		return (EmployeeDO)super.getCache(id);
	}

	@Override
	public void deleteById(Long id) {
		super.deleteDB(id);
	}

}
