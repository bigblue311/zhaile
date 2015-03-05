package com.zhaile.web.webpage.screen;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alipay.config.AlipayConfig;
import com.alipay.util.AlipayCore;
import com.alipay.util.AlipaySubmit;
import com.google.common.collect.Maps;
import com.zhaile.biz.common.Cache;
import com.zhaile.dal.cache.key.SystemConfigCacheKey;
import com.zhaile.web.common.screen.DefaultLayout;

public class Alipay extends DefaultLayout {
	
	@Autowired
	private Cache cache;
	
	public void execute(@Param("paymentId") String paymentId,@Param("total_fee") String totalFee,Context context) {
    	super.load(context,null);
    	context.put("title", "富阳宅乐网");
    	context.put("sPara", goAlipay(paymentId,totalFee));
    }
	
	private Map<String, String> goAlipay(String paymentId, String totalFee){
		//支付类型
		String payment_type = "1";
		
		//必填，不能修改
		//服务器异步通知页面路径
		String notify_url = "http://www.fyzhaile.com/alipaycallback.htm";
		//需http://格式的完整路径，不能加?id=123这类自定义参数		//页面跳转同步通知页面路径
		
		String return_url = "http://www.fyzhaile.com/alipaycallback.htm";
		//需http://格式的完整路径，不能加?id=123这类自定义参数，不能写成http://localhost/		
		
		//卖家支付宝帐户
		//String seller_email = "2393952640@qq.com";
		
		//必填		
		//商户订单号
		String out_trade_no = paymentId;
		//商户网站订单系统中唯一订单号，必填		
		
		//订单名称
		String subject = "宅乐网外卖订单号"+paymentId;
		
		//必填		
		//付款金额
		if(cache.getSwitch(SystemConfigCacheKey.DEBUG_MODE_SWITCH)){
			totalFee =	"0.01";
		}
		
//		//防钓鱼时间戳
//		String anti_phishing_key = "";
//		try {
//			anti_phishing_key = AlipaySubmit.query_timestamp();
//		} catch (Exception e) {
//			e.printStackTrace();
//		} 
		//若要使用请调用类文件submit中的query_timestamp函数		
		
		//客户端的IP地址
		//String exter_invoke_ip = "115.28.135.31
		//非局域网的外网IP地址，如：221.0.0.1
		
		
		//////////////////////////////////////////////////////////////////////////////////
		
		//把请求参数打包成数组
		Map<String, String> sParaTemp = Maps.newHashMap();
		sParaTemp.put("service", "create_direct_pay_by_user");
		sParaTemp.put("partner", AlipayConfig.partner);
		sParaTemp.put("_input_charset", AlipayConfig.input_charset);
		sParaTemp.put("out_trade_no", out_trade_no);
		sParaTemp.put("subject", subject);
		sParaTemp.put("payment_type", payment_type);
		sParaTemp.put("total_fee", totalFee);
		sParaTemp.put("seller_id", AlipayConfig.partner);
		sParaTemp.put("notify_url", notify_url);
		sParaTemp.put("return_url", return_url);
		//sParaTemp.put("seller_email", seller_email);
		//除去数组中的空值和签名参数
        Map<String, String> sPara = AlipayCore.paraFilter(sParaTemp);
        sPara.put("sign", AlipaySubmit.buildRequestMysign(sPara));
        sPara.put("sign_type", AlipayConfig.sign_type);
        //生成签名结果
        return sPara;
	}
}
