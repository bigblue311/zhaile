package com.zhaile.dal.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.victor.framework.common.shared.Result;
import com.victor.framework.common.tools.CollectionTools;
import com.victor.framework.common.tools.LogTools;
import com.victor.framework.dal.basic.EntityDAO;
import com.zhaile.dal.dao.CustomerDAO;
import com.zhaile.dal.enumerate.LogOpEnum;
import com.zhaile.dal.log.DbLogger;
import com.zhaile.dal.model.CustomerDO;
import com.zhaile.dal.query.condition.CustomerQueryCondition;

public class CustomerDAOImpl extends EntityDAO<CustomerDO> implements CustomerDAO {
	
	@Autowired
	private DbLogger dblogger;
	
	public CustomerDAOImpl() {
		super(CustomerDO.class.getSimpleName());
	}

	private final LogTools log = new LogTools(CustomerDAOImpl.class);
	
	@Override
	public Result<Boolean> update(CustomerDO customerDO){
		Result<Boolean> result = super.update(customerDO);
		if(result.isSuccess()){
			dblogger.write(LogOpEnum.更新,customerDO.getId());
		}
		return result;
	}
	
	@Override
	public Result<CustomerDO> loginByName(String name, String password) {
		CustomerDO loginCredential = new CustomerDO();
		loginCredential.setName(name);
		loginCredential.setPassword(password);
		return login("loginByName",loginCredential);
	}
	
	@Override
	public Result<CustomerDO> loginByEmail(String email, String password) {
		CustomerDO loginCredential = new CustomerDO();
		loginCredential.setEmail(email);
		loginCredential.setPassword(password);
		return login("loginByEmail",loginCredential);
	}
	
	@Override
	public Result<CustomerDO> loginByMobile(String mobile, String password) {
		CustomerDO loginCredential = new CustomerDO();
		loginCredential.setMobile(mobile);
		loginCredential.setPassword(password);
		return login("loginByMobile",loginCredential);
	}
	
	@Override
	public Result<CustomerDO> checkByMobile(String mobile, String name) {
		CustomerDO loginCredential = new CustomerDO();
		loginCredential.setMobile(mobile);
		loginCredential.setName(name);
		return login("checkByMobile",loginCredential);
	}

	@Override
	public Result<CustomerDO> loginFromThrid(String thirdPartUser,
			String thirdPartUserId) {
		CustomerDO loginCredential = new CustomerDO();
		loginCredential.setThirdPartUser(thirdPartUser);
		loginCredential.setThirdPartUserId(thirdPartUserId);
		Result<CustomerDO> result = login("loginFromThridPart",loginCredential);
		return result;
	}
	
	private Result<CustomerDO> login(String sqlId,CustomerDO loginCredential){
		try {
			Result<CustomerDO> result = super.queryForEntity(sqlId, loginCredential);
			if(result.isSuccess()){
				CustomerDO customer = result.getDataObject();
				dblogger.write(LogOpEnum.登录,customer.getId());
				return Result.newInstance(customer, "登录成功", true);
			} else {
				return Result.newInstance(null, "登录失败", false);
			}
		} catch (Exception e) {
			log.error("登录失败", e);
			return Result.newInstance(null, "登录失败", false);
		}
	}

	@Override
	public Result<Boolean> checkExistName(String name) {
		CustomerDO loginCredential = new CustomerDO();
		loginCredential.setName(name);
		try {
			Result<List<CustomerDO>> result = super.queryForList("getByName", loginCredential);
			if(result.isSuccess()){
				if(CollectionTools.isNotEmpty(result.getDataObject())){
					return Result.newInstance(false, "该用户名已经存在", false);
				} else {
					return Result.newInstance(true, "该用户名可以注册", true);
				}
			} 
			return Result.newInstance(false, "校验失败", false);
		} catch (Exception e) {
			log.error("校验异常", e);
			return Result.newInstance(false, "校验异常", false);
		}
	}
	
	@Override
	public Result<Boolean> checkExistEmail(String email) {
		CustomerDO loginCredential = new CustomerDO();
		loginCredential.setEmail(email);
		try {
			Result<List<CustomerDO>> result = super.queryForList("getByEmail", loginCredential);
			if(result.isSuccess()){
				if(CollectionTools.isNotEmpty(result.getDataObject())){
					return Result.newInstance(false, "该邮箱已经存在", false);
				} else {
					return Result.newInstance(true, "该邮箱可以注册", true);
				}
			} 
			return Result.newInstance(false, "校验失败", false);
		} catch (Exception e) {
			log.error("校验异常", e);
			return Result.newInstance(false, "校验异常", false);
		}
	}
	
	@Override
	public Result<Boolean> checkExistMobile(String mobile) {
		CustomerDO loginCredential = new CustomerDO();
		loginCredential.setMobile(mobile);
		try {
			Result<List<CustomerDO>> result = super.queryForList("getByMobile", loginCredential);
			if(result.isSuccess()){
				if(CollectionTools.isNotEmpty(result.getDataObject())){
					return Result.newInstance(false, "该注册手机号码已经存在", false);
				} else {
					return Result.newInstance(true, "该注册手机号码可以注册", true);
				}
			} 
			return Result.newInstance(false, "校验失败", false);
		} catch (Exception e) {
			log.error("校验异常", e);
			return Result.newInstance(false, "校验异常", false);
		}
	}

	@Override
	public Result<List<CustomerDO>> getPage(CustomerQueryCondition queryCondition) {
		return super.getPage(queryCondition);
	}

	@Override
	public Result<Integer> getCount(CustomerQueryCondition queryCondition) {
		return super.getCount(queryCondition);
	}	
}
