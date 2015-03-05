package com.zhaile.admin.webpage.screen.json;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.zhaile.admin.webpage.filter.AdminLoginFilter;
import com.zhaile.biz.web.manager.AdvertisementManager;
import com.zhaile.dal.model.MapMarkerDO;

public class UpdateMapMarkerPoint extends AdminLoginFilter {
	
	@Autowired
	private AdvertisementManager advertisementManager;
	
	public Boolean execute(@Param("id") Long id,@Param("lng") Double lng,@Param("lat") Double lat) throws IOException {
		MapMarkerDO mapMarkerDO = new MapMarkerDO();
		mapMarkerDO.setId(id);
		mapMarkerDO.setLng(lng);
		mapMarkerDO.setLat(lat);
		return advertisementManager.updateMapMarker(mapMarkerDO);
    }
}
