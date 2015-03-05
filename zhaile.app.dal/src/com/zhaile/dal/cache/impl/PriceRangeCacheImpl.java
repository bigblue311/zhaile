package com.zhaile.dal.cache.impl;

import java.util.List;

import com.google.common.collect.Lists;
import com.victor.framework.search.basic.PriceRange;
import com.zhaile.dal.cache.PriceRangeCache;

public class PriceRangeCacheImpl implements PriceRangeCache {

	public final static PriceRange RANGE_0_5 = new PriceRange(1l,0.0,5.0,"0-5元");
	public final static PriceRange RANGE_5_10 = new PriceRange(2l,5.0,10.0,"5-10元");
	public final static PriceRange RANGE_10_20 = new PriceRange(3l,10.0,20.0,"10-20元");
	public final static PriceRange RANGE_20_50 = new PriceRange(4l,20.0,50.0,"20-50元");
	public final static PriceRange RANGE_50_100 = new PriceRange(5l,50.0,100.0,"50-100元");
	public final static PriceRange RANGE_100_1000 = new PriceRange(6l,100.0,1000.0,"我是土豪");
	
	public final static List<PriceRange> ranges = Lists.newArrayList(RANGE_0_5,RANGE_5_10,RANGE_10_20,RANGE_20_50,RANGE_50_100,RANGE_100_1000);

	@Override
	public PriceRange getPriceRange(double price){
		for(PriceRange range : ranges) {
			if(range.inRange(price)) {
				return range;
			}
		}
		return null;
	}

	@Override
	public PriceRange getById(long id) {
		for(PriceRange range : ranges) {
			if(range.getId() == id) {
				return range;
			}
		}
		return null;
	}

	@Override
	public PriceRange getPRange(String id) {
		Long idl = Long.parseLong(id);
		return getById(idl);
	}

}
