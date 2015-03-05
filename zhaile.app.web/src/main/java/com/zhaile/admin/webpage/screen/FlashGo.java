package com.zhaile.admin.webpage.screen;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.alibaba.citrus.turbine.dataresolver.Params;
import com.alibaba.fastjson.JSONObject;
import com.victor.framework.dal.basic.Paging;
import com.zhaile.admin.webpage.filter.AdminLoginFilter;
import com.zhaile.biz.web.form.FlashGoQueryFormBean;
import com.zhaile.biz.web.manager.FlashGoManager;
import com.zhaile.dal.model.FlashGoDO;
import com.zhaile.dal.query.condition.FlashGoQueryCondition;

public class FlashGo extends AdminLoginFilter {
	
	@Autowired
	private FlashGoManager flashGoManager;
	
	public void execute(@Params FlashGoQueryFormBean flashGoQueryFormBean,
						Navigator nav, Context context) throws IOException{
		boolean access = super.doFilter("flashGo",context,nav);
		if(!access){
			return;
		}
		FlashGoQueryCondition queryCondition = flashGoQueryFormBean.getFlashGoQueryCondition();
		Paging<FlashGoDO> eventList = Paging.emptyPage();
		eventList = flashGoManager.getPage(queryCondition);
		context.put("eventList", JSONObject.toJSONString(eventList.getData()));
		context.put("paging", eventList);
		context.put("flashGoQuery", flashGoQueryFormBean);
	}
}
