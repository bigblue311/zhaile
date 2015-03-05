package com.zhaile.web.webpage.screen.json;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.victor.framework.common.shared.Result;
import com.zhaile.biz.web.manager.CustomerManager;
import com.zhaile.web.common.screen.DefaultLayout;

public class CheckExist extends DefaultLayout{
	
	@Autowired
	private CustomerManager customerManager;
	
	public Result<Boolean> execute(@Param("loginId") String loginId,@Param("field") String field) throws IOException {
		return customerManager.checkExist(loginId,field);
    }
}
