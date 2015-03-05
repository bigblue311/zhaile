package com.zhaile.biz.web.manager;

import java.util.List;

import com.zhaile.dal.model.MapMarkerDO;
import com.zhaile.dal.query.condition.MapMarkerQueryCondition;

public interface AdvertisementManager {
	
	/**
	 * 申请地图广告
	 * @param mapMarkerDO
	 */
	void applyMapMarker(MapMarkerDO mapMarkerDO);
	
	/**
	 * 更新地图广告
	 * @param mapMarkerDO
	 * @return
	 */
	Boolean updateMapMarker(MapMarkerDO mapMarkerDO);
	
	/**
	 * 审批通过地图广告
	 * @param mapMarkerId
	 * @return
	 */
	Boolean approveMapMarker(Long mapMarkerId);
	
	/**
	 * 拒绝地图广告
	 * @param mapMarkerId
	 * @return
	 */
	Boolean rejectMapMarker(Long mapMarkerId);
	
	/**
	 * 根据条件查询地图广告
	 * @param queryCondition
	 * @return
	 */
	List<MapMarkerDO> queryMapMarker(MapMarkerQueryCondition queryCondition);
	
	/**
	 * 获取今天有效的地图广告
	 * @return
	 */
	List<MapMarkerDO> getTodayValid();
}
