package com.zhaile.dal.dao;

import java.util.List;

import com.victor.framework.common.shared.Result;
import com.zhaile.dal.model.PaymentDO;
import com.zhaile.dal.query.condition.PaymentQueryCondition;

public interface PaymentDAO {
	Result<Long> insert(PaymentDO paymentDO);
	
	Result<Boolean> update(PaymentDO paymentDO);
	
	Result<PaymentDO> getById(Long id);
	
	Result<List<PaymentDO>> getByIds(Long[] id);
	
	Result<List<PaymentDO>> getSmsTask();
	
	Result<List<PaymentDO>> getPage(PaymentQueryCondition queryCondition);
	
	Result<Integer> getCount(PaymentQueryCondition queryCondition);
	
	Result<Double> getTotalNetpay(PaymentQueryCondition queryCondition);
	
	Result<Double> getTotalCharge(PaymentQueryCondition queryCondition);
}
