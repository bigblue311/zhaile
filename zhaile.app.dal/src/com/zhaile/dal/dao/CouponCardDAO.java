package com.zhaile.dal.dao;

import java.util.List;

import com.victor.framework.common.shared.Result;
import com.zhaile.dal.model.CouponCardDO;
import com.zhaile.dal.query.condition.CouponCardQueryCondition;

public interface CouponCardDAO {
	/**
	 * 插入一条记录
	 * @param couponListDO
	 * @return
	 */
	Result<Long> insert(CouponCardDO couponCardDO);
	
	/**
	 * 更新一条记录
	 * @param couponListDO
	 * @return
	 */
	Result<Boolean> update(CouponCardDO couponCardDO);
	
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
	Result<CouponCardDO> getById(Long id);
	
	/**
	 * 根据查询条件获取所有
	 * @param queryCondition
	 * @return
	 */
	Result<List<CouponCardDO>> getByCondition(CouponCardQueryCondition queryCondition);
	/**
	 * 获取分页数据
	 * @param queryCondition
	 * @return
	 */
	Result<List<CouponCardDO>> getPage(CouponCardQueryCondition queryCondition);
	
	/**
	 * 获取分页总个数
	 * @param queryCondition
	 * @return
	 */
	Result<Integer> getCount(CouponCardQueryCondition queryCondition);
}
