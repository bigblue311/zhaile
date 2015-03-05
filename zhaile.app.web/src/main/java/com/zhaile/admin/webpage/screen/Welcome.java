package com.zhaile.admin.webpage.screen;

import java.io.IOException;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.zhaile.admin.webpage.filter.AdminLoginFilter;

public class Welcome extends AdminLoginFilter {
	
	public void execute(Context context,Navigator nav) throws IOException{
		boolean access = super.doFilter("welcome",context,nav);
		if(!access){
			return;
		}
	}
}
