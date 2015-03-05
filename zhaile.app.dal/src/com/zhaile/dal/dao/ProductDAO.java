package com.zhaile.dal.dao;

import java.util.List;

import com.victor.framework.common.shared.Result;
import com.zhaile.dal.model.ProductDO;
import com.zhaile.dal.query.condition.ProductQueryCondition;

public interface ProductDAO {
	/**
	 * 插入一条记录
	 * @param productDO
	 * @return
	 */
	Result<Long> insert(ProductDO productDO);
	
	/**
	 * 更新一条记录
	 * @param productDO
	 * @return
	 */
	Result<Boolean> update(ProductDO productDO);
	
	/**
	 * 软删除一条记录
	 * @param id
	 * @return
	 */
	Result<Boolean> softDelete(Long id);
	
	/**
	 * 更新商品状态根据店铺ID
	 * @param enable
	 * @param shopId
	 * @return
	 */
	Result<Boolean> updateStatusByShopId(String enable,Long shopId);
	
	/**
	 * 从软删除状态恢复
	 * @param id
	 * @return
	 */
	Result<Boolean> recover(Long id);
	
	/**
	 * 获取所有有效的记录
	 * @return
	 */
	Result<List<ProductDO>> getAll();
	
	/**
	 * 获取所有记录
	 * @return
	 */
	Result<List<ProductDO>> getFull();
	
	/**
	 * 根据店铺ID获取所有记录
	 * @param shopId
	 * @return
	 */
	Result<List<ProductDO>> getFullByShopId(Long shopId);
	
	/**
	 * 获取店铺的ID集合，当该店铺所有商品都失效的时候
	 * @return
	 */
	Result<List<Long>> getAllDisabled();
	
	/**
	 * 获取店铺的ID集合，当该店铺有商品有效时
	 * @return
	 */
	Result<List<Long>> getAnyEnabled();
	
	/**
	 * 获取被失效了的店铺的ID集合
	 * @return
	 */
	Result<List<Long>> getDisabledList();
	
	/**
	 * 根据ID获取记录
	 * @param id
	 * @return
	 */
	Result<ProductDO> getById(Long id);
	
	/**
	 * 根据ID批量获取记录
	 * @param id
	 * @return
	 */
	Result<List<ProductDO>> getByIds(Long[] id);
	
	/**
	 * 根据查询条件获取记录
	 * @param queryCondition
	 * @return
	 */
	Result<List<ProductDO>> getPage(ProductQueryCondition queryCondition);
	
	/**
	 * 根据查询条件获取记录条数
	 * @param queryCondition
	 * @return
	 */
	Result<Integer> getCount(ProductQueryCondition queryCondition);
	
	/**
	 * 获取最热卖的10件商品
	 * @return
	 */
	Result<List<ProductDO>> getBestSellerTop10();
	
	/**
	 * 根据分类随机获取商品
	 * @param count
	 * @param categoryId
	 * @return
	 */
	Result<List<ProductDO>> getRandom(int count,Long... categoryId);
}
