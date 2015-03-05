package com.zhaile.admin.webpage.screen.json;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.zhaile.admin.webpage.filter.AdminLoginFilter;
import com.zhaile.biz.web.manager.TransactionManager;
import com.zhaile.biz.web.model.PaymentVO;

public class OrderRecipt extends AdminLoginFilter {
	
	@Autowired
	private TransactionManager transactionManager;
	
	public void execute(@Param("id") Long id,Context context) throws IOException {
		PaymentVO paymentVO = transactionManager.getPaymentById(id);
		context.put("paymentVO", paymentVO);
    }
}
