package com.zhaile.web.webpage.screen.my;


import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.zhaile.admin.webpage.filter.MyLoginFilter;
import com.zhaile.biz.web.manager.CustomerManager;

public class Comment extends MyLoginFilter{
	
	@Autowired
	CustomerManager customerManager;
	
	public void execute(Context context,Navigator nav) {
		if(!super.doFilter(nav)) return;
    	super.load(context,null);
    	Long userId = super.getCustomerDO(null).getId();
    	context.put("title", "富阳宅乐网-投诉建议");
    	context.put("commentList",customerManager.getCommentByUserId(userId));
		context.put("prodId","");
		context.put("shopId","");
    }
}
