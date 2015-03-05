package com.victor.framework.common.tools;

import com.alibaba.fastjson.JSON;

public class JsonTools {
	public static String toJson(Object obj){
		return JSON.toJSONString(obj);
	}
	
	public static <T> T fromJson(String json, Class<T> clazz){
		return JSON.parseObject(json,clazz);
	}
}
