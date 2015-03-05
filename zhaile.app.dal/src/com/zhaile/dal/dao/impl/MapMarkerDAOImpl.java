package com.zhaile.dal.dao.impl;

import java.sql.SQLException;
import java.util.List;

import com.victor.framework.common.shared.Result;
import com.victor.framework.dal.basic.EntityDAO;
import com.zhaile.dal.dao.MapMarkerDAO;
import com.zhaile.dal.enumerate.MapMarkerStatusEnum;
import com.zhaile.dal.model.MapMarkerDO;
import com.zhaile.dal.query.condition.MapMarkerQueryCondition;

public class MapMarkerDAOImpl extends EntityDAO<MapMarkerDO> implements MapMarkerDAO {

	public MapMarkerDAOImpl() {
		super(MapMarkerDO.class.getSimpleName());
	}

	@Override
	public Result<Boolean> approve(Long id) {
		MapMarkerDO mapMarkerDO = new MapMarkerDO();
		mapMarkerDO.setId(id);
		mapMarkerDO.setStatus(MapMarkerStatusEnum.已确定.getCode());
		return super.update(mapMarkerDO);
	}

	@Override
	public Result<Boolean> reject(Long id) {
		MapMarkerDO mapMarkerDO = new MapMarkerDO();
		mapMarkerDO.setId(id);
		mapMarkerDO.setStatus(MapMarkerStatusEnum.已取消.getCode());
		return super.update(mapMarkerDO);
	}

	@Override
	public Result<List<MapMarkerDO>> getTodayValid() {
		return super.queryForList("getTodayValid");
	}

	@Override
	public Result<List<MapMarkerDO>> getAll() {
		return super.queryForList("getAll");
	}

	@Override
	public Result<List<MapMarkerDO>> getTasks() {
		return super.queryForList("getTasks");
	}

	@Override
	public Integer getTaskCount() {
		try {
			return (Integer) super.getSqlMapClient().queryForObject(getNamespace()+".getTaskCount");
		} catch (SQLException e) {
			return -1;
		}
	}

	@Override
	public Result<List<MapMarkerDO>> getPage(
			MapMarkerQueryCondition queryCondtion) {
		return super.getPage(queryCondtion);
	}

	@Override
	public Result<Integer> getCount(MapMarkerQueryCondition queryCondtion) {
		return super.getCount(queryCondtion);
	}
}
