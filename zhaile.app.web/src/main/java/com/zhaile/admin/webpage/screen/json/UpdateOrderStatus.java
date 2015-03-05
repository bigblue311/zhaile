package com.zhaile.admin.webpage.screen.json;

import java.io.IOException;


import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.zhaile.biz.common.Cache;
import com.zhaile.biz.web.json.PaymentQueryJson;
import com.zhaile.biz.web.manager.TransactionManager;

public class UpdateOrderStatus {
	@Autowired
	private Cache cache;
	
	@Autowired
	private TransactionManager transactionManager;
	
	public Boolean execute(@Param("id") Long id,
						@Param("customerId") Long customerId,
						@Param("employeeId") Long employeeId,
						@Param("status") String status,
						@Param("charge") Double charge,
						@Param("received") Double received,
						@Param("paymentStatus") String paymentStatus) throws IOException {
		PaymentQueryJson payment = null;
		payment = transactionManager.updatePaymentStatus(id, customerId, employeeId, status, charge, received, paymentStatus);
		if(payment!=null){
			cache.setPaymentStatus(payment);
			return true;
		} else {
			return false;
		}	
    }
}
