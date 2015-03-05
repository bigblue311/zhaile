package com.zhaile.dal.cache;

import java.util.Collection;
import java.util.List;

import com.zhaile.dal.model.AdvertisementDO;
import com.zhaile.dal.model.ProductDO;

public interface AdvertisementCache {
	void reload();
	
	AdvertisementDO getCahce(Long id);
	
	void click(Long id);
	
	void updateDB(AdvertisementDO ad);
	
	Collection<AdvertisementDO> values();
	
	AdvertisementDO getAd(int position);
	
	List<ProductDO> getAdWindow3Products(int postion);
	
	List<ProductDO> getAdWindow3Imgs(int postion);
}
