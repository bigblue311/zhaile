package com.zhaile.admin.webpage.screen.json;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.zhaile.admin.webpage.filter.AdminLoginFilter;
import com.zhaile.biz.web.manager.AdvertisementManager;

public class UpdateMapMarkerStatus extends AdminLoginFilter {
	
	@Autowired
	private AdvertisementManager advertisementManager;
	
	public Boolean execute(@Param("id") Long id,@Param("status") String status) throws IOException {
		Boolean result = Boolean.FALSE;
		if(status.equals("1")){
			result = advertisementManager.approveMapMarker(id);
		} else {
			result = advertisementManager.rejectMapMarker(id);
		}
		return result;
    }
}
