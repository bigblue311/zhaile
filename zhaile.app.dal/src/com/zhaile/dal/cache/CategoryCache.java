package com.zhaile.dal.cache;

import com.zhaile.dal.model.CategoryDO;
import com.zhaile.dal.model.ProductDO;
import com.zhaile.dal.model.ShopDO;

import java.util.Collection;
import java.util.List;

public interface CategoryCache {
	Collection<Object> getAll();
	
	Collection<CategoryDO> getL1();
	
	Collection<CategoryDO> getL2();
	
	void reload();
	
	void reindex();
	
	CategoryDO getCate(String id);
	
	List<CategoryDO> getByShopId(Long shopId);
	
	List<CategoryDO> getByProdId(Long productId);
	
	List<ShopDO> getShops(String id);
	
	ShopDO getShop(String id);
	
	ProductDO getProd(Long id);
	
	List<ShopDO> getAllShops();
	
	List<ShopDO> getAllShops(String... alphabets);
	
	void updateDB(CategoryDO categoryDO);
}
