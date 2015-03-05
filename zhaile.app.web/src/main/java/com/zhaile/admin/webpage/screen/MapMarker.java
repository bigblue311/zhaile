package com.zhaile.admin.webpage.screen;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.alibaba.citrus.turbine.dataresolver.Params;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.zhaile.admin.webpage.filter.AdminLoginFilter;
import com.zhaile.biz.web.form.MapMarkerQueryFormBean;
import com.zhaile.biz.web.manager.AdvertisementManager;
import com.zhaile.dal.enumerate.MapMarkerStatusEnum;
import com.zhaile.dal.model.MapMarkerDO;
import com.zhaile.dal.query.condition.MapMarkerQueryCondition;

public class MapMarker extends AdminLoginFilter {
	
	@Autowired
	private AdvertisementManager advertisementManager;
	
	public void execute(@Params MapMarkerQueryFormBean mapMarkerQuery,
						Navigator nav, Context context) throws IOException{
		boolean access = super.doFilter("mapMarker",context,nav);
		if(!access){
			return;
		}
		
		context.put("MapMarkerStatusEnum", MapMarkerStatusEnum.getAll());
		
		MapMarkerQueryCondition queryCondition = mapMarkerQuery.getMapMarkerQueryCondition();
		List<MapMarkerDO> mapMarkerList = Lists.newArrayList();
		mapMarkerList = advertisementManager.queryMapMarker(queryCondition);
		context.put("mapMarkerList", JSONObject.toJSONString(mapMarkerList));
		context.put("MapMarkerQueryFormBean", mapMarkerQuery);
	}
}
