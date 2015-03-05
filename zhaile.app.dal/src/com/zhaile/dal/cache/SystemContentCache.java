package com.zhaile.dal.cache;

import com.zhaile.dal.model.SystemContentDO;

public interface SystemContentCache {
	void reload();
	
	SystemContentDO getContent(String position);
	
	void updateDB(SystemContentDO systemContentDO);
}
