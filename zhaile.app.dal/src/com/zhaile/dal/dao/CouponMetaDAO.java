package com.zhaile.dal.dao;

import java.util.List;

import com.victor.framework.common.shared.Result;
import com.zhaile.dal.model.CouponMetaDO;
import com.zhaile.dal.query.condition.CouponMetaQueryCondition;

public interface CouponMetaDAO {
	
	/**
	 * 插入一条记录
	 * @param couponListDO
	 * @return
	 */
	Result<Long> insert(CouponMetaDO couponListDO);
	
	/**
	 * 更新一条记录
	 * @param couponListDO
	 * @return
	 */
	Result<Boolean> update(CouponMetaDO couponListDO);
	
	/**
	 * 删除一条记录
	 * @param id
	 * @return
	 */
	Result<Boolean> delete(Long id);

	/**
	 * 根据ID获取
	 * @param id
	 * @return
	 */
	Result<CouponMetaDO> getById(Long id);
	
	/**
	 * 获取分页数据
	 * @param queryCondition
	 * @return
	 */
	Result<List<CouponMetaDO>> getPage(CouponMetaQueryCondition queryCondition);
	
	/**
	 * 获取分页总个数
	 * @param queryCondition
	 * @return
	 */
	Result<Integer> getCount(CouponMetaQueryCondition queryCondition);
}
