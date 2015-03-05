package com.zhaile.admin.webpage.screen;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.zhaile.admin.webpage.filter.AdminLoginFilter;
import com.zhaile.biz.common.Cache;

public class Employee extends AdminLoginFilter {
	
	@Autowired
	private Cache cache;
	
	public void execute(@Param("command") String command,Context context,Navigator nav) throws IOException{
		boolean access = super.doFilter("employee",context,nav);
		if(!access){
			return;
		}
		if(command!=null && command.equalsIgnoreCase("reload")){
			cache.reload();
		}
	}
}
