package com.zhaile.admin.webpage.screen;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.alibaba.citrus.turbine.dataresolver.Params;
import com.alibaba.fastjson.JSONObject;
import com.zhaile.admin.webpage.filter.AdminLoginFilter;
import com.zhaile.biz.web.form.SmsQueryFormBean;
import com.zhaile.biz.web.manager.SmsTaskManager;
import com.zhaile.dal.enumerate.SmsStatusEnum;
import com.zhaile.dal.enumerate.SmsTypeEnum;
import com.zhaile.dal.model.SmsTaskDO;
import com.zhaile.dal.query.condition.SmsTaskQueryCondition;

public class MessageCenter extends AdminLoginFilter {
	
	@Autowired
	private SmsTaskManager smsTaskManager;
	
	public void execute(@Params SmsQueryFormBean smsQueryFormBean,
						Navigator nav, Context context) throws IOException{
		boolean access = super.doFilter("messageCenter",context,nav);
		if(!access){
			return;
		}
		
		context.put("SmsTypeEnum", SmsTypeEnum.getAll());
		context.put("SmsStatusEnum", SmsStatusEnum.getAll());
		
		SmsTaskQueryCondition queryCondition = smsQueryFormBean.getSmsTaskQueryCondition();
		List<SmsTaskDO> smsList = smsTaskManager.querySmsTasks(queryCondition);
		context.put("smsList", JSONObject.toJSONString(smsList));
		context.put("smsQueryFormBean", smsQueryFormBean);
	}
}
