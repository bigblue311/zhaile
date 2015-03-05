package com.victor.framework.common.tools;

import java.util.Map;

import com.google.common.collect.Maps;

public class SecurityTools {
	private static Map<String,String> filterMap = Maps.newHashMap();
	
	static {
		filterMap.put("<", "&lt;");
		filterMap.put(">", "&gt;");
		filterMap.put("'", "&quot;");
		filterMap.put("\"", "&quot;");
		filterMap.put("\\", "");
		filterMap.put("=", "等于");
		filterMap.put("@", "");
		filterMap.put("(", "&lt;");
		filterMap.put(")", "&gt;");
		filterMap.put("{", "&lt;");
		filterMap.put("}", "&gt;");
		filterMap.put("../", "");
		filterMap.put("|", "");
		filterMap.put(";", "");
		filterMap.put("and", "a n d");
		filterMap.put("or", "o r");
		filterMap.put("script", "s c r i p t");
		filterMap.put("alert", "a l e r t");
		filterMap.put("on", "o n");
		filterMap.put("like", "l i k e");
		filterMap.put("join", "j o i n");
		filterMap.put("insert", "i n s e r t");
		filterMap.put("update", "u p d a t e");
		filterMap.put("select", "s e l e c t");
		filterMap.put("delete", "d e l e t e");
		filterMap.put("del", "d e l");
		filterMap.put("passwd", "p a s s w d");
		filterMap.put("windows", "w i n d o w s");
		filterMap.put("etc", "e t c");
		filterMap.put("$", " $ ");
		filterMap.put("%", " % ");
	}
	
	public static void injectionFilter(String context){
		if(context == null) {
			return;
		}
		context = context.toLowerCase();
		for(Map.Entry<String, String> entry : filterMap.entrySet()){
			context = context.replace(entry.getKey(), entry.getValue());
		}
	}
}
