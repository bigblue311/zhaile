package com.zhaile.web.webpage.screen;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.zhaile.biz.common.Cache;
import com.zhaile.web.common.screen.DefaultLayout;

public class Event extends DefaultLayout {
	
	@Autowired
	private Cache cache;
	
	public void execute(@Param("adId") Long adId,Context context) {
    	super.load(context,null);
    	if(adId!=null){
			cache.getAdvertisementcache().click(adId);
		}
    	context.put("title", "情人节，与您尝出甜蜜");
    }
}
