package com.zhaile.admin.webpage.filter;

import com.alibaba.citrus.turbine.Navigator;
import com.zhaile.web.common.screen.DefaultLayout;

public class MyLoginFilter extends DefaultLayout {
	public boolean doFilter(Navigator nav){
		if(super.getCustomerDO(null) == null){
			nav.redirectTo("zhaile").withTarget("index.vm");
			return false;
		}
		return true;
	}
}
