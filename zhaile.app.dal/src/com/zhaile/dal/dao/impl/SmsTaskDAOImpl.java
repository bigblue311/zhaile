package com.zhaile.dal.dao.impl;

import java.util.List;

import com.victor.framework.common.shared.Result;
import com.victor.framework.dal.basic.EntityDAO;
import com.zhaile.dal.dao.SmsTaskDAO;
import com.zhaile.dal.model.SmsTaskDO;
import com.zhaile.dal.query.condition.SmsTaskQueryCondition;

public class SmsTaskDAOImpl extends EntityDAO<SmsTaskDO> implements SmsTaskDAO{

	public SmsTaskDAOImpl() {
		super(SmsTaskDO.class.getSimpleName());
	}

	@Override
	public Result<List<SmsTaskDO>> getPage(SmsTaskQueryCondition queryCondition) {
		return super.getPage(queryCondition);
	}

	@Override
	public Result<Boolean> retry(Long id) {
		SmsTaskDO smsTaskDO = new SmsTaskDO();
		smsTaskDO.setId(id);
		return super.updateBySID("retry", smsTaskDO);
	}

	@Override
	public void expireSms() {
		super.update("expireSms");
	}

}
