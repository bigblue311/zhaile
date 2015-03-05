package com.zhaile.web.webpage.screen.json;

import java.io.IOException;

import com.alibaba.citrus.turbine.Context;
import com.zhaile.web.common.screen.DefaultLayout;

public class PaymentStatus extends DefaultLayout {
	
	public void execute(Context context) throws IOException{
		context.put("paymentStatus", super.getPaymentStatus(null));
	}
}
