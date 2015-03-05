package com.zhaile.dal.dao;

import java.util.List;

import com.victor.framework.common.shared.Result;
import com.zhaile.dal.model.LogSearchDO;
import com.zhaile.dal.model.vo.LogSearchStatistic;
import com.zhaile.dal.query.condition.LogQueryCondition;

public interface LogSearchDAO {
	Result<Long> insert(LogSearchDO logSearchDO);
	
	Result<LogSearchDO> getById(Long id);
	
	Result<List<LogSearchDO>> getByIds(Long[] id);

	Result<List<LogSearchStatistic>> getPage(LogQueryCondition condition); 
	
	Result<List<LogSearchDO>> getByLog(Long logId); 
	
	Result<Integer> getCount(LogQueryCondition condition); 
	
	Result<List<String>> getTop(Long limit);
	
	Result<List<LogSearchStatistic>> getStatistic(Long limit);
}
