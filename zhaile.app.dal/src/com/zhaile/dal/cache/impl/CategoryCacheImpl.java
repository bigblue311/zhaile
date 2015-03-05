package com.zhaile.dal.cache.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.victor.framework.common.shared.Result;
import com.victor.framework.common.tools.CollectionTools;
import com.victor.framework.common.tools.LogTools;
import com.victor.framework.common.tools.PinYinTools;
import com.victor.framework.common.tools.StringTools;
import com.victor.framework.dal.cache.StaticCache;
import com.victor.framework.spymemcached.CacheService;
import com.zhaile.dal.cache.CategoryCache;
import com.zhaile.dal.dao.ProductDAO;
import com.zhaile.dal.dao.ShopDAO;
import com.zhaile.dal.model.CategoryDO;
import com.zhaile.dal.model.ProductDO;
import com.zhaile.dal.model.ShopDO;
import com.zhaile.dal.query.condition.ShopQueryCondition;

public class CategoryCacheImpl extends StaticCache<CategoryDO> implements CategoryCache {

	private static final Map<Long, List<ShopDO>> shopMap = Maps.newConcurrentMap();
	private static final Map<Long, ShopDO> shopKvMap = Maps.newConcurrentMap();
	private static final Map<String, List<ShopDO>> shopAvMap = Maps.newConcurrentMap();
	
	private static final Map<Long, List<CategoryDO>> categoryL1 = Maps.newConcurrentMap();
	private static final Map<Long, List<CategoryDO>> categoryL2 = Maps.newConcurrentMap();
	
	private static final String[] alphabets = new String[]{"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
	
	@Autowired
	private ShopDAO shopDAO;
	
	@Autowired
	private ProductDAO productDAO;
	
	@Autowired
	private CacheService memcachedService;
	
	private static LogTools log = new LogTools(CategoryCache.class);
	
	public CategoryCacheImpl() {
		super(CategoryDO.class.getSimpleName());
	}

	@Override
	public void reloadCache() {
		loanLevels();
		reloadShop();
		Result<List<CategoryDO>> result = super.queryForList("getAll");
		if(result.isSuccess() && CollectionTools.isNotEmpty(result.getDataObject())){
			super.clearCache();
			for(CategoryDO category: result.getDataObject()){
				shopMap.put(category.getId(), getShopByCategoryId(category.getId()));
				super.updateCache(category.getId(), category);
			}
			for(String alphabet : alphabets){
				shopAvMap.put(alphabet, getShopByAlphabet(alphabet));
			}
		} else {
			log.error("加载缓存数据失败");
		}
	}
	
	private void loanLevels(){
		loadL1();
		loadL2();
	}
	
	private void loadL1(){
		List<CategoryDO> L1 = getByParentId(0l);
		for(CategoryDO category : L1) {
			categoryL1.put(category.getId(), getByParentId(category.getId()));
		}
	}
	
	private void loadL2(){
		for(List<CategoryDO> L1 : categoryL1.values()){
			for(CategoryDO L2 : L1){
				categoryL2.put(L2.getId(), getByParentId(L2.getId()));
			}
		}
	}
	
	private List<CategoryDO> getByParentId(Long parentId) {
		Map<String,Object> param = Maps.newHashMap();
		param.put("parentId", parentId);
		Result<List<CategoryDO>> result = super.queryForList("getByParentId", param);
		if(result.isSuccess() && CollectionTools.isNotEmpty(result.getDataObject())){
			return result.getDataObject();
		}
		return new ArrayList<CategoryDO>();
	}
	

	@Override
	public List<CategoryDO> getByShopId(Long shopId) {
		Map<String,Object> param = Maps.newHashMap();
		param.put("shopId", shopId);
		Result<List<CategoryDO>> result = super.queryForList("getByShopId",param);
		if(result.isSuccess() && CollectionTools.isNotEmpty(result.getDataObject())){
			return result.getDataObject();
		}
		return new ArrayList<CategoryDO>();
	}

	@Override
	public List<CategoryDO> getByProdId(Long productId) {
		Map<String,Object> param = Maps.newHashMap();
		param.put("productId", productId);
		Result<List<CategoryDO>> result = super.queryForList("getByProdId",param);
		if(result.isSuccess() && CollectionTools.isNotEmpty(result.getDataObject())){
			return result.getDataObject();
		}
		return new ArrayList<CategoryDO>();
	}
	
	private List<ShopDO> getShopByCategoryId(Long categoryId){
		String key = this.getClass().getSimpleName()+".getShopByCategoryId."+categoryId;
		List<ShopDO> list = memcachedService.getList(key, ShopDO.class);
		if(list == null){
			ShopQueryCondition queryCondition = new ShopQueryCondition();
			queryCondition.categoryId(categoryId);
			Result<List<ShopDO>> result = shopDAO.getPage(queryCondition);
			if(result!=null && result.isSuccess()){
				list = result.getDataObject();
				memcachedService.setObject(key, list, 3*60*60);
			} else {
				return Lists.newArrayList();
			}
		}
		return list;
		
		
	}
	
	private List<ShopDO> getShopByAlphabet(String alphabet){
		String key = this.getClass().getSimpleName()+".getShopByAlphabet."+alphabet;
		List<ShopDO> list = memcachedService.getList(key, ShopDO.class);
		if(list == null){
			ShopQueryCondition queryCondition = new ShopQueryCondition();
			queryCondition.alphabet(alphabet);
			Result<List<ShopDO>> result = shopDAO.getPage(queryCondition);
			if(result!=null && result.isSuccess()){
				list = result.getDataObject();
				memcachedService.setObject(key, list, 3*60*60);
			} else {
				return Lists.newArrayList();
			}
		}
		return list;
	}
	
	private void reloadShop(){
		Result<List<ShopDO>> result = shopDAO.getAll();
		if(result.isSuccess() && CollectionTools.isNotEmpty(result.getDataObject())){
			shopKvMap.clear();
			for(ShopDO shop : result.getDataObject()){
				shopKvMap.put(shop.getId(), shop);
			}
		} else {
			log.error("加载缓存数据失败");
		}
	}

	@Override
	public void reload() {
		reloadCache();
	}

	@Override
	public CategoryDO getCate(String id) {
		if(StringTools.isEmpty(id)) return null;
		Long idLong = Long.parseLong(id+"");
		return (CategoryDO)super.getCache(idLong);
	}

	@Override
	public List<ShopDO> getShops(String id) {
		Long idLong = Long.parseLong(id+"");
		return shopMap.get(idLong);
	}
	
	@Override
	public Collection<Object> getAll() {
		return super.cacheValues();
	}
	
	@Override
	public Collection<CategoryDO> getL1() {
		Collection<CategoryDO> result = Lists.newArrayList();
		for(List<CategoryDO> l1List : categoryL1.values()) {
			if(l1List!=null) {
				result.addAll(l1List);
			}
		}
		return result;
	}

	@Override
	public Collection<CategoryDO> getL2() {
		Collection<CategoryDO> result = Lists.newArrayList();
		for(List<CategoryDO> l2List : categoryL2.values()) {
			if(l2List!=null) {
				result.addAll(l2List);
			}
		}
		return result;
	}

	@Override
	public ShopDO getShop(String id) {
		Long idLong = Long.parseLong(id+"");
		return shopKvMap.get(idLong);
	}

	@Override
	public List<ShopDO> getAllShops() {
		String key = this.getClass().getSimpleName()+".getAllShops";
		List<ShopDO> list = memcachedService.getList(key, ShopDO.class);
		if(list == null){
			Result<List<ShopDO>> result = shopDAO.getAll();
			if(result!=null && result.isSuccess()){
				list = result.getDataObject();
				memcachedService.setObject(key, list, 3*60*60);
			} else {
				return Lists.newArrayList();
			}
			
		}
		return list;
	}

	@Override
	public List<ShopDO> getAllShops(String... alphabets) {
		List<ShopDO> result = Lists.newArrayList();
		for(String alphabet : alphabets){
			result.addAll(shopAvMap.get(alphabet));
		}
		return result;
	}

	@Override
	public void reindex() {
		Result<List<CategoryDO>> cates = super.queryForList("getAll");
		Result<List<ShopDO>> shops = shopDAO.getAll();
		Result<List<ProductDO>> prods = productDAO.getAll();
		if(cates.isSuccess() && CollectionTools.isNotEmpty(cates.getDataObject())){
			List<CategoryDO> list = cates.getDataObject();
			log.info("开始准备【分类】拼音,共"+list.size()+"个");
			for(CategoryDO cate : list) {
				String pinyin = PinYinTools.getPinYin(cate.getName());
				cate.setPinyin(pinyin);
				super.updateDB(cate);
			}
			log.info("【分类】拼音更新完成");
		}
		if(shops.isSuccess() && CollectionTools.isNotEmpty(shops.getDataObject())){
			List<ShopDO> list = shops.getDataObject();
			log.info("开始准备【店铺】拼音,共"+list.size()+"个");
			for(ShopDO shop : list) {
				String pinyin = PinYinTools.getPinYin(shop.getName());
				shop.setPinyin(pinyin);
				shopDAO.update(shop);
			}
			log.info("【店铺】拼音更新完成");
		}
		if(prods.isSuccess() && CollectionTools.isNotEmpty(prods.getDataObject())){
			List<ProductDO> list = prods.getDataObject();
			log.info("开始准备【商品】拼音,共"+list.size()+"个");
			for(ProductDO prod : list) {
				String pinyin = PinYinTools.getPinYin(prod.getName());
				prod.setPinyin(pinyin);
				productDAO.update(prod);
			}
			log.info("【商品】拼音更新完成");
		}
	}

	@Override
	public ProductDO getProd(Long id) {
		return productDAO.getById(id).getDataObject();
	}
}
