package com.zhaile.dal.dao.impl;

import java.sql.SQLException;
import java.util.List;

import com.victor.framework.common.shared.Result;
import com.victor.framework.dal.basic.EntityDAO;
import com.zhaile.dal.dao.CouponCardDAO;
import com.zhaile.dal.model.CouponCardDO;
import com.zhaile.dal.query.condition.CouponCardQueryCondition;

public class CouponCardDAOImpl extends EntityDAO<CouponCardDO> implements CouponCardDAO{

	public CouponCardDAOImpl(){
		super(CouponCardDO.class.getSimpleName());
	}
	
	@Override
	public Result<List<CouponCardDO>> getByCondition(CouponCardQueryCondition queryCondition) {
		try {
			Object obj = getSqlMapClient().queryForList(super.getNamespace()+".getByCondition", queryCondition.getQueryMap());
			if(obj != null) {
				@SuppressWarnings("unchecked")
				List<CouponCardDO> entity = (List<CouponCardDO>)obj;
				return Result.newInstance(entity, "获取数据成功", true);
			} else {
				return Result.newInstance(null, "获取数据失败", false);
			}
		} catch (SQLException e) {
			return Result.newInstance(null, "获取数据失败", false);
		}
	}

	@Override
	public Result<List<CouponCardDO>> getPage(
			CouponCardQueryCondition queryCondition) {
		return super.getPage(queryCondition);
	}

	@Override
	public Result<Integer> getCount(CouponCardQueryCondition queryCondition) {
		return super.getCount(queryCondition);
	}

}
