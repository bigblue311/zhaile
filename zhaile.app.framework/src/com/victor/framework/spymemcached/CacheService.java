package com.victor.framework.spymemcached;

import java.util.List;

/**
 * 缓存服务
 * @author alex
 *
 */
public interface CacheService {
	/**
	 * 获得缓存
	 * @param key
	 * @return
	 */
	String get(String key);
	/**
	 * 获得缓存对象
	 * @param <T>
	 * @param key
	 * @return
	 */
	<T> T getObject(String key,Class<T> clazz);
	
	/**
	 * 获得缓存列表对象
	 * @param key
	 * @param clazz
	 * @return
	 */
	<T> List<T> getList(String key,Class<T> clazz);
	/**
	 * 设置缓存
	 * @param key
	 * @param value
	 */
	void set(String key,String value);
	/**
	 * 设置缓存(过期时间：秒)
	 * @param key
	 * @param value
	 * @param exprieSecond
	 */
	void set(String key,String value,int exprieSecond);
	/**
	 * 设置缓存
	 * @param key
	 * @param value
	 */
	void setObject(String key,Object value);
	/**
	 * 设置缓存(过期时间：秒)
	 * @param key
	 * @param value
	 * @param exprieSecond
	 */
	void setObject(String key,Object value,int exprieSecond);
	/**
	 * 删除缓存
	 * @param key
	 */
	void del(String key);
	/**
	 * 当key不存在时设置缓存(过期时间：秒)，如果存在，返回失败
	 * @param key
	 * @param value
	 * @param exprieSecond
	 */
	boolean add(String key,String value,int exprieSecond);
	
	/**
	 * 是否开启缓存,默认是开启
	 * @param enable
	 */
	void setEnable(boolean enable);
}
