package com.zhaile.dal.dao;

import java.util.Date;
import java.util.List;

import com.victor.framework.common.shared.Result;
import com.victor.framework.dal.basic.Paging;
import com.zhaile.dal.model.FlashGoDO;
import com.zhaile.dal.query.condition.FlashGoQueryCondition;

public interface FlashGoDAO {
	Result<Long> insert(FlashGoDO flashGoDO);
	
	Result<Boolean> update(FlashGoDO flashGoDO);
	
	Result<FlashGoDO> getById(Long id);
	
	void sold(Long id);
	
	List<FlashGoDO> getByOpenDate(Date dateFrom, Date dateTo);
	
	Paging<FlashGoDO> getByOpenDate(FlashGoQueryCondition queryCondition);

}
