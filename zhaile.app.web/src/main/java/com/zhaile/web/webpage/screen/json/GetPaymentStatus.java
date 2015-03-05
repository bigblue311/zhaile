package com.zhaile.web.webpage.screen.json;

import java.io.IOException;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.zhaile.biz.web.json.PaymentQueryJson;
import com.zhaile.web.common.screen.DefaultLayout;

public class GetPaymentStatus extends DefaultLayout {
	
	public PaymentQueryJson execute(@Param("userId") Long userId) throws IOException{
		return super.getPaymentStatus(userId);
	}
}
