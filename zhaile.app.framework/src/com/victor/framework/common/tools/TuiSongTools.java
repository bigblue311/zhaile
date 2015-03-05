package com.victor.framework.common.tools;

import java.io.IOException;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.NotificationTemplate;

public class TuiSongTools {
	private static String appId = "mJrTnX1AY698Puda1gqfg4";
	private static String appKey = "dWQjnN4fKb6YQGe5rRjvb5";
	private static String master = "xC2N19wsHs9R4vvrWNQ7D8";
	private static String host = "http://sdk.open.api.igexin.com/apiex.htm";
	
	public static void main(String[] args){
		System.out.println(push("b3218de3c52fffffe730492bbc84f7b8","Hello\n1123"));
	}
	
	public static boolean push(String cid, String receipt){
		IGtPush push = new IGtPush(host,appKey,master);
		try {
			push.connect();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		NotificationTemplate template = getNotifiTemplate(receipt);
		SingleMessage message = new SingleMessage();
		message.setOffline(true);
		message.setOfflineExpireTime(24*3600*1000);
		message.setData(template);
		
		Target target = new Target();
		target.setAppId(appId);
		target.setClientId(cid);
		
		IPushResult result = push.pushMessageToSingle(message, target);
		return result.getResponse().get("result").toString().equals("ok");
	}
	
	private static NotificationTemplate getNotifiTemplate(String receipt){
		NotificationTemplate template = new NotificationTemplate();
		template.setAppId(appId);
		template.setAppkey(appKey);
		template.setTitle("新的订单");
		template.setText("您有新的订单，快来接单啦");
		template.setIsRing(true);
		template.setIsVibrate(true);
		template.setIsClearable(true);
		template.setTransmissionType(1);
		template.setTransmissionContent(receipt);
		return template;
	}
}
