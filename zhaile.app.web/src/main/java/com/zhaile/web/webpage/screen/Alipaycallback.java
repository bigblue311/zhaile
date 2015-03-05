package com.zhaile.web.webpage.screen;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Navigator;
import com.alipay.util.AlipayNotify;
import com.zhaile.biz.web.manager.TransactionManager;
import com.zhaile.dal.enumerate.PaymentStatusEnum;

public class Alipaycallback {
	
	private static final String SUCCESS = "T";
	private static final String FAILED = "F";
	
	@Autowired
    private HttpServletRequest request;
	
	@Autowired
	private TransactionManager transactionManager;
	
	public void execute(Navigator nav) throws UnsupportedEncodingException {
		String success = FAILED;
		String msg = "交易验证失败";
		Map<String,String> params = new HashMap<String,String>();
		Map<String,String[]> requestParams = request.getParameterMap();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
			params.put(name, valueStr);
		}
		
		//商户订单号
		String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
		Long paymentId = Long.parseLong(out_trade_no);
		//支付宝交易号
		String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

		//交易状态
		String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");

		if(AlipayNotify.verify(params)){
			if(trade_status.equals("TRADE_FINISHED")){
				transactionManager.updatePaymentStatus(paymentId, PaymentStatusEnum.支付成功.getCode(),trade_no,trade_status);
			} else if (trade_status.equals("TRADE_SUCCESS")){
				transactionManager.updatePaymentStatus(paymentId, PaymentStatusEnum.支付成功.getCode(),trade_no,trade_status);
			}
			success = SUCCESS;
			msg = "支付宝付款成功";
		}else{//验证失败
			transactionManager.updatePaymentStatus(paymentId, PaymentStatusEnum.支付失败.getCode(),trade_no,trade_status);
			success = FAILED;
			msg = "支付宝付款失败";
		}
		nav.redirectTo("zhaile").withTarget("finish.vm").withParameter("success", success).withParameter("msg", msg);
    }
}
