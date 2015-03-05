package com.zhaile.dal.dao.impl;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;

import com.victor.framework.common.shared.Result;
import com.victor.framework.common.tools.LogTools;
import com.victor.framework.dal.basic.EntityDAO;
import com.zhaile.dal.dao.CustomerCreditDAO;
import com.zhaile.dal.enumerate.CreditActionEnum;
import com.zhaile.dal.log.DbLogger;
import com.zhaile.dal.model.CustomerCreditDO;

public class CustomerCreditDAOImpl extends EntityDAO<CustomerCreditDO> implements CustomerCreditDAO {

	@Autowired
	private DbLogger dblogger;
	
	public CustomerCreditDAOImpl() {
		super(CustomerCreditDO.class.getSimpleName());
	}
	
	private final LogTools log = new LogTools(CustomerCreditDAOImpl.class);

	@Override
	public Result<CustomerCreditDO> getByCustomerId(Long customerId) {
		return queryForEntity("getByCustomerId","customerId", customerId);
	}

	@Override
	public Result<CustomerCreditDO> gainCredits(Long customerId, Integer creditPoints) {
		CustomerCreditDO customerCreditDO = null;
		try {
			getSqlMapClient().startTransaction();
			Result<CustomerCreditDO> result = getByCustomerId(customerId);
			if(result.isSuccess()){
				customerCreditDO = result.getDataObject();
				if(customerCreditDO == null){
					customerCreditDO = new CustomerCreditDO();
					customerCreditDO.setCustomerId(customerId);
					customerCreditDO.setCreditPoints(creditPoints);
					insert(customerCreditDO);
				} else {
					customerCreditDO.setCreditPoints(customerCreditDO.getCreditPoints()+creditPoints);
					update(customerCreditDO);
				}
			} else {
				customerCreditDO = new CustomerCreditDO();
				customerCreditDO.setCustomerId(customerId);
				customerCreditDO.setCreditPoints(creditPoints);
				insert(customerCreditDO);
			}
			dblogger.write(customerId, creditPoints, CreditActionEnum.增加, "");
			getSqlMapClient().commitTransaction();
			return Result.newInstance(customerCreditDO, "添加积分成功", true);
		} catch (SQLException e) {
			log.error("开启事务失败",e);
			return Result.newInstance(customerCreditDO, "添加积分失败", false);
		} finally{
			try {
				getSqlMapClient().endTransaction();
			} catch (SQLException e) {
				log.error("关闭事务失败",e);
				return Result.newInstance(customerCreditDO, "添加积分失败", false);
			}
		}
	}

	@Override
	public Result<CustomerCreditDO> useCredits(Long customerId, Integer creditPoints) {
		CustomerCreditDO customerCreditDO = null;
		try {
			getSqlMapClient().startTransaction();
			Result<CustomerCreditDO> result = getByCustomerId(customerId);
			if(result.isSuccess()){
				customerCreditDO = result.getDataObject();
				if(customerCreditDO == null){
					customerCreditDO = new CustomerCreditDO();
					customerCreditDO.setCustomerId(customerId);
					customerCreditDO.setCreditPoints(0);
					insert(customerCreditDO);
				} else {
					if(customerCreditDO.getCreditPoints() >= creditPoints){
						customerCreditDO.setCreditPoints(customerCreditDO.getCreditPoints()-creditPoints);
						update(customerCreditDO);
					}
				}
				dblogger.write(customerId, creditPoints, CreditActionEnum.减少, "");
				getSqlMapClient().commitTransaction();
				return Result.newInstance(customerCreditDO, "使用积分成功", true);
			}
			
		} catch (SQLException e) {
			log.error("开启事务失败",e);
			return Result.newInstance(customerCreditDO, "使用积分失败", false);
		} finally{
			try {
				getSqlMapClient().endTransaction();
			} catch (SQLException e) {
				log.error("关闭事务失败",e);
				return Result.newInstance(customerCreditDO, "使用积分失败", false);
			}
		}
		return Result.newInstance(customerCreditDO, "使用积分失败", false);
	}

}
