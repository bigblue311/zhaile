package com.zhaile.dal.test;

import com.zhaile.dal.cache.AdvertisementCache;
import com.zhaile.dal.cache.CategoryCache;
import com.zhaile.dal.cache.HeadImgCache;
import com.zhaile.dal.cache.SystemConfigCache;
import com.zhaile.dal.cache.impl.AdvertisementCacheImpl;
import com.zhaile.dal.cache.impl.CategoryCacheImpl;
import com.zhaile.dal.cache.impl.HeadImgCacheImpl;
import com.zhaile.dal.cache.impl.SystemConfigCacheImpl;
import com.zhaile.dal.cache.key.CategoryCacheKey;
import com.zhaile.dal.cache.key.HeadImgCacheKey;
import com.zhaile.dal.cache.key.SystemConfigCacheKey;

public class CacheTest {
	public static void main(String[] args){
		CategoryCache categoryCache = new CategoryCacheImpl();
		categoryCache.reload();
		System.out.println(categoryCache.getCate(CategoryCacheKey.美食.toString()));
		
		HeadImgCache imgCache = new HeadImgCacheImpl();
		imgCache.reload();
		System.out.println(imgCache.getCahce(HeadImgCacheKey.默认));
		
		SystemConfigCache configCache = new SystemConfigCacheImpl();
		configCache.reload();
		System.out.println(configCache.getConfig(SystemConfigCacheKey.DEFAULT_CHARGE));
		
		AdvertisementCache adCache = new AdvertisementCacheImpl();
		adCache.reload();
		System.out.println(adCache.getAd(0));
		
	}
}
