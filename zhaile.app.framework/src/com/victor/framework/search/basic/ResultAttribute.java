package com.victor.framework.search.basic;

import java.util.Comparator;
import java.util.Map;

import com.google.common.collect.Maps;

public class ResultAttribute {	
	private Map<Long,Integer> priceRange = Maps.newTreeMap(new ElementsComparable());
	private Map<Long,Integer> shopId = Maps.newTreeMap(new ElementsComparable());
	private Map<Long,Integer> categoryId = Maps.newTreeMap(new ElementsComparable());
	
	public Map<Long, Integer> getPriceRange() {
		return priceRange;
	}
	public void setPriceRange(Map<Long, Integer> priceRange) {
		this.priceRange = priceRange;
	}
	public Map<Long, Integer> getShopId() {
		return shopId;
	}
	public void setShopId(Map<Long, Integer> shopId) {
		this.shopId = shopId;
	}
	public Map<Long, Integer> getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Map<Long, Integer> categoryId) {
		this.categoryId = categoryId;
	}

	class ElementsComparable implements Comparator<Long>{
		@Override
		public int compare(Long o1, Long o2) {
			return o1.intValue()-o2.intValue();
		}
	}
}
