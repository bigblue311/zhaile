package com.victor.framework.test;

import com.victor.framework.spymemcached.MemcachedServiceImpl;

public class MemcachedTest {
	public static void main(String[] args) throws Exception{
		MemcachedServiceImpl cache = new MemcachedServiceImpl();
		cache.setHost("c0aa126057f54d03.m.cnqdalicm9pub001.ocs.aliyuncs.com");
		cache.setPort("11211");
		cache.setUsername("c0aa126057f54d03");
		cache.setPassword("zhaileAdm1n");
		cache.setAuth(true);
		cache.setEnable(true);
		cache.setKeyPrefix("zhaile.test");
		cache.afterPropertiesSet();
		
		cache.set("hello", "hello memcached");
		System.out.println(cache.get("hello"));
	}
}
