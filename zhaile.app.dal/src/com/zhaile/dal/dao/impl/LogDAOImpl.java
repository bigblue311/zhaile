package com.zhaile.dal.dao.impl;

import java.util.List;

import com.victor.framework.common.shared.Result;
import com.victor.framework.dal.basic.EntityDAO;
import com.zhaile.dal.dao.LogDAO;
import com.zhaile.dal.model.LogDO;
import com.zhaile.dal.query.condition.LogQueryCondition;

public class LogDAOImpl extends EntityDAO<LogDO> implements LogDAO {

	public LogDAOImpl() {
		super(LogDO.class.getSimpleName());
	}

	@Override
	public Result<List<LogDO>> getPage(LogQueryCondition condition) {
		return super.getPage(condition);
	}

	@Override
	public Result<List<LogDO>> getLog(LogQueryCondition condition) {
		return super.queryForList("getLog", condition.getQueryMap());
	}

	@Override
	public Result<Integer> getCount(LogQueryCondition condition) {
		return super.getCount(condition);
	}

}
