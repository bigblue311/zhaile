package com.zhaile.admin.webpage.screen;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alibaba.fastjson.JSONObject;
import com.zhaile.admin.webpage.filter.AdminLoginFilter;
import com.zhaile.biz.web.manager.CustomerManager;
import com.zhaile.dal.model.CustomerDO;

public class Customer extends AdminLoginFilter {
	
	@Autowired
	private CustomerManager customerManager;
	
	public void execute(@Param("name") String name, 
						@Param("email") String email,
						@Param("mobile") String mobile,
						Navigator nav, Context context) throws IOException{
		boolean access = super.doFilter("customer",context,nav);
		if(!access){
			return;
		}
		List<CustomerDO> list = customerManager.getCustomer(name, email,mobile);
		context.put("customerList", JSONObject.toJSONString(list));
	}
}
