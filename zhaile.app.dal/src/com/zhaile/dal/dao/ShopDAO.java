package com.zhaile.dal.dao;

import java.util.List;

import com.victor.framework.common.shared.Result;
import com.zhaile.dal.model.ShopDO;
import com.zhaile.dal.query.condition.ShopQueryCondition;

public interface ShopDAO {
	/**
	 * 增加一条记录
	 * @param shopDO
	 * @return
	 */
	Result<Long> insert(ShopDO shopDO);
	
	/**
	 * 更新一条记录
	 * @param shopDO
	 * @return
	 */
	Result<Boolean> update(ShopDO shopDO);
	
	/**
	 * 解除一家店铺的推送绑定
	 * @param shopDO
	 * @return
	 */
	Result<Boolean> clearShopBinding(ShopDO shopDO);
	/**
	 * 软删除一条记录
	 * @param id
	 * @return
	 */
	Result<Boolean> softDelete(Long id);
	
	/**
	 * 恢复一条软删除的记录
	 * @param id
	 * @return
	 */
	Result<Boolean> recover(Long id);
	
	/**
	 * 当店铺下所有商品为空时，将店铺置为禁用
	 * @return
	 */
	Result<Boolean> autoDisable(Long[] id);
	
	/**
	 * 当店铺下有商品时，将店铺置为有效
	 * @return
	 */
	Result<Boolean> autoEnable(Long[] id);
	
	/**
	 * 根据ID获取店铺
	 * @param id
	 * @return
	 */
	Result<ShopDO> getById(Long id);
	
	/**
	 * 获取所有有效的店铺
	 * @return
	 */
	Result<List<ShopDO>> getAll();
	
	/**
	 * 获取所有有效的店铺个数,按距离排序
	 * @return
	 */
	Integer getAllByDistanceCount();
	/**
	 * 获取所有有效的店铺,按距离排序
	 * @return
	 */
	Result<List<ShopDO>> getAllByDistance(Double lng,Double lat,int start, int pageSize);
	
	/**
	 * 获取所有店铺
	 * @return
	 */
	Result<List<ShopDO>> getFull();
	
	/**
	 * 根据ID批量获取
	 * @param id
	 * @return
	 */
	Result<List<ShopDO>> getByIds(Long[] id);
	
	/**
	 * 根据查询条件获取
	 * @param queryCondition
	 * @return
	 */
	Result<List<ShopDO>> getPage(ShopQueryCondition queryCondition);

	/**
	 * 根据条件获取符合的记录个数
	 * @param queryCondition
	 * @return
	 */
	Result<Integer> getCount(ShopQueryCondition queryCondition);
	
	/**
	 * 获取被失效了的店铺的ID集合
	 * @return
	 */
	Result<List<Long>> getDisabledList();
	
	/**
	 * 获取最热卖的10家店铺
	 * @return
	 */
	Result<List<ShopDO>> getBestSellerTop10();
	
	/**
	 * 获取随机的店铺
	 * @param count
	 * @param categoryId
	 * @return
	 */
	Result<List<ShopDO>> getRandom(int count,Long... categoryId);
}
