package com.zhaile.dal.dao;

import java.util.List;

import com.victor.framework.common.shared.Result;
import com.zhaile.dal.model.CustomerDO;
import com.zhaile.dal.query.condition.CustomerQueryCondition;

public interface CustomerDAO {
	Result<Long> insert(CustomerDO customerDO);
	
	Result<Boolean> update(CustomerDO customerDO);
	
	Result<Boolean> delete(Long id);
	
	Result<CustomerDO> getById(Long id);
	
	Result<CustomerDO> loginByName(String name,String password);
	
	Result<CustomerDO> loginByEmail(String email,String password);
	
	Result<CustomerDO> loginByMobile(String mobile,String password);
	
	Result<CustomerDO> checkByMobile(String mobile,String name);
	
	Result<CustomerDO> loginFromThrid(String thridPartType,String thridPartId);
	
	Result<Boolean> checkExistName(String name);
	
	Result<Boolean> checkExistEmail(String email);
	
	Result<Boolean> checkExistMobile(String mobile);
	
	Result<List<CustomerDO>> getPage(CustomerQueryCondition queryCondition);

	Result<Integer> getCount(CustomerQueryCondition queryCondition);
}
