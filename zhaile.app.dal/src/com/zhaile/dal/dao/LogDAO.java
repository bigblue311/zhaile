package com.zhaile.dal.dao;

import java.util.List;

import com.victor.framework.common.shared.Result;
import com.zhaile.dal.model.LogDO;
import com.zhaile.dal.query.condition.LogQueryCondition;

public interface LogDAO {
	Result<Long> insert(LogDO logDO);
	
	Result<LogDO> getById(Long id);
	
	Result<List<LogDO>> getByIds(Long[] id);
	
	Result<Integer> getCount(LogQueryCondition condition);

	Result<List<LogDO>> getPage(LogQueryCondition condition); 
	
	Result<List<LogDO>> getLog(LogQueryCondition condition); 
}
