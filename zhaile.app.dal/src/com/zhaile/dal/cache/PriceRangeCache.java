package com.zhaile.dal.cache;

import com.victor.framework.search.basic.PriceRange;

public interface PriceRangeCache {	
	PriceRange getById(long id);
	
	PriceRange getPRange(String id);
	
	PriceRange getPriceRange(double price);
}
