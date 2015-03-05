package com.zhaile.dal.dao;

import java.util.List;

import com.victor.framework.common.shared.Result;
import com.zhaile.dal.model.LogCreditDO;
import com.zhaile.dal.query.condition.LogQueryCondition;

public interface LogCreditDAO {
	Result<Long> insert(LogCreditDO logCreditDO);
	
	Result<LogCreditDO> getById(Long id);
	
	Result<List<LogCreditDO>> getByIds(Long[] id);

	Result<Integer> getCount(LogQueryCondition condition);
	 
	Result<List<LogCreditDO>> getPage(LogQueryCondition condition); 
	
	Result<List<LogCreditDO>> getByLog(Long logId); 
}
