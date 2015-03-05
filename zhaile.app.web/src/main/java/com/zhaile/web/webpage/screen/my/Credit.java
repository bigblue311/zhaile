package com.zhaile.web.webpage.screen.my;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.zhaile.admin.webpage.filter.MyLoginFilter;

public class Credit extends MyLoginFilter{
	public void execute(Context context,Navigator nav) {
		if(!super.doFilter(nav)) return;
    	super.load(context,null);
    	context.put("title", "富阳宅乐网-积分兑换");
    }
}
