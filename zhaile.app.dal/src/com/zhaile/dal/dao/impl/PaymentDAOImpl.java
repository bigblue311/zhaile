package com.zhaile.dal.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.victor.framework.common.shared.Result;
import com.victor.framework.common.tools.StringTools;
import com.victor.framework.dal.basic.EntityDAO;
import com.zhaile.dal.dao.PaymentDAO;
import com.zhaile.dal.enumerate.DeliveryStatusEnum;
import com.zhaile.dal.enumerate.LogOpEnum;
import com.zhaile.dal.enumerate.SmsStatusEnum;
import com.zhaile.dal.log.DbLogger;
import com.zhaile.dal.model.PaymentDO;
import com.zhaile.dal.query.condition.PaymentQueryCondition;

public class PaymentDAOImpl extends EntityDAO<PaymentDO> implements PaymentDAO {

	@Autowired
	private DbLogger dblogger;
	
	public PaymentDAOImpl() {
		super(PaymentDO.class.getSimpleName());
	}
	
	@Override
	public Result<Long> insert(PaymentDO paymentDO){
		Result<Long> result = super.insert(paymentDO);
		if(result.isSuccess()){
			dblogger.write(LogOpEnum.支付, paymentDO.getCustomerId());
			dblogger.write(paymentDO.getCustomerId(),DeliveryStatusEnum.未处理,paymentDO.getId());
		}
		return result;
	}
	
	@Override
	public Result<Boolean> update(PaymentDO paymentDO){
		Result<Boolean> result = super.update(paymentDO);
		if(StringTools.isNotEmpty(paymentDO.getStatus())){
			if(result.isSuccess()){
				dblogger.write(paymentDO.getCustomerId(),DeliveryStatusEnum.getByCode(paymentDO.getStatus()),paymentDO.getId());
			}
		}
		return result;
	}

	@Override
	public Result<List<PaymentDO>> getPage(PaymentQueryCondition queryCondition) {
		return super.getPage(queryCondition);
	}

	@Override
	public Result<Integer> getCount(PaymentQueryCondition queryCondition) {
		return super.getCount(queryCondition);
	}

	@Override
	public Result<Double> getTotalNetpay(PaymentQueryCondition queryCondition) {
		Result<Object> result = super.queryForEntity(PaymentDO.class.getSimpleName(), "getTotalNetpay", queryCondition.getQueryMap());
		if(result.isSuccess()){
			try{
				Double totalNetpay = Double.parseDouble(result.getDataObject().toString());
				return Result.newInstance(totalNetpay, "获取总支付成功", true);
			} catch(Exception e) {
				return Result.newInstance(0d, "获取总支付失败,"+e.getMessage(), false);
			}
		} else {
			return Result.newInstance(0d, "获取总支付失败", false);
		}
	}

	@Override
	public Result<Double> getTotalCharge(PaymentQueryCondition queryCondition) {
		Result<Object> result = super.queryForEntity(PaymentDO.class.getSimpleName(), "getTotalCharge", queryCondition.getQueryMap());
		if(result.isSuccess()){
			try{
				Double totalNetpay = Double.parseDouble(result.getDataObject().toString());
				return Result.newInstance(totalNetpay, "获取总支付成功", true);
			} catch(Exception e) {
				return Result.newInstance(0d, "获取总支付失败,"+e.getMessage(), false);
			}
		} else {
			return Result.newInstance(0d, "获取总支付失败", false);
		}
	}

	@Override
	public Result<List<PaymentDO>> getSmsTask() {
		super.update("expireSms");
		PaymentQueryCondition queryCondition = new PaymentQueryCondition();
		queryCondition.smsStatus(SmsStatusEnum.准备发送.getCode());
		return super.getPage(queryCondition);
	}
}
