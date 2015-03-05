package com.zhaile.search.engine;

import java.util.Date;
import java.util.List;


import com.victor.framework.dal.basic.EntityDO;
import com.victor.framework.search.basic.KeyWord;
import com.victor.framework.search.basic.SearchQuery;
import com.victor.framework.search.basic.SearchResult;
import com.victor.framework.search.cache.Computable;
import com.zhaile.dal.model.ShopTagDO;

public interface Searchable<Entity extends EntityDO> extends Computable<SearchQuery, SearchResult<Entity>> {
	SearchResult<Entity> getById(Long... id);
	
	SearchResult<Entity> findShop(Long categoryId,String keyword);
	
	SearchResult<Entity> findProduct(Long shopId);
	
	SearchResult<Entity> findProduct(Long shopId, Long categoryId);
	
	SearchResult<Entity> findProduct(Long shopId, String keyword);
	
	SearchResult<Entity> findProduct(Long shopId, Long categoryId, String keyword);
	
	SearchResult<Entity> search(Long customerId, String keyword);
	
	SearchResult<Entity> search(Long customerId, Long categoryId, String keyword);
	
	SearchResult<Entity> search(Long customerId, Long tagId);
	
	SearchResult<Entity> search(Long customerId, String keyword, Date gmtModifyStart, Date gmtModifyEnd);
	
	SearchResult<Entity> search(Long customerId, Long tagId, Date gmtModifyStart, Date gmtModifyEnd);
	
	SearchResult<Entity> search(String keyword);
	
	SearchResult<Entity> search(Long categoryId);
	
	SearchResult<Entity> search(String keyword, Date gmtModifyStart, Date gmtModifyEnd);
	
	SearchResult<Entity> search(Long tagId, Date gmtModifyStart, Date gmtModifyEnd);
	
	List<KeyWord> keywordAutoComplete(Long shopId,Long categoryId,String key);
	
	List<ShopTagDO> tagAutoComplete(String key);
	
	List<String> keywordTop10();
	
	List<ShopTagDO> tagTop10();
	
	SearchResult<Entity> bestSellTop10();
	
	SearchResult<Entity> getRandom(int count,Long... categoryId);
}
