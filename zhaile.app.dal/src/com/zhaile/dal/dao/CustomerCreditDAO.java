package com.zhaile.dal.dao;

import com.victor.framework.common.shared.Result;
import com.zhaile.dal.model.CustomerCreditDO;

public interface CustomerCreditDAO {
	//Result<Boolean> insert(CustomerCreditDO customerCreditDO);
	
	//Result<Boolean> update(CustomerCreditDO customerCreditDO);
	
	//Result<Boolean> delete(Long id);
	
	Result<CustomerCreditDO> getByCustomerId(Long customerId);
	
	Result<CustomerCreditDO> gainCredits(Long customerId, Integer creditPoints);
	
	Result<CustomerCreditDO> useCredits(Long customerId, Integer creditPoints);
}
