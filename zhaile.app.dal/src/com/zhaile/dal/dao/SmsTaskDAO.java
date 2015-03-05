package com.zhaile.dal.dao;

import java.util.List;

import com.victor.framework.common.shared.Result;
import com.zhaile.dal.model.SmsTaskDO;
import com.zhaile.dal.query.condition.SmsTaskQueryCondition;

public interface SmsTaskDAO {
	Result<Long> insert(SmsTaskDO smsTaskDO);
	
	Result<Boolean> update(SmsTaskDO smsTaskDO);
	
	Result<Boolean> retry(Long id);
	
	void expireSms();
	
	Result<SmsTaskDO> getById(Long id);
	
	Result<List<SmsTaskDO>> getPage(SmsTaskQueryCondition queryCondition);
}
