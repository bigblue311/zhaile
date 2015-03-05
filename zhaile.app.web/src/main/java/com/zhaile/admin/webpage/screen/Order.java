package com.zhaile.admin.webpage.screen;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.alibaba.citrus.turbine.dataresolver.Params;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.victor.framework.common.tools.StringTools;
import com.zhaile.admin.webpage.filter.AdminLoginFilter;
import com.zhaile.biz.web.form.OrderQueryFormBean;
import com.zhaile.biz.web.json.OrderQueryJson;
import com.zhaile.biz.web.manager.TransactionManager;
import com.zhaile.dal.enumerate.DeliveryStatusEnum;
import com.zhaile.dal.enumerate.SourceEnum;
import com.zhaile.dal.query.condition.OrderQueryCondition;

public class Order extends AdminLoginFilter {
	
	@Autowired
	private TransactionManager transactionManager;
	
	public void execute(@Params OrderQueryFormBean orderQuery,
						Navigator nav, Context context) throws IOException{
		boolean access = super.doFilter("order",context,nav);
		if(!access){
			return;
		}
		
		context.put("DeliveryStatusEnum", DeliveryStatusEnum.getAll());
		context.put("SourceEnum", SourceEnum.getAll());
		
		if(orderQuery.getModifyStart() == null && 
				orderQuery.getModifyEnd() == null && 
		StringTools.isAllEmpty(orderQuery.getCustomerName(),
				orderQuery.getShopName(),
				orderQuery.getStatus()) &&
				orderQuery.getEmployeeId() == null &&
				orderQuery.getSource() == null) {
			return;
		}
		OrderQueryCondition queryCondition = orderQuery.getOrderQueryCondition();
		List<OrderQueryJson> orderList = Lists.newArrayList();
		if(super.isZhaile()){
			orderList = transactionManager.getOrderReport(queryCondition);
		} else {
			if(super.getShopIds()!=null){
				queryCondition.shopId(super.getShopIds());
				orderList = transactionManager.getOrderReport(queryCondition);
			}
		} 
		context.put("orderList", JSONObject.toJSONString(orderList));
		context.put("orderQuery", orderQuery);
	}
}
