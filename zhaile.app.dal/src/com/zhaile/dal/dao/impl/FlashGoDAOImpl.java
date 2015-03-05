package com.zhaile.dal.dao.impl;

import java.util.Date;
import java.util.List;

import com.victor.framework.common.shared.Result;
import com.victor.framework.dal.basic.EntityDAO;
import com.victor.framework.dal.basic.Paging;
import com.zhaile.dal.dao.FlashGoDAO;
import com.zhaile.dal.model.FlashGoDO;
import com.zhaile.dal.query.condition.FlashGoQueryCondition;

public class FlashGoDAOImpl extends EntityDAO<FlashGoDO> implements FlashGoDAO{

	public FlashGoDAOImpl() {
		super(FlashGoDO.class.getSimpleName());
	}

	@Override
	public List<FlashGoDO> getByOpenDate(Date dateFrom, Date dateTo) {
		FlashGoQueryCondition queryCondition = new FlashGoQueryCondition();
		queryCondition.gmtOpenStart(dateFrom).gmtOpenEnd(dateTo);
		return super.getPage(queryCondition).getDataObject();
	}

	@Override
	public Paging<FlashGoDO> getByOpenDate(FlashGoQueryCondition queryCondition) {
		int totalSize = super.getCount(queryCondition).getDataObject();
		Result<List<FlashGoDO>> result = super.getPage(queryCondition);
		@SuppressWarnings("unchecked")
		Paging<FlashGoDO> flashGoPage = queryCondition.getPaging(totalSize, 5);
		List<FlashGoDO> flashGoList = result.getDataObject();
		flashGoPage.setData(flashGoList);
		return flashGoPage;
	}

	@Override
	public void sold(Long id) {
		FlashGoDO forUpdate = new FlashGoDO();
		forUpdate.setId(id);
		super.updateBySID("sold",forUpdate);
	}

}
