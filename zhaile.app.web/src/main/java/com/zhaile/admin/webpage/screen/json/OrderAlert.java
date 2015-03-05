package com.zhaile.admin.webpage.screen.json;

import java.io.IOException;
import java.util.Date;


import org.springframework.beans.factory.annotation.Autowired;

import com.zhaile.admin.webpage.filter.AdminLoginFilter;
import com.zhaile.biz.web.json.PaymentStatisticJson;
import com.zhaile.biz.web.manager.TransactionManager;

public class OrderAlert extends AdminLoginFilter {
	
	@Autowired
	private TransactionManager transactionManager;
	
	public PaymentStatisticJson execute() throws IOException {
		return transactionManager.queryPaymentStatistic(new Date(),super.getShopIds());
    }
}
