package com.zhaile.dal.dao.impl;

import java.util.List;

import com.victor.framework.common.shared.Result;
import com.victor.framework.dal.basic.EntityDAO;
import com.zhaile.dal.dao.CouponMetaDAO;
import com.zhaile.dal.model.CouponMetaDO;
import com.zhaile.dal.query.condition.CouponMetaQueryCondition;

public class CouponMetaDAOImpl extends EntityDAO<CouponMetaDO> implements CouponMetaDAO {

	public CouponMetaDAOImpl() {
		super(CouponMetaDO.class.getSimpleName());
	}

	@Override
	public Result<List<CouponMetaDO>> getPage(CouponMetaQueryCondition queryCondition) {
		return super.getPage(queryCondition);
	}

	@Override
	public Result<Integer> getCount(CouponMetaQueryCondition queryCondition) {
		return super.getCount(queryCondition);
	}

}
