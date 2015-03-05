package com.zhaile.admin.webpage.screen.json;

import java.io.IOException;


import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.zhaile.biz.common.Cache;
import com.zhaile.biz.web.json.PaymentQueryJson;
import com.zhaile.biz.web.manager.SmsTaskManager;
import com.zhaile.biz.web.manager.TransactionManager;

public class UpdateSmsStatus {
	@Autowired
	private Cache cache;
	
	@Autowired
	private TransactionManager transactionManager;
	
	@Autowired
	private SmsTaskManager smsTaskManager;
	
	public Boolean execute(@Param("id") Long id,@Param("paymentId") Long paymentId,@Param("smsStatus") String smsStatus) throws IOException {
		boolean updateStatus = smsTaskManager.updateSmsStatus(id, smsStatus);
		if(paymentId!=null){
			PaymentQueryJson payment = transactionManager.updatePaymentSmsStatus(paymentId, smsStatus);
			if(payment!=null){
				cache.setPaymentStatus(payment);
				return true;
			} else {
				return false;
			}
		}
		return updateStatus;
    }
}
