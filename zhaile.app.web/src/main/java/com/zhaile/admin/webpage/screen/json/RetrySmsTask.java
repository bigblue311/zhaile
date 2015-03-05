package com.zhaile.admin.webpage.screen.json;

import java.io.IOException;


import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.zhaile.biz.web.manager.SmsTaskManager;

public class RetrySmsTask {
	
	@Autowired
	private SmsTaskManager smsTaskManager;
	
	public boolean execute(@Param("id") Long id) throws IOException {
		return smsTaskManager.retry(id);
    }
}
