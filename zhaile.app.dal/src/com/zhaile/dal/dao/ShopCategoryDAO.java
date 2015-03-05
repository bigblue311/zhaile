package com.zhaile.dal.dao;

import java.util.List;

import com.victor.framework.common.shared.Result;
import com.zhaile.dal.model.ShopCategoryDO;

public interface ShopCategoryDAO {
	
	/**
	 * 插入一条记录
	 * @param shopCategoryDO
	 * @return
	 */
	Result<Long> insert(ShopCategoryDO shopCategoryDO);
	
	/**
	 * 获取分页总数据
	 * @param shopCateId
	 * @return
	 */
	Integer getByCateIdCount(Long shopCateId);
	/**
	 * 根据店铺分类ID，获取店铺ID的集合
	 * @param shopCateId
	 * @return
	 */
	List<Long> getShopIdByCateId(Long shopCateId,Double lng,Double lat,int start,int pageSize);
	
	/**
	 * 根据店铺ID删除
	 */
	void deleteByShopId(Long shopId);
	
	/**
	 * 根据店铺ID获取
	 * @param shopId
	 * @return
	 */
	List<ShopCategoryDO> getByShopId(Long shopId);
}
