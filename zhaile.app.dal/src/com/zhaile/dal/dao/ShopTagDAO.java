package com.zhaile.dal.dao;

import java.util.List;

import com.victor.framework.common.shared.Result;
import com.zhaile.dal.model.ShopTagDO;

public interface ShopTagDAO {
	Result<Long> insert(ShopTagDO shopTagDO);
	
	Result<Boolean> updateContent(Long id,String content);
	
	Result<Boolean> SearchCountIncrease(Long id);
	
	Result<Boolean> delete(Long id);
	
	Result<ShopTagDO> getById(Long id);
	
	Result<List<ShopTagDO>> getOnlyByProdId(Long prodId);
	
	Result<List<ShopTagDO>> getOnlyByShopId(Long shopId);
	
	Result<List<ShopTagDO>> getAllByShopId(Long shopId);
	
	Result<List<ShopTagDO>> getAllByCategoryId(Long categoryId);
	
	Result<List<ShopTagDO>> getOnlyProdTag(String key);
	
	Result<List<ShopTagDO>> getOnlyShopTag(String key);
	
	Result<List<ShopTagDO>> getOnlyProdTag();
	
	Result<List<ShopTagDO>> getOnlyShopTag();
}
