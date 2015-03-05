package com.zhaile.dal.dao;

import java.util.List;

import com.victor.framework.common.shared.Result;
import com.zhaile.dal.model.MapMarkerDO;
import com.zhaile.dal.query.condition.MapMarkerQueryCondition;

public interface MapMarkerDAO {
	Result<Long> insert(MapMarkerDO mapMarkerDO);
	
	Result<Boolean> update(MapMarkerDO mapMarkerDO);
	
	Result<Boolean> approve(Long id);
	
	Result<Boolean> reject(Long id);
	
	Result<List<MapMarkerDO>> getTodayValid();
	
	Result<List<MapMarkerDO>> getTasks();
	
	Integer getTaskCount();
	
	Result<List<MapMarkerDO>> getAll();
	
	Result<List<MapMarkerDO>> getPage(MapMarkerQueryCondition queryCondtion);
	
	Result<Integer> getCount(MapMarkerQueryCondition queryCondtion);
}
