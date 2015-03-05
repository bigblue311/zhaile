package com.zhaile.admin.webpage.screen;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.alibaba.citrus.turbine.dataresolver.Params;
import com.alibaba.fastjson.JSONObject;
import com.victor.framework.dal.basic.Paging;
import com.zhaile.admin.webpage.filter.AdminLoginFilter;
import com.zhaile.biz.web.form.PaymentQueryFormBean;
import com.zhaile.biz.web.json.PaymentQueryJson;
import com.zhaile.biz.web.manager.TransactionManager;
import com.zhaile.dal.enumerate.DeliveryStatusEnum;
import com.zhaile.dal.enumerate.SourceEnum;
import com.zhaile.dal.enumerate.SmsStatusEnum;
import com.zhaile.dal.enumerate.PaymentStatusEnum;
import com.zhaile.dal.query.condition.PaymentQueryCondition;

public class Payment extends AdminLoginFilter {
	
	@Autowired
	private TransactionManager transactionManager;
	
	public void execute(@Params PaymentQueryFormBean paymentQuery,
						Navigator nav, Context context) throws IOException{
		boolean access = super.doFilter("payment",context,nav);
		if(!access){
			return;
		}
		
		context.put("DeliveryStatusEnum", DeliveryStatusEnum.getAll());
		context.put("SourceEnum", SourceEnum.getAll());
		context.put("SmsStatusEnum", SmsStatusEnum.getAll());
		context.put("PaymentStatusEnum", PaymentStatusEnum.getAll());
		
		PaymentQueryCondition queryCondition = paymentQuery.getPaymentQueryCondition();
		Paging<PaymentQueryJson> orderList = Paging.emptyPage();
		if(super.isZhaile()){
			orderList = transactionManager.queryPayment(queryCondition);
		} else {
			if(super.getShopIds()!=null){
				queryCondition.shopId(super.getShopIds());
				orderList = transactionManager.queryPayment(queryCondition);
			}
		} 
		context.put("paymentList", JSONObject.toJSONString(orderList.getData()));
		context.put("paging", orderList);
		context.put("paymentQuery", paymentQuery);
	}
}
