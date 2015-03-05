package com.zhaile.dal.cache.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.victor.framework.common.shared.Result;
import com.victor.framework.common.tools.CollectionTools;
import com.victor.framework.common.tools.LogTools;
import com.victor.framework.dal.cache.StaticCache;
import com.zhaile.dal.cache.AdvertisementCache;
import com.zhaile.dal.dao.ProductDAO;
import com.zhaile.dal.model.AdvertisementDO;
import com.zhaile.dal.model.ProductDO;

public class AdvertisementCacheImpl extends StaticCache<AdvertisementDO> implements AdvertisementCache {

	private static LogTools log = new LogTools(AdvertisementCache.class);
	
	private static final Map<Integer,AdvertisementDO> adMap = Maps.newConcurrentMap();
	private static final Map<Integer,List<ProductDO>> adWindow3Map = Maps.newConcurrentMap();
	private static final List<Integer> adWindow3Positions = Lists.newArrayList(50,51,52,53,54,55,56,57,58,59,60,61,62,63,64,65);
	
	@Autowired
	private ProductDAO productDAO;
	
	public AdvertisementCacheImpl() {
		super(AdvertisementDO.class.getSimpleName());
	}

	@Override
	public void reload() {
		reloadCache();
	}

	@Override
	public AdvertisementDO getCahce(Long id) {
		if(id==null) return null;
		return (AdvertisementDO)super.getCache(id);
	}
	
	@Override
	public Collection<AdvertisementDO> values() {
		return	Collections2.transform(super.cacheValues(), 
		new Function<Object, AdvertisementDO>() {
			@Override
			public AdvertisementDO apply(final Object obj) {
				return (AdvertisementDO)obj;
			}
		});
	}

	@Override
	public void reloadCache() {
		Result<List<AdvertisementDO>> result = super.queryForList("getAll");
		if(result.isSuccess() && CollectionTools.isNotEmpty(result.getDataObject())){
			super.clearCache();
			adMap.clear();
			for(AdvertisementDO ad: result.getDataObject()){
				super.updateCache(ad.getId(), ad);
				adMap.put(ad.getPosition(), ad);
				loadAdWindow3(ad);
			}
		} else {
			log.error("加载缓存数据失败");
		}
	}
	
	private void loadAdWindow3(AdvertisementDO ad) {
		if(adWindow3Positions.contains(ad.getPosition())){
			String[] prodIds = ad.getType().split(",");
			List<ProductDO> prodList = getProdByIds(convert(prodIds));
			adWindow3Map.put(ad.getPosition(), prodList);
		}
	}
	
	private List<ProductDO> getProdByIds(Long[] id) {
		Result<List<ProductDO>> result = productDAO.getByIds(id);
		if(result!=null && result.isSuccess()) {
			return result.getDataObject();
		}
		return Lists.newArrayList();
	}
	
	private Long[] convert(String[] id){
		Long[] result = new Long[id.length];
		for(int i =0;i<id.length;i++){
			result[i] = Long.parseLong(id[i]);
		}
		return result;
	}

	@Override
	public AdvertisementDO getAd(int position) {
		return adMap.get(position);
	}

	@Override
	public Collection<Object> getAll() {
		return super.cacheValues();
	}

	@Override
	public void click(Long id) {
		AdvertisementDO ad = new AdvertisementDO();
		ad.setId(id);
		super.updateBySID("click", ad);
	}

	@Override
	public List<ProductDO> getAdWindow3Products(int postion) {
		List<ProductDO> list = adWindow3Map.get(postion);
		if(list.size()>=12) {
			return list.subList(0, 12);
		}
		else {
			return list;
		}
	}

	@Override
	public List<ProductDO> getAdWindow3Imgs(int postion) {
		List<ProductDO> list = adWindow3Map.get(postion);
		return list.subList(list.size()-3, list.size());
	}
}
