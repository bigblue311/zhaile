package com.zhaile.admin.webpage.screen.json;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;
import com.zhaile.admin.webpage.filter.AdminLoginFilter;
import com.zhaile.biz.common.Cache;
import com.zhaile.biz.web.manager.SmsTaskManager;
import com.zhaile.dal.cache.key.SystemConfigCacheKey;
import com.zhaile.dal.model.SmsTaskDO;

public class QuerySmsTask extends AdminLoginFilter {
	@Autowired
	private Cache cache;
	
	@Autowired
	private SmsTaskManager smsTaskManager;
	
	public List<SmsTaskDO> execute() throws IOException {
		if(cache.getSwitch(SystemConfigCacheKey.SMS_SWITCH)){
			return smsTaskManager.querySmsTaskPending();
		}
		return Lists.newArrayList();
    }
}
