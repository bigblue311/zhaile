package com.zhaile.biz.web.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.zhaile.biz.common.manager.CommonManager;
import com.zhaile.dal.dao.MapMarkerDAO;
import com.zhaile.dal.model.MapMarkerDO;
import com.zhaile.dal.query.condition.MapMarkerQueryCondition;

public class AdvertisementManagerImpl extends CommonManager implements AdvertisementManager{

	@Autowired
	private MapMarkerDAO mapMarkerDAO;
	
	@Override
	public void applyMapMarker(MapMarkerDO mapMarkerDO) {
		mapMarkerDAO.insert(mapMarkerDO);
	}

	@Override
	public Boolean updateMapMarker(MapMarkerDO mapMarkerDO) {
		return mapMarkerDAO.update(mapMarkerDO).getDataObject();
	}

	@Override
	public Boolean approveMapMarker(Long mapMarkerId) {
		return mapMarkerDAO.approve(mapMarkerId).getDataObject();
	}

	@Override
	public Boolean rejectMapMarker(Long mapMarkerId) {
		return mapMarkerDAO.reject(mapMarkerId).getDataObject();
	}

	@Override
	public List<MapMarkerDO> queryMapMarker(MapMarkerQueryCondition queryCondition) {
		return mapMarkerDAO.getPage(queryCondition).getDataObject();
	}

	@Override
	public List<MapMarkerDO> getTodayValid() {
		return mapMarkerDAO.getTodayValid().getDataObject();
	}
}
