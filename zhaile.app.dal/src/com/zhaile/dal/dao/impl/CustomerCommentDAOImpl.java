package com.zhaile.dal.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.victor.framework.common.shared.Result;
import com.victor.framework.dal.basic.EntityDAO;
import com.zhaile.dal.dao.CustomerCommentDAO;
import com.zhaile.dal.enumerate.LogOpEnum;
import com.zhaile.dal.log.DbLogger;
import com.zhaile.dal.model.CustomerCommentDO;
import com.zhaile.dal.query.condition.CustomerCommentQueryCondition;

public class CustomerCommentDAOImpl extends EntityDAO<CustomerCommentDO> implements CustomerCommentDAO {

	@Autowired
	private DbLogger dblogger;
	
	public CustomerCommentDAOImpl() {
		super(CustomerCommentDO.class.getSimpleName());
	}
	
	@Override
	public Result<Long> insert(CustomerCommentDO customerCommentDO){
		Result<Long> result = super.insert(customerCommentDO);
		if(result.isSuccess() && customerCommentDO.getCustomerId()!=null){
			dblogger.write(LogOpEnum.评论, customerCommentDO.getCustomerId());
		}
		return result;
	}

	@Override
	public Result<Integer> getCount(CustomerCommentQueryCondition queryCondition) {
		return super.getCount(queryCondition);
	}

	@Override
	public Result<List<CustomerCommentDO>> getPage(CustomerCommentQueryCondition queryCondition) {
		return super.getPage(queryCondition);
	}

	@Override
	public Integer deleteInvalid() {
		try {
			return super.getSqlMapClient().delete(super.getNamespace()+".deleteInvalid");
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
}
