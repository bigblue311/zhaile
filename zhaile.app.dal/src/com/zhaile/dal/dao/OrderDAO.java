package com.zhaile.dal.dao;

import java.util.List;

import com.victor.framework.common.shared.Result;
import com.zhaile.dal.model.OrderDO;
import com.zhaile.dal.query.condition.OrderQueryCondition;

public interface OrderDAO {
	Result<Long> insert(OrderDO orderDO);
	
	Result<OrderDO> getById(Long id);
	
	Result<List<OrderDO>> getByIds(Long[] id);
	
	Result<List<OrderDO>> getPage(OrderQueryCondition queryCondtion);
	
	Result<Integer> getCount(OrderQueryCondition queryCondtion);
}
