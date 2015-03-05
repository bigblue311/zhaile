package com.zhaile.web.webpage.screen.my;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.alibaba.citrus.turbine.dataresolver.Params;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.zhaile.admin.webpage.filter.MyLoginFilter;
import com.zhaile.biz.web.form.OrderQueryFormBean;
import com.zhaile.biz.web.json.OrderQueryJson;
import com.zhaile.biz.web.manager.TransactionManager;
import com.zhaile.dal.enumerate.DeliveryStatusEnum;
import com.zhaile.dal.enumerate.SourceEnum;
import com.zhaile.dal.query.condition.OrderQueryCondition;

public class History extends MyLoginFilter{
	
	@Autowired
	TransactionManager transactionManager;
	
	public void execute(@Params OrderQueryFormBean orderQuery,
						Context context,Navigator nav) {
		if(!super.doFilter(nav)) return;
    	super.load(context,null);
    	context.put("title", "富阳宅乐网-购买记录");
    	
    	Long customerId = super.getCustomerDO(null).getId();
    	
    	context.put("DeliveryStatusEnum", DeliveryStatusEnum.getAll());
		context.put("SourceEnum", SourceEnum.getAll());
		
		OrderQueryCondition queryCondition = orderQuery.getOrderQueryCondition();
		queryCondition.customerId(customerId);
		List<OrderQueryJson> orderList = Lists.newArrayList();
		orderList = transactionManager.getOrderReport(queryCondition);
		context.put("orderList", JSONObject.toJSONString(orderList));
		context.put("orderQuery", orderQuery);
	}	
}