package com.zhaile.search.engine.db;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;
import com.victor.framework.common.shared.Result;
import com.victor.framework.common.tools.CollectionTools;
import com.victor.framework.common.tools.DateTools;
import com.victor.framework.search.basic.KeyWord;
import com.victor.framework.search.basic.PriceRange;
import com.victor.framework.search.basic.ResultAttribute;
import com.victor.framework.search.basic.SearchQuery;
import com.victor.framework.search.basic.SearchResult;
import com.victor.framework.search.cache.ComputableCache;
import com.victor.framework.search.enumerate.SearchResultEnum;
import com.zhaile.dal.cache.PriceRangeCache;
import com.zhaile.dal.dao.LogSearchDAO;
import com.zhaile.dal.dao.ProductDAO;
import com.zhaile.dal.dao.ShopDAO;
import com.zhaile.dal.dao.ShopTagDAO;
import com.zhaile.dal.log.DbLogger;
import com.zhaile.dal.model.ProductDO;
import com.zhaile.dal.model.ShopDO;
import com.zhaile.dal.model.ShopTagDO;
import com.zhaile.dal.query.condition.ProductQueryCondition;
import com.zhaile.search.engine.Searchable;

public class DBProductSearchable implements Searchable<ProductDO> {
	
	@Autowired
	private ProductDAO productDAO;
	
	@Autowired
	private ShopDAO shopDAO;
	
	@Autowired
	private LogSearchDAO logSearchDAO;
	
	@Autowired
	private ShopTagDAO shopTagDAO;
	
	@Autowired
	private PriceRangeCache priceRangeCache;
	
	@Autowired
	private DbLogger dblogger;
	
	private final ComputableCache<SearchQuery,SearchResult<ProductDO>> cache 
						 = new ComputableCache<SearchQuery,SearchResult<ProductDO>>(this);
	
	@Override
	public SearchResult<ProductDO> comput(SearchQuery searchQuery) {
		ProductQueryCondition sqc = new ProductQueryCondition();
		sqc.setQueryMap(searchQuery.getQueryMap());
		Result<Integer> cntResult = productDAO.getCount(sqc);
		Result<List<ProductDO>> pageResult = productDAO.getPage(sqc);
		if(cntResult.isSuccess() && pageResult.isSuccess()){
			return SearchResult.newInstance(pageResult.getDataObject(),wrapResultAttribute(pageResult.getDataObject()),cntResult.getDataObject(),
											"查询成功", true);
		} else {
			return SearchResult.newInstance(new ArrayList<ProductDO>(),null,0,
											"查询失败", false);
		}
	}

	@Override
	public boolean forceUpdate(SearchResult<ProductDO> searchResult) {
		return !DateTools.isValid(3, searchResult.getTimestamp());
	}

	@Override
	public SearchResult<ProductDO> search(Long customerId, String keyword) {
		dblogger.write(customerId, keyword, 0l);
		return search(keyword);
	}

	@Override
	public SearchResult<ProductDO> search(Long customerId, Long tagId) {
		try {
			String keyword = shopTagDAO.getById(tagId).getDataObject().getContent();
			shopTagDAO.SearchCountIncrease(tagId);
			dblogger.write(customerId, keyword, tagId);
			return search(tagId);
		} catch (Exception e) {
			return SearchResult.newInstance(new ArrayList<ProductDO>(),null,0,
					"查询失败", false);
		}
	}

	@Override
	public SearchResult<ProductDO> search(Long customerId, String keyword,
			Date gmtModifyStart, Date gmtModifyEnd) {
		dblogger.write(customerId, keyword, 0l);
		return search(keyword,gmtModifyStart,gmtModifyEnd);
	}

	@Override
	public SearchResult<ProductDO> search(Long customerId, Long tagId,
			Date gmtModifyStart, Date gmtModifyEnd) {
		try {
			String keyword = shopTagDAO.getById(tagId).getDataObject().getContent();
			shopTagDAO.SearchCountIncrease(tagId);
			dblogger.write(customerId, keyword, tagId);
			return search(tagId,gmtModifyStart,gmtModifyEnd);
		} catch (Exception e) {
			return SearchResult.newInstance(new ArrayList<ProductDO>(),null,0,
					"查询失败", false);
		}
	}

	@Override
	public List<KeyWord> keywordAutoComplete(Long shopId, Long categoryId,String key) {
		SearchQuery searchQuery = new SearchQuery(SearchResultEnum.商品);
		searchQuery.start(0).pageSize(20).shopId(shopId).categoryId(categoryId).keyword(key);
		SearchResult<ProductDO> result = this.comput(searchQuery);
		List<KeyWord> autoList = Lists.newArrayList();
		if(result.isSuccess()) {
			List<ProductDO> list = result.getDataObject();
			for(ProductDO product : list){
				ShopDO shop = shopDAO.getById(product.getShopId()).getDataObject();
				if(shop!=null) {
					KeyWord keyWord = new KeyWord(product.getName(),shop.getName(),product.getPrice().toString());
					autoList.add(keyWord);
				}
			}
		}
		return autoList;
	}

	@Override
	public List<ShopTagDO> tagAutoComplete(String key) {
		return shopTagDAO.getOnlyProdTag(key).getDataObject();
	}

	@Override
	public List<String> keywordTop10() {
		return logSearchDAO.getTop(10l).getDataObject();
	}

	@Override
	public List<ShopTagDO> tagTop10() {
		return shopTagDAO.getOnlyProdTag().getDataObject();
	}

	@Override
	public SearchResult<ProductDO> findProduct(Long shopId) {
		try {
			SearchQuery searchQuery = new SearchQuery(SearchResultEnum.商品);
			searchQuery.shopId(shopId);
			return (SearchResult<ProductDO>) cache.comput(searchQuery);
		} catch (Exception e) {
			return SearchResult.newInstance(new ArrayList<ProductDO>(),null,0,
					"查询失败", false);
		}
	}

	@Override
	public SearchResult<ProductDO> findProduct(Long shopId, String keyword) {
		try {
			SearchQuery searchQuery = new SearchQuery(SearchResultEnum.商品);
			searchQuery.shopId(shopId).keyword(keyword);
			return (SearchResult<ProductDO>) cache.comput(searchQuery);
		} catch (Exception e) {
			return SearchResult.newInstance(new ArrayList<ProductDO>(),null,0,
					"查询失败", false);
		}
	}

	@Override
	public SearchResult<ProductDO> findShop(Long categoryId,String keyword) {
		return SearchResult.newInstance(new ArrayList<ProductDO>(),null,0, "查询失败", false);
	}

	@Override
	public SearchResult<ProductDO> search(String keyword) {
		try {
			SearchQuery searchQuery = new SearchQuery(SearchResultEnum.商品);
			searchQuery.keyword(keyword);
			return (SearchResult<ProductDO>) cache.comput(searchQuery);
		} catch (Exception e) {
			return SearchResult.newInstance(new ArrayList<ProductDO>(),null,0,
					"查询失败", false);
		}
	}

	@Override
	public SearchResult<ProductDO> search(Long categoryId) {
		try {
			SearchQuery searchQuery = new SearchQuery(SearchResultEnum.商品);
			searchQuery.categoryId(categoryId);
			return (SearchResult<ProductDO>) cache.comput(searchQuery);
		} catch (Exception e) {
			return SearchResult.newInstance(new ArrayList<ProductDO>(),null,0,
					"查询失败", false);
		}
	}

	@Override
	public SearchResult<ProductDO> search(String keyword, Date gmtModifyStart, Date gmtModifyEnd) {
		try {
			SearchQuery searchQuery = new SearchQuery(SearchResultEnum.商品);
			searchQuery.keyword(keyword)
			.gmtModifyStart(gmtModifyStart).gmtModifyEnd(gmtModifyEnd);
			return (SearchResult<ProductDO>) cache.comput(searchQuery);
		} catch (Exception e) {
			return SearchResult.newInstance(new ArrayList<ProductDO>(),null,0,
					"查询失败", false);
		}
	}

	@Override
	public SearchResult<ProductDO> search(Long tagId, Date gmtModifyStart, Date gmtModifyEnd) {
		try {
			SearchQuery searchQuery = new SearchQuery(SearchResultEnum.商家);
			String keyword = shopTagDAO.getById(tagId).getDataObject().getContent();
			searchQuery.tagId(tagId).keyword(keyword)
			.gmtModifyStart(gmtModifyStart).gmtModifyEnd(gmtModifyEnd);
			return (SearchResult<ProductDO>) cache.comput(searchQuery);
		} catch (Exception e) {
			return SearchResult.newInstance(new ArrayList<ProductDO>(),null,0,
					"查询失败", false);
		}
	}

	@Override
	public SearchResult<ProductDO> bestSellTop10() {
		Result<List<ProductDO>> result = productDAO.getBestSellerTop10();
		if(result.isSuccess()){
			List<ProductDO> list = result.getDataObject();
			return SearchResult.newInstance(list,wrapResultAttribute(list), list.size(), result.getMessage(), true);
		} else {
			return SearchResult.newInstance(new ArrayList<ProductDO>(),null,0,
					"查询失败", false);
		}
	}
	
	@Override
	public SearchResult<ProductDO> getById(Long... id) {
		Result<List<ProductDO>> result = productDAO.getByIds(id);
		if(result.isSuccess()){
			List<ProductDO> list = result.getDataObject();
			return SearchResult.newInstance(list,wrapResultAttribute(list), list.size(), result.getMessage(), true);
		} else {
			return SearchResult.newInstance(new ArrayList<ProductDO>(),null,0,
					"查询失败", false);
		}
	}

	@Override
	public SearchResult<ProductDO> getRandom(int count,Long... categoryId) {
		Result<List<ProductDO>> result = productDAO.getRandom(count,categoryId);
		if(result.isSuccess()){
			List<ProductDO> list = result.getDataObject();
			return SearchResult.newInstance(list,wrapResultAttribute(list), list.size(), result.getMessage(), true);
		} else {
			return SearchResult.newInstance(new ArrayList<ProductDO>(),null,0,
					"查询失败", false);
		}
	}
	
	private ResultAttribute wrapResultAttribute(List<ProductDO> list) {
		if(list == null || CollectionTools.isEmpty(list)) {
			return null;
		}
		ResultAttribute attr = new ResultAttribute();
		for(ProductDO prod : list) {
			if(prod!=null) {
				if(prod.getShopId()!=null) {
					Long shopId = prod.getShopId();
					Map<Long,Integer> shopMap =attr.getShopId();
					if(!shopMap.containsKey(shopId)) {
						shopMap.put(shopId,1);
					} else {
						shopMap.put(shopId,shopMap.get(shopId)+1);
					}
					attr.setShopId(shopMap);
				}
				if(prod.getCategoryId()!=null) {
					Long categoryId = prod.getCategoryId();
					Map<Long,Integer> categoryMap =attr.getCategoryId();
					if(!categoryMap.containsKey(categoryId)) {
						categoryMap.put(categoryId,1);
					} else {
						categoryMap.put(categoryId,categoryMap.get(categoryId)+1);
					}
					attr.setCategoryId(categoryMap);
				}
				PriceRange priceRange = priceRangeCache.getPriceRange(prod.getPrice());
				if(priceRange!=null){
					Long priceRangeId = priceRange.getId();
					Map<Long,Integer> priceRangeMap = attr.getPriceRange();
					if(!priceRangeMap.containsKey(priceRangeId)) {
						priceRangeMap.put(priceRangeId,1);
					} else {
						priceRangeMap.put(priceRangeId,priceRangeMap.get(priceRangeId)+1);
					}
					attr.setPriceRange(priceRangeMap);
				}
			}
		}
		return attr;
	}

	@Override
	public SearchResult<ProductDO> findProduct(Long shopId, Long categoryId) {
		try {
			SearchQuery searchQuery = new SearchQuery(SearchResultEnum.商品);
			searchQuery.shopId(shopId).categoryId(categoryId);
			return (SearchResult<ProductDO>) cache.comput(searchQuery);
		} catch (Exception e) {
			return SearchResult.newInstance(new ArrayList<ProductDO>(),null,0,
					"查询失败", false);
		}
	}

	@Override
	public SearchResult<ProductDO> findProduct(Long shopId, Long categoryId,
			String keyword) {
		try {
			SearchQuery searchQuery = new SearchQuery(SearchResultEnum.商品);
			searchQuery.shopId(shopId).categoryId(categoryId).keyword(keyword);
			return (SearchResult<ProductDO>) cache.comput(searchQuery);
		} catch (Exception e) {
			return SearchResult.newInstance(new ArrayList<ProductDO>(),null,0,
					"查询失败", false);
		}
	}

	@Override
	public SearchResult<ProductDO> search(Long customerId, Long categoryId, String keyword) {
		try {
			SearchQuery searchQuery = new SearchQuery(SearchResultEnum.商品);
			searchQuery.categoryId(categoryId).keyword(keyword);
			return (SearchResult<ProductDO>) cache.comput(searchQuery);
		} catch (Exception e) {
			return SearchResult.newInstance(new ArrayList<ProductDO>(),null,0,
					"查询失败", false);
		}
	}
}
