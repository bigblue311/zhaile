package com.zhaile.dal.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.victor.framework.common.shared.Result;
import com.victor.framework.dal.basic.EntityDAO;
import com.zhaile.dal.dao.OrderDAO;
import com.zhaile.dal.enumerate.LogOpEnum;
import com.zhaile.dal.log.DbLogger;
import com.zhaile.dal.model.OrderDO;
import com.zhaile.dal.query.condition.OrderQueryCondition;

public class OrderDAOImpl extends EntityDAO<OrderDO> implements OrderDAO {

	@Autowired
	private DbLogger dblogger;
	
	public OrderDAOImpl() {
		super(OrderDO.class.getSimpleName());
	}

	@Override
	public Result<Long> insert(OrderDO orderDO){
		Result<Long> result = super.insert(orderDO);
		if(result.isSuccess()){
			dblogger.write(LogOpEnum.交易, orderDO.getCustomerId());
		}
		return result;
	}
	
	@Override
	public Result<List<OrderDO>> getPage(OrderQueryCondition queryCondtion) {
		return super.getPage(queryCondtion);
	}

	@Override
	public Result<Integer> getCount(OrderQueryCondition queryCondtion) {
		return super.getCount(queryCondtion);
	}
}
