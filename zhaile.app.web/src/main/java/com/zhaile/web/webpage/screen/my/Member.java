package com.zhaile.web.webpage.screen.my;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.zhaile.admin.webpage.filter.MyLoginFilter;
import com.zhaile.biz.web.manager.CustomerManager;
import com.zhaile.biz.web.model.CustomerVO;
import com.zhaile.dal.model.PeopleContactDO;

public class Member extends MyLoginFilter{
	
	@Autowired
	CustomerManager customerManager;
	
	public void execute(Context context,Navigator nav) {
		if(!super.doFilter(nav)) return;
    	super.load(context,null);
    	context.put("title", "富阳宅乐网-我的个人信息");
    	
    	CustomerVO loginUser = customerManager.getCustomer(super.getCustomerDO(null).getId());
    	
		context.put("memberInfo",loginUser);
		
		List<PeopleContactDO> clist = super.getPeopleContactList(null);
		context.put("peopleContact",clist);
    }
}
