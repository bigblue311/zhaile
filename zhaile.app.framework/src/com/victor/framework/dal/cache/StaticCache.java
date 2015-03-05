package com.victor.framework.dal.cache;

import java.util.Collection;
import java.util.Map;

import com.google.common.collect.Maps;
import com.victor.framework.dal.basic.EntityDAO;
import com.victor.framework.dal.basic.EntityDO;

public abstract class StaticCache<entity extends EntityDO> extends EntityDAO<entity> {
	
	public StaticCache(String namespace) {
		super(namespace);
	}

	private final Map<Long,Object> cacheMap = Maps.newConcurrentMap();
	
	public abstract void reloadCache();
	
	public abstract Collection<Object> getAll();
	
	public final void clearCache(){
		cacheMap.clear();
	}
	
	public final Object getCache(Long key){
		if(key == null) return null;
		return cacheMap.get(key);
	}
	
	public final void updateCache(Long key, Object obj){
		cacheMap.put(key, obj);
	}
	
	public final void updateDB(entity e){
		super.update(e);
	}
	
	public final void insertDB(entity e){
		super.insert(e);
	}
	
	public final void deleteDB(Long id){
		super.delete(id);
	}
	
	public final Collection<Object> cacheValues(){
		return cacheMap.values();
	}
}
