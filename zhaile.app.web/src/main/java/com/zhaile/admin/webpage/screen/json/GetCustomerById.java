package com.zhaile.admin.webpage.screen.json;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.zhaile.admin.webpage.filter.AdminLoginFilter;
import com.zhaile.biz.web.manager.CustomerManager;
import com.zhaile.biz.web.model.CustomerVO;

public class GetCustomerById extends AdminLoginFilter{
	
	@Autowired
	private CustomerManager customerManager;
	
	public CustomerVO execute(@Param("id") Long id) {
		return customerManager.getCustomer(id);
	}
}
