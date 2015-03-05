package com.zhaile.dal.dao;

import java.util.List;

import com.victor.framework.common.shared.Result;
import com.zhaile.dal.model.CustomerCommentDO;
import com.zhaile.dal.query.condition.CustomerCommentQueryCondition;

public interface CustomerCommentDAO {
	Result<Long> insert(CustomerCommentDO customerCommentDO);
	
	Result<Boolean> update(CustomerCommentDO customerCommentDO);
	
	Result<Boolean> delete(Long id);
	
	Integer deleteInvalid();
	
	Result<CustomerCommentDO> getById(Long id);
	
	Result<List<CustomerCommentDO>> getByIds(Long[] id);
	
	Result<Integer> getCount(CustomerCommentQueryCondition queryCondition);
	
	Result<List<CustomerCommentDO>> getPage(CustomerCommentQueryCondition queryCondition);
}
