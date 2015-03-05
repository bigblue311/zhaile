package com.zhaile.web.webpage.screen.my;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.zhaile.admin.webpage.filter.MyLoginFilter;
import com.zhaile.biz.web.manager.CustomerManager;
import com.zhaile.biz.web.model.CustomerVO;

public class Fav extends MyLoginFilter{
	
	@Autowired
	CustomerManager customerManager;
	
	public void execute(Context context,Navigator nav) {
		if(!super.doFilter(nav)) return;
    	super.load(context,null);
    	context.put("title", "富阳宅乐网-收藏夹");
    	
    	CustomerVO loginUser = customerManager.getCustomer(super.getCustomerDO(null).getId());
    	
		context.put("memberInfo",loginUser);
    }
}
