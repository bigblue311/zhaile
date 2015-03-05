package com.zhaile.dal.dao.impl;

import java.util.List;

import com.victor.framework.common.shared.Result;
import com.victor.framework.dal.basic.EntityDAO;
import com.zhaile.dal.dao.LogCreditDAO;
import com.zhaile.dal.model.LogCreditDO;
import com.zhaile.dal.query.condition.LogQueryCondition;

public class LogCreditDAOImpl extends EntityDAO<LogCreditDO> implements LogCreditDAO {

	public LogCreditDAOImpl() {
		super(LogCreditDO.class.getSimpleName());
	}

	@Override
	public Result<List<LogCreditDO>> getPage(LogQueryCondition condition) {
		return super.getPage(condition);
	}

	@Override
	public Result<List<LogCreditDO>> getByLog(Long logId) {
		return super.queryForList("getByLog", "logId", logId);
	}

	@Override
	public Result<Integer> getCount(LogQueryCondition condition) {
		return super.getCount(condition);
	}

}
