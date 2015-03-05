package com.zhaile.search.engine.db;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;
import com.victor.framework.common.shared.Result;
import com.victor.framework.common.tools.DateTools;
import com.victor.framework.common.tools.StringTools;
import com.victor.framework.search.basic.KeyWord;
import com.victor.framework.search.basic.SearchQuery;
import com.victor.framework.search.basic.SearchResult;
import com.victor.framework.search.cache.ComputableCache;
import com.victor.framework.search.enumerate.SearchResultEnum;
import com.zhaile.dal.dao.LogSearchDAO;
import com.zhaile.dal.dao.ShopDAO;
import com.zhaile.dal.dao.ShopTagDAO;
import com.zhaile.dal.log.DbLogger;
import com.zhaile.dal.model.ShopDO;
import com.zhaile.dal.model.ShopTagDO;
import com.zhaile.dal.query.condition.ShopQueryCondition;
import com.zhaile.search.engine.Searchable;

public class DBShopSearchable implements Searchable<ShopDO> {
	
	@Autowired
	private ShopDAO shopDAO;
	
	@Autowired
	private LogSearchDAO logSearchDAO;
	
	@Autowired
	private ShopTagDAO shopTagDAO;
	
	@Autowired
	private DbLogger dblogger;
	
	private final ComputableCache<SearchQuery,SearchResult<ShopDO>> cache 
						 = new ComputableCache<SearchQuery,SearchResult<ShopDO>>(this);
	
	@Override
	public SearchResult<ShopDO> comput(SearchQuery searchQuery) {
		ShopQueryCondition sqc = new ShopQueryCondition();
		sqc.setQueryMap(searchQuery.getQueryMap());
		Result<Integer> cntResult = shopDAO.getCount(sqc);
		Result<List<ShopDO>> pageResult = shopDAO.getPage(sqc);
		if(cntResult.isSuccess() && pageResult.isSuccess()){
			return SearchResult.newInstance(pageResult.getDataObject(),null,cntResult.getDataObject(),
											"查询成功", true);
		} else {
			return SearchResult.newInstance(new ArrayList<ShopDO>(),null,0,
											"查询失败", false);
		}
	}

	@Override
	public boolean forceUpdate(SearchResult<ShopDO> searchResult) {
		return !DateTools.isValid(3, searchResult.getTimestamp());
	}

	@Override
	public SearchResult<ShopDO> search(Long customerId, String keyword) {
		dblogger.write(customerId, keyword, 0l);
		return search(keyword);
	}

	@Override
	public SearchResult<ShopDO> search(Long customerId, Long tagId) {
		try {
			String keyword = shopTagDAO.getById(tagId).getDataObject().getContent();
			shopTagDAO.SearchCountIncrease(tagId);
			dblogger.write(customerId, keyword, tagId);
			return search(tagId);
		} catch (Exception e) {
			return SearchResult.newInstance(new ArrayList<ShopDO>(),null,0,
					"查询失败", false);
		}
	}

	@Override
	public SearchResult<ShopDO> search(Long customerId, String keyword,
			Date gmtModifyStart, Date gmtModifyEnd) {
		dblogger.write(customerId, keyword, 0l);
		return search(keyword,gmtModifyStart,gmtModifyEnd);
	}

	@Override
	public SearchResult<ShopDO> search(Long customerId, Long tagId,
			Date gmtModifyStart, Date gmtModifyEnd) {
		try {
			String keyword = shopTagDAO.getById(tagId).getDataObject().getContent();
			shopTagDAO.SearchCountIncrease(tagId);
			dblogger.write(customerId, keyword, tagId);
			return search(tagId,gmtModifyStart,gmtModifyEnd);
		} catch (Exception e) {
			return SearchResult.newInstance(new ArrayList<ShopDO>(),null,0,
					"查询失败", false);
		}
	}

	@Override
	public List<KeyWord> keywordAutoComplete(Long shopId, Long categoryId,String key) {
		SearchQuery searchQuery = new SearchQuery(SearchResultEnum.商家);
		searchQuery.start(0).pageSize(10).shopId(shopId).categoryId(categoryId).keyword(key);
		SearchResult<ShopDO> result = this.comput(searchQuery);
		List<KeyWord> autoList = Lists.newArrayList();
		if(result.isSuccess()) {
			List<ShopDO> list = result.getDataObject();
			for(ShopDO shop : list){
				KeyWord keyWord = new KeyWord(shop.getName(),shop.getName());
				autoList.add(keyWord);
			}
		}
		return autoList;
	}

	@Override
	public List<ShopTagDO> tagAutoComplete(String key) {
		return shopTagDAO.getOnlyShopTag(key).getDataObject();
	}

	@Override
	public List<String> keywordTop10() {
		return logSearchDAO.getTop(10l).getDataObject();
	}

	@Override
	public List<ShopTagDO> tagTop10() {
		return shopTagDAO.getOnlyShopTag().getDataObject();
	}

	@Override
	public SearchResult<ShopDO> findProduct(Long shopOwnerId) {
		return SearchResult.newInstance(new ArrayList<ShopDO>(),null,0,"查询失败", false);
	}

	@Override
	public SearchResult<ShopDO> findProduct(Long shopOwnerId, String keyword) {
		return SearchResult.newInstance(new ArrayList<ShopDO>(),null,0,"查询失败", false);
	}

	@Override
	public SearchResult<ShopDO> findShop(Long categoryId,String keyword) {
		try {
			if(categoryId == null && StringTools.isEmpty(keyword)){
				return SearchResult.newInstance(new ArrayList<ShopDO>(),null,0,
						"查询失败", false);
			}
			SearchQuery searchQuery = new SearchQuery(SearchResultEnum.商家);
			searchQuery.keyword(keyword).categoryId(categoryId);
			return (SearchResult<ShopDO>) cache.comput(searchQuery);
		} catch (Exception e) {
			return SearchResult.newInstance(new ArrayList<ShopDO>(),null,0,
					"查询失败", false);
		}
	}

	@Override
	public SearchResult<ShopDO> search(String keyword) {
		try {
			SearchQuery searchQuery = new SearchQuery(SearchResultEnum.商家);
			searchQuery.keyword(keyword);
			return (SearchResult<ShopDO>) cache.comput(searchQuery);
		} catch (Exception e) {
			return SearchResult.newInstance(new ArrayList<ShopDO>(),null,0,
					"查询失败", false);
		}
	}

	@Override
	public SearchResult<ShopDO> search(Long categoryId) {
		try {
			SearchQuery searchQuery = new SearchQuery(SearchResultEnum.商家);
			searchQuery.categoryId(categoryId);
			return (SearchResult<ShopDO>) cache.comput(searchQuery);
		} catch (Exception e) {
			return SearchResult.newInstance(new ArrayList<ShopDO>(),null,0,
					"查询失败", false);
		}
	}

	@Override
	public SearchResult<ShopDO> search(String keyword, Date gmtModifyStart,
			Date gmtModifyEnd) {
		try {
			SearchQuery searchQuery = new SearchQuery(SearchResultEnum.商家);
			searchQuery.keyword(keyword)
			.gmtModifyStart(gmtModifyStart).gmtModifyEnd(gmtModifyEnd);
			return (SearchResult<ShopDO>) cache.comput(searchQuery);
		} catch (Exception e) {
			return SearchResult.newInstance(new ArrayList<ShopDO>(),null,0,
					"查询失败", false);
		}
	}

	@Override
	public SearchResult<ShopDO> search(Long tagId, Date gmtModifyStart,
			Date gmtModifyEnd) {
		try {
			SearchQuery searchQuery = new SearchQuery(SearchResultEnum.商家);
			String keyword = shopTagDAO.getById(tagId).getDataObject().getContent();
			searchQuery.tagId(tagId).keyword(keyword)
			.gmtModifyStart(gmtModifyStart).gmtModifyEnd(gmtModifyEnd);
			return (SearchResult<ShopDO>) cache.comput(searchQuery);
		} catch (Exception e) {
			return SearchResult.newInstance(new ArrayList<ShopDO>(),null,0,
					"查询失败", false);
		}
	}

	@Override
	public SearchResult<ShopDO> bestSellTop10() {
		Result<List<ShopDO>> result = shopDAO.getBestSellerTop10();
		if(result.isSuccess()){
			List<ShopDO> list = result.getDataObject();
			return SearchResult.newInstance(list,null, list.size(), result.getMessage(), true);
		} else {
			return SearchResult.newInstance(new ArrayList<ShopDO>(),null,0,
					"查询失败", false);
		}
	}
	
	@Override
	public SearchResult<ShopDO> getById(Long... id) {
		Result<List<ShopDO>> result = shopDAO.getByIds(id);
		if(result.isSuccess()){
			List<ShopDO> list = result.getDataObject();
			return SearchResult.newInstance(list,null, list.size(), result.getMessage(), true);
		} else {
			return SearchResult.newInstance(new ArrayList<ShopDO>(),null,0,
					"查询失败", false);
		}
	}

	@Override
	public SearchResult<ShopDO> getRandom(int count,Long... categoryId) {
		Result<List<ShopDO>> result = shopDAO.getRandom(count,categoryId);
		if(result.isSuccess()){
			List<ShopDO> list = result.getDataObject();
			return SearchResult.newInstance(list,null , list.size(), result.getMessage(), true);
		} else {
			return SearchResult.newInstance(new ArrayList<ShopDO>(),null,0,
					"查询失败", false);
		}
	}

	@Override
	public SearchResult<ShopDO> findProduct(Long shopId, Long categoryId) {
		return SearchResult.newInstance(new ArrayList<ShopDO>(),null,0, "查询失败", false);
	}

	@Override
	public SearchResult<ShopDO> findProduct(Long shopId, Long categoryId,
			String keyword) {
		return SearchResult.newInstance(new ArrayList<ShopDO>(),null,0, "查询失败", false);
	}

	@Override
	public SearchResult<ShopDO> search(Long customerId, Long categoryId, String keyword) {
		try {
			SearchQuery searchQuery = new SearchQuery(SearchResultEnum.商家);
			searchQuery.keyword(keyword).categoryId(categoryId);
			return (SearchResult<ShopDO>) cache.comput(searchQuery);
		} catch (Exception e) {
			return SearchResult.newInstance(new ArrayList<ShopDO>(),null,0,
					"查询失败", false);
		}
	}
}
