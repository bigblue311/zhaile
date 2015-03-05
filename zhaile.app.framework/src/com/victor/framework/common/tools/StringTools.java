package com.victor.framework.common.tools;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.collect.Lists;

public class StringTools {
	public static boolean isAllEmpty(String... str){
		boolean checkResult = true;
		for(String s : str){
			if(isNotEmpty(s)){
				checkResult = false;
			}
		}
		return checkResult;
	}
	
	public static boolean isAnyEmpty(String... str){
		boolean checkResult = false;
		for(String s : str){
			if(isEmpty(s)){
				checkResult = true;
			}
		}
		return checkResult;
	}
	
	public static boolean isEmpty(String str){
		return (str == null || str.isEmpty() || str.trim().isEmpty());
	}
	
	public static boolean isNotEmpty(String str){
		return !isEmpty(str);
	}
	
	public static String join(String seperate, Object[] str){
		if(str == null || str.length==0 || isEmpty(seperate)){
			return "";
		}
		String str1 = str[0].toString().trim();
		StringBuilder sb = new StringBuilder(str1);
		for(int i=1;i<str.length;i++){
			String each = str[i].toString().trim();
			if(isNotEmpty(each)){
				sb.append(each);
				sb.append(seperate);
			}
		}
		return sb.toString();
	}
	
	public static String join(String seperate,String... str){
		return join(seperate,str);
	}
	
	public static String join(String seperate,List<Object> str){
		return join(seperate,str.toArray());
	}
	
	public static String joinValue(String seperate,Map<Object,Object> str){
		return join(seperate,str.values().toArray());
	}
	
	public static String joinKey(String seperate,Map<Object,Object> str){
		return join(seperate,str.keySet().toArray());
	}
	
	public static String join(String seperate,Set<Object> str){
		return join(seperate,str.toArray());
	}
	
	public static List<String> split(String str,String seperate){
		String[] spt = str.split(seperate);
		List<String> list = Lists.newArrayList();
		for(String each:spt){
			if(isNotEmpty(each)){
				list.add(each);
			}
		}
		return list;
	}
	
	public static String cut(String str,String from, String to){
		if(isAnyEmpty(str,from,to)){
			return "";
		}
		int fromIndex = str.indexOf(from)+from.length();
		int toIndex = str.indexOf(to);
		if(fromIndex<toIndex){
			return str.substring(fromIndex, toIndex);
		}
		return "";
	}
	
	public static String subStr(String str,int fromIndex,int length){
		if(isEmpty(str) || fromIndex<0 || fromIndex>str.length() || length<=0){
			return "";
		}
		return str.substring(fromIndex, fromIndex+length);
	}
	
	public static String reverse(String str){
		StringBuilder sb = new StringBuilder(str);
		return sb.reverse().toString();
	}
	
	public static Boolean isEmail(String str){
		if(isEmpty(str)) return false;
		String check = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$"; 
		Pattern pattern = Pattern.compile(check);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}
	
	public static Boolean isMobile(String str){
		if(isEmpty(str)) return false;
		String check = "^(1(([35][0-9])|(47)|[5][036789]|[8][012346789]))\\d{8}$"; 
		Pattern pattern = Pattern.compile(check);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}
	
	public static Boolean equalAny(String key,String... any){
		if(key == null) {
			return false;
		}
		for(String loop : any){
			if(loop.equals(key)){
				return true;
			}
		}
		return false;
	}
}
