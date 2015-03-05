package com.zhaile.dal.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.victor.framework.common.shared.Result;
import com.victor.framework.dal.basic.EntityDAO;
import com.zhaile.dal.dao.CustomerFavDAO;
import com.zhaile.dal.enumerate.LogOpEnum;
import com.zhaile.dal.log.DbLogger;
import com.zhaile.dal.model.CustomerFavDO;
import com.zhaile.dal.query.condition.CustomerFavQueryCondition;

public class CustomerFavDAOImpl extends EntityDAO<CustomerFavDO> implements CustomerFavDAO {

	@Autowired
	private DbLogger dblogger;
	
	public CustomerFavDAOImpl() {
		super(CustomerFavDO.class.getSimpleName());
	}
	
	@Override
	public Result<Long> insert(CustomerFavDO customerFavDO){
		dblogger.write(LogOpEnum.收藏, customerFavDO.getCustomerId());
		CustomerFavDO exist = super.getById(customerFavDO.getId()).getDataObject();
		if(exist == null) {
			return super.insert(customerFavDO);
		} else {
			super.update(customerFavDO);
			return Result.newInstance(customerFavDO.getId(), "更新成功", true);
		}
		
	}
	
	@Override
	public Result<Boolean> delete(Long id){
		CustomerFavDO customerFavDO = super.getById(id).getDataObject();
		if(customerFavDO!=null) {
			dblogger.write(LogOpEnum.取消收藏, customerFavDO.getCustomerId());
		}
		return super.delete(id);
	}

	@Override
	public Result<List<CustomerFavDO>> getPage(CustomerFavQueryCondition queryCondition) {
		return super.getPage(queryCondition);
	}
}
