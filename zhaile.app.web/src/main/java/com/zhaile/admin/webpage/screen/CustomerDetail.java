package com.zhaile.admin.webpage.screen;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.zhaile.admin.webpage.filter.AdminLoginFilter;
import com.zhaile.biz.web.manager.CustomerManager;
import com.zhaile.biz.web.model.CustomerVO;
import com.zhaile.dal.enumerate.CreditActionEnum;

public class CustomerDetail extends AdminLoginFilter {
	
	@Autowired
	private CustomerManager customerManager;
	
	public void execute(@Param("id") Long id,
						Navigator nav, Context context) throws IOException{
		boolean access = super.doFilter("cache",context,nav);
		if(!access){
			return;
		}
		CustomerVO customer = customerManager.getCustomer(id);
		context.put("customer", customer);
		context.put("creditActionEnum", CreditActionEnum.getAll());
	}
}
