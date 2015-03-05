package com.zhaile.admin.webpage.screen.json;

import java.io.IOException;


import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.zhaile.admin.webpage.filter.AdminLoginFilter;
import com.zhaile.biz.common.Cache;
import com.zhaile.biz.web.json.PaymentQueryJson;
import com.zhaile.biz.web.manager.TransactionManager;

public class UpdateOrderComment extends AdminLoginFilter {
	
	@Autowired
	private Cache cache;
	
	@Autowired
	private TransactionManager transactionManager;
	
	public Boolean execute(@Param("id") Long id,@Param("comment") String comment) throws IOException {
		PaymentQueryJson payment = transactionManager.updatePaymentComment(id, comment);
		if(payment!=null){
			cache.setPaymentStatus(payment);
			return true;
		} else {
			return false;
		}
    }
}
