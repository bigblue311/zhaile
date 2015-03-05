package com.zhaile.dal.cache;

import com.zhaile.dal.model.SystemConfigDO;

public interface SystemConfigCache {
	void reload();
	
	SystemConfigDO getConfig(String key);
	
	void updateDB(SystemConfigDO systemConfigDO);
	
	boolean getSwitch(String name);
}
