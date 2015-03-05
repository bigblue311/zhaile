package com.zhaile.admin.webpage.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.alibaba.citrus.turbine.dataresolver.FormGroup;
import com.zhaile.biz.common.Cache;
import com.zhaile.dal.model.SystemConfigDO;

public class UpdateSystemConfigAction {
	
	@Autowired
	private Cache cache;
	
	public void doUpdate(@FormGroup("systemConfig") SystemConfigDO systemConfigDO, Navigator nav,Context context) {
		cache.update2DB(systemConfigDO);
		cache.reload();
	}
}
