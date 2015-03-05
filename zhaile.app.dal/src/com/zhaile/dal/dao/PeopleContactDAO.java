package com.zhaile.dal.dao;

import java.util.List;

import com.victor.framework.common.shared.Result;
import com.zhaile.dal.model.PeopleContactDO;

public interface PeopleContactDAO {
	Result<Long> insert(PeopleContactDO peopleContactDO);
	
	Result<Boolean> update(PeopleContactDO peopleContactDO);
	
	Result<Boolean> delete(Long id);
	
	Result<PeopleContactDO> getById(Long id);
	
	Result<List<PeopleContactDO>> getByCustomerId(Long customerId);
	
	Result<List<PeopleContactDO>> getByShopId(Long shopId);
	
	Result<List<PeopleContactDO>> getByIP(String IP);
}
