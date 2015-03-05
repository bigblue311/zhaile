package com.zhaile.search.engine.db;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;

import com.google.common.collect.Lists;
import com.victor.framework.common.tools.CollectionTools;
import com.victor.framework.common.tools.StringTools;
import com.victor.framework.search.basic.KeyWord;
import com.victor.framework.search.basic.SearchResult;
import com.zhaile.dal.model.ProductDO;
import com.zhaile.dal.model.ShopDO;
import com.zhaile.dal.model.ShopTagDO;
import com.zhaile.search.engine.Searchable;

public class DBSearchFacade implements InitializingBean{
	private List<Searchable<?>> searchableList = Lists.newArrayList();
	private Searchable<?> pSearchable;
	private Searchable<?> sSearchable;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		searchableList.add(sSearchable);
		searchableList.add(pSearchable);
	}
	
	public List<SearchResult<?>> findShop(Long categoryId,String keyword){
		List<SearchResult<?>> searchResultList = Lists.newArrayList();
		for(Searchable<?> searchable : searchableList){
			searchResultList.add(searchable.findShop(categoryId,keyword));
		}
		return searchResultList;
	}
	
	public List<SearchResult<?>> getProduct(Long... id){
		List<SearchResult<?>> searchResultList = Lists.newArrayList();
		searchResultList.add(pSearchable.getById(id));
		return searchResultList;
	}
	
	public List<SearchResult<?>> getShop(Long... id){
		List<SearchResult<?>> searchResultList = Lists.newArrayList();
		searchResultList.add(sSearchable.getById(id));
		return searchResultList;
	}
	
	public List<SearchResult<?>> findProduct(Long shopId){
		List<SearchResult<?>> searchResultList = Lists.newArrayList();
		for(Searchable<?> searchable : searchableList){
			searchResultList.add(searchable.findProduct(shopId));
		}
		return searchResultList;
	}
	
	public List<SearchResult<?>> findProduct(Long shopId,Long categoryId){
		List<SearchResult<?>> searchResultList = Lists.newArrayList();
		for(Searchable<?> searchable : searchableList){
			searchResultList.add(searchable.findProduct(shopId,categoryId));
		}
		return searchResultList;
	}
	
	public List<SearchResult<?>> findProduct(Long shopId, String keyword){
		List<SearchResult<?>> searchResultList = Lists.newArrayList();
		for(Searchable<?> searchable : searchableList){
			searchResultList.add(searchable.findProduct(shopId,keyword));
		}
		return searchResultList;
	}
	
	public List<SearchResult<?>> findProduct(Long shopId, Long categoryId, String keyword){
		List<SearchResult<?>> searchResultList = Lists.newArrayList();
		for(Searchable<?> searchable : searchableList){
			searchResultList.add(searchable.findProduct(shopId, categoryId, keyword));
		}
		return searchResultList;
	}
	
	public List<SearchResult<?>> search(String keyword){
		List<SearchResult<?>> searchResultList = Lists.newArrayList();
		for(Searchable<?> searchable : searchableList){
			searchResultList.add(searchable.search(keyword));
		}
		return searchResultList;
	}
	
	public List<SearchResult<?>> search(Long categoryId){
		List<SearchResult<?>> searchResultList = Lists.newArrayList();
		for(Searchable<?> searchable : searchableList){
			searchResultList.add(searchable.search(categoryId));
		}
		return searchResultList;
	}
	
	public List<SearchResult<?>> search(String keyword, Date gmtModifyStart, Date gmtModifyEnd){
		List<SearchResult<?>> searchResultList = Lists.newArrayList();
		for(Searchable<?> searchable : searchableList){
			searchResultList.add(searchable.search(keyword, gmtModifyStart, gmtModifyEnd));
		}
		return searchResultList;
	}
	
	public List<SearchResult<?>> search(Long tagId, Date gmtModifyStart, Date gmtModifyEnd){
		List<SearchResult<?>> searchResultList = Lists.newArrayList();
		for(Searchable<?> searchable : searchableList){
			searchResultList.add(searchable.search(tagId, gmtModifyStart, gmtModifyEnd));
		}
		return searchResultList;
	}
	
	public List<SearchResult<?>> search(Long customerId, String keyword){
		List<SearchResult<?>> searchResultList = Lists.newArrayList();
		for(Searchable<?> searchable : searchableList){
			searchResultList.add(searchable.search(customerId, keyword));
		}
		return searchResultList;
	}
	
	public List<SearchResult<?>> search(Long customerId,Long categoryId, String keyword){
		List<SearchResult<?>> searchResultList = Lists.newArrayList();
		for(Searchable<?> searchable : searchableList){
			searchResultList.add(searchable.search(customerId, categoryId, keyword));
		}
		return searchResultList;
	}
	
	public List<SearchResult<?>> search(Long customerId, Long tagId){
		List<SearchResult<?>> searchResultList = Lists.newArrayList();
		for(Searchable<?> searchable : searchableList){
			searchResultList.add(searchable.search(customerId, tagId));
		}
		return searchResultList;
	}
	
	public List<SearchResult<?>> search(Long customerId, String keyword, Date gmtModifyStart, Date gmtModifyEnd){
		List<SearchResult<?>> searchResultList = Lists.newArrayList();
		for(Searchable<?> searchable : searchableList){
			searchResultList.add(searchable.search(customerId, keyword, gmtModifyStart, gmtModifyEnd));
		}
		return searchResultList;
	}
	
	public List<SearchResult<?>> search(Long customerId, Long tagId, Date gmtModifyStart, Date gmtModifyEnd){
		List<SearchResult<?>> searchResultList = Lists.newArrayList();
		for(Searchable<?> searchable : searchableList){
			searchResultList.add(searchable.search(customerId, tagId, gmtModifyStart, gmtModifyEnd));
		}
		return searchResultList;
	}
	
	public List<KeyWord> keywordAutoComplete(Long shopId, Long categoryId,String key){
		if(StringTools.isEmpty(key)){
			return Lists.newArrayList();
		}
		List<KeyWord> list = Lists.newArrayList();
		for(Searchable<?> searchable : searchableList){
			List<KeyWord> keyList = searchable.keywordAutoComplete(shopId,categoryId,key);
			if(CollectionTools.isNotEmpty(keyList)){
				for(KeyWord rkey : keyList){
					if(!list.contains(rkey)){
						list.add(rkey);
					}
				}
			}
		}
		return list;
	}
	
	public List<ShopTagDO> tagAutoComplete(String key){
		if(StringTools.isEmpty(key)){
			return Lists.newArrayList();
		}
		List<ShopTagDO> list = Lists.newArrayList();
		for(Searchable<?> searchable : searchableList){
			List<ShopTagDO> keyList = searchable.tagAutoComplete(key);
			if(CollectionTools.isNotEmpty(keyList)){
				for(ShopTagDO rkey : keyList){
					if(!list.contains(rkey)){
						list.add(rkey);
					}
				}
			}
		}
		return list;
	}
	
	public List<String> keywordTop(){
		List<String> list = Lists.newArrayList();
		for(Searchable<?> searchable : searchableList){
			List<String> keyList = searchable.keywordTop10();
			if(CollectionTools.isNotEmpty(keyList)){
				for(String rkey : keyList){
					if(!list.contains(rkey)){
						list.add(rkey);
					}
				}
			}
		}
		return list;
	}
	
	public List<ShopTagDO> tagTop(){
		List<ShopTagDO> list = Lists.newArrayList();
		for(Searchable<?> searchable : searchableList){
			List<ShopTagDO> keyList = searchable.tagTop10();
			if(CollectionTools.isNotEmpty(keyList)){
				for(ShopTagDO rkey : keyList){
					if(!list.contains(rkey)){
						list.add(rkey);
					}
				}
			}
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<ProductDO> bestProductTop10(){
		return (List<ProductDO>)pSearchable.bestSellTop10().getDataObject();
	}
	
	@SuppressWarnings("unchecked")
	public List<ShopDO> bestShopTop10(){
		return (List<ShopDO>)sSearchable.bestSellTop10().getDataObject();
	}
	
	public List<SearchResult<?>> random(int count,Long... categoryId){
		List<SearchResult<?>> searchResultList = Lists.newArrayList();
		for(Searchable<?> searchable : searchableList){
			searchResultList.add(searchable.getRandom(count,categoryId));
		}
		return searchResultList;
	}
	
	@SuppressWarnings("unchecked")
	public List<ProductDO> randomProduct(int count,Long... categoryId){
		return (List<ProductDO>)pSearchable.getRandom(count,categoryId).getDataObject();
	}
	
	@SuppressWarnings("unchecked")
	public List<ShopDO> randomShop(int count,Long... categoryId){
		return (List<ShopDO>)sSearchable.getRandom(count,categoryId).getDataObject();
	}

	public void setpSearchable(Searchable<?> pSearchable) {
		this.pSearchable = pSearchable;
	}

	public void setsSearchable(Searchable<?> sSearchable) {
		this.sSearchable = sSearchable;
	}
}
