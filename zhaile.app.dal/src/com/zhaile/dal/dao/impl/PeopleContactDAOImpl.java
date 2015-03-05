package com.zhaile.dal.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.victor.framework.common.shared.Result;
import com.victor.framework.dal.basic.EntityDAO;
import com.zhaile.dal.dao.PeopleContactDAO;
import com.zhaile.dal.enumerate.ForeignKeyEnum;
import com.zhaile.dal.enumerate.LogOpEnum;
import com.zhaile.dal.log.DbLogger;
import com.zhaile.dal.model.PeopleContactDO;

public class PeopleContactDAOImpl extends EntityDAO<PeopleContactDO> implements PeopleContactDAO{

	@Autowired
	private DbLogger dblogger;
	
	public PeopleContactDAOImpl() {
		super(PeopleContactDO.class.getSimpleName());
	}
	
	@Override
	public Result<Long> insert(PeopleContactDO peopleContactDO){
		Result<Long> result = super.insert(peopleContactDO);
		if(result.isSuccess() && ForeignKeyEnum.用户.equals(peopleContactDO.getForeignKeyType())){
			dblogger.write(LogOpEnum.更新, peopleContactDO.getForeignId());
		}
		return result;
	}
	
	@Override
	public Result<Boolean> update(PeopleContactDO peopleContactDO){
		Result<Boolean> result = super.update(peopleContactDO);
		if(result.isSuccess() && ForeignKeyEnum.用户.equals(peopleContactDO.getForeignKeyType())){
			dblogger.write(LogOpEnum.更新, peopleContactDO.getForeignId());
		}
		return result;
	}

	@Override
	public Result<List<PeopleContactDO>> getByCustomerId(Long customerId) {
		PeopleContactDO query = new PeopleContactDO();
		query.setForeignId(customerId);
		query.setForeignKeyType(ForeignKeyEnum.用户.getCode());
		return super.queryForList("getByForeignId", query.toMap());
	}

	@Override
	public Result<List<PeopleContactDO>> getByShopId(Long shopId) {
		PeopleContactDO query = new PeopleContactDO();
		query.setForeignId(shopId);
		query.setForeignKeyType(ForeignKeyEnum.商家.getCode());
		return super.queryForList("getByForeignId", query.toMap());
	}

	@Override
	public Result<List<PeopleContactDO>> getByIP(String IP) {
		PeopleContactDO query = new PeopleContactDO();
		query.setForeignKey(IP);
		query.setForeignKeyType(ForeignKeyEnum.IP.getCode());
		return super.queryForList("getByForeignId", query.toMap());
	}

}
