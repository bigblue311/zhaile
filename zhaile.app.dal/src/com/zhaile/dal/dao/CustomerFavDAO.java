package com.zhaile.dal.dao;

import java.util.List;

import com.victor.framework.common.shared.Result;
import com.zhaile.dal.model.CustomerFavDO;
import com.zhaile.dal.query.condition.CustomerFavQueryCondition;

public interface CustomerFavDAO {
	/**
	 * 插入一条记录
	 * @param customerFavDO
	 * @return
	 */
	Result<Long> insert(CustomerFavDO customerFavDO);
	
	/**
	 * 更新一条记录
	 * @param customerFavDO
	 * @return
	 */
	Result<Boolean> update(CustomerFavDO customerFavDO);
	
	/**
	 * 删除一条记录
	 * @param id
	 * @return
	 */
	Result<Boolean> delete(Long id);
	
	/**
	 * 回收那些已经下架的商店
	 */
	Result<Boolean> recycle(Long[] shopId);
	
	/**
	 * 根据ID获取记录
	 * @param id
	 * @return
	 */
	Result<CustomerFavDO> getById(Long id);
	
	/**
	 * 根据查询条件获取
	 * @param queryCondition
	 * @return
	 */
	Result<List<CustomerFavDO>> getPage(CustomerFavQueryCondition queryCondition);
}
