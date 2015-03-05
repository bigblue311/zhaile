package com.zhaile.dal.dao.impl;

import java.sql.SQLException;
import java.util.List;

import com.victor.framework.common.shared.Result;
import com.victor.framework.common.tools.StringTools;
import com.victor.framework.dal.basic.EntityDAO;
import com.zhaile.dal.dao.MapPoiDAO;
import com.zhaile.dal.model.MapPoiDO;

public class MapPoiDAOImpl extends EntityDAO<MapPoiDO> implements MapPoiDAO {

	public MapPoiDAOImpl() {
		super(MapPoiDO.class.getSimpleName());
	}

	@Override
	public Result<MapPoiDO> getByIp(String ip) {
		try {
			if(StringTools.isNotEmpty(ip) && ip.contains(".")){
				@SuppressWarnings("unchecked")
				List<MapPoiDO> list = super.getSqlMapClient().queryForList(super.getNamespace()+".getByIp", ip);
				if(list.size()<1){
					return Result.newInstance(null, "nothing found", true);
				}
				if(list.size()>1){
					return Result.newInstance(null, "duplicate ip found", false);
				}
				return Result.newInstance(list.get(0), "success", true);
			}
			return Result.newInstance(null, "invalid request", false);
			
		} catch (SQLException e) {
			return Result.newInstance(null, "bad sql", false);
		}
	}
}
