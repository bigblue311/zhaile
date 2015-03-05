package com.zhaile.admin.webpage.screen;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.zhaile.admin.webpage.filter.AdminLoginFilter;

public class Cache extends AdminLoginFilter {
	
	@Autowired
	private com.zhaile.biz.common.Cache cache;
	
	public void execute(@Param("command") String command,Context context,Navigator nav) throws IOException{
		boolean access = super.doFilter("cache",context,nav);
		if(!access){
			return;
		}
		if(command!=null && command.equalsIgnoreCase("reload")){
			cache.reload();
		}
		if(command!=null && command.equalsIgnoreCase("reindex")){
			cache.getCategorycache().reindex();
		}
	}
}
