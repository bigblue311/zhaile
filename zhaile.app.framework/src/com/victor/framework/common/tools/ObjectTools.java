package com.victor.framework.common.tools;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.collect.Lists;

public class ObjectTools {
	
	public static boolean isInteger(Object obj){
		return obj instanceof Integer;
	}
	
	public static boolean isBoolean(Object obj){
		return obj instanceof Boolean;
	}
	
	public static boolean isLong(Object obj){
		return obj instanceof Long;
	}
	
	public static boolean isDouble(Object obj){
		return obj instanceof Double;
	}
	
	public static boolean isBigDecimal(Object obj){
		return obj instanceof BigDecimal;
	}
	
	public static boolean isString(Object obj){
		String str=	obj.toString();
		Pattern pattern = Pattern.compile("(.\\w+)+@\\w+",Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(str);
		return !matcher.matches();
	}
	
	public static boolean isDate(Object obj){
		return obj instanceof Date;
	}
	
	public static Object getValue(Object obj, Field field){
		List<Field> Fileds = Lists.newArrayList(obj.getClass().getDeclaredFields());
		if(Fileds.contains(field)){
			Method[] getMethods = obj.getClass().getDeclaredMethods();
			for(Method getMethod : getMethods){
				if(getMethod.getName().toLowerCase().equals("get"+field.getName().toLowerCase())){
					try {
						return getMethod.invoke(obj, new Object[0]);
					} catch (Exception e) {
						return null;
					} 
				}
			}
		}
		return null;
	}
	
	public static void copy(Object from,Object to){
		Field[] fromFileds = from.getClass().getDeclaredFields();
		Field[] toFileds = to.getClass().getDeclaredFields();
		for(Field fromField : fromFileds) {
			try {
				for(Field toField : toFileds) {
					if(fromField.getName().equals(toField.getName())) {
						Method[] setMethods = to.getClass().getDeclaredMethods();
						for(Method setMethod : setMethods){
							if(setMethod.getName().toLowerCase().equals("set"+toField.getName().toLowerCase())){
								Method[] getMethods = from.getClass().getDeclaredMethods();
								for(Method getMethod : getMethods){
									if(getMethod.getName().toLowerCase().equals("get"+toField.getName().toLowerCase())){
										Object getValue = getMethod.invoke(from, new Object[0]);
										setMethod.invoke(to, getValue);
									}
								}
							}
						}
					}
				}
			} catch (Exception e) {
				continue;
			}
		}
	}
	
	public static void main(String[] args){
		Long test = new Long("100");
		System.out.println(isString(test));
	}
}
