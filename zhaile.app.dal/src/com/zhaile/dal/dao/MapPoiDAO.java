package com.zhaile.dal.dao;

import com.victor.framework.common.shared.Result;
import com.zhaile.dal.model.MapPoiDO;

public interface MapPoiDAO {
	Result<Long> insert(MapPoiDO mapPoiDO);
	
	Result<Boolean> update(MapPoiDO mapPoiDO);
	
	Result<MapPoiDO> getByIp(String ip);
}
