package com.zhaile.dal.dao;

import java.util.List;

import com.victor.framework.common.shared.Result;
import com.zhaile.dal.model.LogDeliveryDO;
import com.zhaile.dal.query.condition.LogQueryCondition;

public interface LogDeliveryDAO {
	Result<Long> insert(LogDeliveryDO logDeliveryDO);
	
	Result<LogDeliveryDO> getById(Long id);
	
	Result<List<LogDeliveryDO>> getByIds(Long[] id);
	
	Result<Integer> getCount(LogQueryCondition condition);

	Result<List<LogDeliveryDO>> getPage(LogQueryCondition condition); 
	
	Result<List<LogDeliveryDO>> getByLog(Long logId); 
}
