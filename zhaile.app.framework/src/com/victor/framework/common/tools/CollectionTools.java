package com.victor.framework.common.tools;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Lists;
import com.victor.framework.dal.basic.EntityDO;

public class CollectionTools {
	public static boolean isEmpty(Object[] list){
		if(list == null || list.length == 0){
			return true;
		} else {
			for(Object obj : list) {
				if(obj != null) {
					return false;
				}
			}
			return true;
		}
	}
	
	public static boolean isNotEmpty(Object[] list){
		return !isEmpty(list);
	}
	
	public static boolean isEmpty(List<?> list){
		if(list == null) return true;
		return isEmpty(list.toArray());
	}
	
	public static boolean isNotEmpty(List<?> list){
		return !isEmpty(list);
	}
	
	public static boolean isEmpty(Collection<?> list){
		if(list == null) return true;
		return isEmpty(list.toArray());
	}
	
	public static boolean isNotEmpty(Collection<?> list){
		return !isEmpty(list);
	}
	
	public static boolean isEmpty(Set<?> list){
		if(list == null) return true;
		return isEmpty(list.toArray());
	}
	
	public static boolean isNotEmpty(Set<?> list){
		return !isEmpty(list);
	}
	
	public static boolean isEmpty(Map<?,?> map){
		if(map == null || map.isEmpty() || isEmpty(map.keySet())) {
			return true;
		}
		return isEmpty(map.values().toArray());
	}
	
	public static boolean isNotEmpty(Map<?,?> map){
		return !isEmpty(map);
	}
	
	public static Object[] sort(Object[] array){
		if(isEmpty(array)) return array;
		List<Object> list = Lists.asList(0, array);
		return sort(list).toArray();
	}
	
	public static List<?> sort(List<?> list){
		if(isEmpty(list)) return list;
		Object obj = list.get(0);
		if(ObjectTools.isInteger(obj)){
			Collections.sort(list, new CollectionTools.IntComparable());
			return list;
		}
		if(ObjectTools.isLong(obj)){
			Collections.sort(list, new CollectionTools.LongComparable());
			return list;
		}
		if(ObjectTools.isDouble(obj)){
			Collections.sort(list, new CollectionTools.DoubleComparable());
			return list;
		}
		if(ObjectTools.isBigDecimal(obj)){
			Collections.sort(list, new CollectionTools.BigDecimalComparable());
			return list;
		}
		if(ObjectTools.isString(obj)){
			Collections.sort(list, new CollectionTools.StringComparable());
			return list;
		}
		return null;
	}
	
	public static List<?> sort(List<?> list, Field field){
		Collections.sort(list, new CollectionTools.feildComparable(field));
		return list;
	}
	
	public static Set<?> sort(Set<?> set){
		return new java.util.TreeSet<Object>(set);
	}
	
	public static Map<String,?> sort(Map<String,?> map){
		return new java.util.TreeMap<String,Object>(map);
	}
	
	public static int compare(EntityDO o1,EntityDO o2, Field field){
		CollectionTools.genericComparable compare = new CollectionTools.genericComparable();
		Object v1 = ObjectTools.getValue(o1, field);
		Object v2 = ObjectTools.getValue(o2, field);
		return compare.compare(v1, v2);
	}
	
	public static int compare(Object o1,Object o2, Field field){
		CollectionTools.genericComparable compare = new CollectionTools.genericComparable();
		Object v1 = ObjectTools.getValue(o1, field);
		Object v2 = ObjectTools.getValue(o2, field);
		return compare.compare(v1, v2);
	}
	
	public static int compare(Object o1,Object o2){
		CollectionTools.genericComparable compare = new CollectionTools.genericComparable();
		return compare.compare(o1, o2);
	}
	
	static class IntComparable implements Comparator<Object>{
	    @Override
	    public int compare(Object o1, Object o2) {
	    	if(o1==null && o2==null){
				return 0;
			}
			if(o1!=null && o2==null){
				return 1;
			}
			if(o1==null && o2!=null){
				return -1;
			}
	    	Integer _o1 = Integer.parseInt(o1.toString());
	    	Integer _o2 = Integer.parseInt(o2.toString());
	        return (_o1>_o2 ? -1 : (_o1==_o2 ? 0 : 1));
	    }
	} 
	
	static class StringComparable implements Comparator<Object>{
	    @Override
	    public int compare(Object o1, Object o2) {
	    	if(o1==null && o2==null){
				return 0;
			}
			if(o1!=null && o2==null){
				return 1;
			}
			if(o1==null && o2!=null){
				return -1;
			}
	    	char[] c1 = o1.toString().toCharArray();
	    	char[] c2 = o2.toString().toCharArray();
	    	for(int i = 0; i<c1.length && i<c2.length;i++){
	    		if(c1[i] != c2[i]){
	    			return c1[i] - c2[i];
	    		}
	    	}
	    	if(c1.length>c2.length) return 1;
	    	if(c1.length<c2.length) return -1;
	    	return 0;
	    }
	}
	
	static class LongComparable implements Comparator<Object>{
		@Override
	    public int compare(Object o1, Object o2) {
			if(o1==null && o2==null){
				return 0;
			}
			if(o1!=null && o2==null){
				return 1;
			}
			if(o1==null && o2!=null){
				return -1;
			}
			Long _o1 = Long.parseLong(o1.toString());
			Long _o2 = Long.parseLong(o2.toString());
			return (_o1>_o2 ? -1 : (_o1==_o2 ? 0 : 1));
	    }
	}
	
	static class DoubleComparable implements Comparator<Object>{
		@Override
	    public int compare(Object o1, Object o2) {
			if(o1==null && o2==null){
				return 0;
			}
			if(o1!=null && o2==null){
				return 1;
			}
			if(o1==null && o2!=null){
				return -1;
			}
			Double _o1 = Double.parseDouble(o1.toString());
			Double _o2 = Double.parseDouble(o2.toString());
			return (_o1>_o2 ? -1 : (_o1==_o2 ? 0 : 1));
	    }
	}
	
	static class BigDecimalComparable implements Comparator<Object>{
		@Override
	    public int compare(Object o1, Object o2) {
			if(o1==null && o2==null){
				return 0;
			}
			if(o1!=null && o2==null){
				return 1;
			}
			if(o1==null && o2!=null){
				return -1;
			}
			BigDecimal _o1 = new BigDecimal(o1.toString());
			BigDecimal _o2 = new BigDecimal(o2.toString());
	        return (_o1.compareTo(_o2));
	    }
	}
	
	static class DateComparable implements Comparator<Object>{
		@Override
	    public int compare(Object o1, Object o2) {
			if(o1==null && o2==null){
				return 0;
			}
			if(o1!=null && o2==null){
				return 1;
			}
			if(o1==null && o2!=null){
				return -1;
			}
			Date _o1 = (Date)o1;
			Date _o2 = (Date)o2;
	        return (_o1.compareTo(_o2));
	    }
	}
	
	static class genericComparable implements Comparator<Object>{
		@Override
	    public int compare(Object o1, Object o2) {
			if(ObjectTools.isInteger(o1)){
				IntComparable intComparable = new IntComparable();
				return intComparable.compare(o1, o2);
			}
			if(ObjectTools.isLong(o1)){
				LongComparable longComparable = new LongComparable();
				return longComparable.compare(o1, o2);
			}
			if(ObjectTools.isDouble(o1)){
				DoubleComparable doubleComparable = new DoubleComparable();
				return doubleComparable.compare(o1, o2);
			}
			if(ObjectTools.isBigDecimal(o1)){
				BigDecimalComparable bigDecimalComparable = new BigDecimalComparable();
				return bigDecimalComparable.compare(o1, o2);
			}
			if(ObjectTools.isDate(o1)){
				DateComparable dateComparable = new DateComparable();
				return dateComparable.compare(o1, o2);
			}
			if(ObjectTools.isString(o1)){
				StringComparable stringComparable = new StringComparable();
				return stringComparable.compare(o1, o2);
			}
			//不排序
			return 0;
	    }
	}
	
	static class feildComparable implements Comparator<Object>{

		private Field field = null;
		
		public feildComparable(Field field){
			this.field = field;
		}
		
		@Override
		public int compare(Object o1, Object o2) {
			return CollectionTools.compare(o1,o2,field);
		}
		
	}
}
