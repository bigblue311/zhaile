package com.zhaile.dal.log;

import org.springframework.beans.factory.annotation.Autowired;

import com.victor.framework.common.shared.Result;
import com.zhaile.dal.dao.LogCreditDAO;
import com.zhaile.dal.dao.LogDAO;
import com.zhaile.dal.dao.LogDeliveryDAO;
import com.zhaile.dal.dao.LogSearchDAO;
import com.zhaile.dal.enumerate.CreditActionEnum;
import com.zhaile.dal.enumerate.DeliveryStatusEnum;
import com.zhaile.dal.enumerate.LogOpEnum;
import com.zhaile.dal.model.LogCreditDO;
import com.zhaile.dal.model.LogDO;
import com.zhaile.dal.model.LogDeliveryDO;
import com.zhaile.dal.model.LogSearchDO;

public class DbLogger{
	
	@Autowired
	private LogDAO logDAO;
	
	@Autowired
	private LogSearchDAO logSearchDAO;
	
	@Autowired
	private LogCreditDAO logCreditDAO;
	
	@Autowired
	private LogDeliveryDAO logDeliveryDAO;
	
	public void write(LogOpEnum logOp,Long customerId){
		LogDO log = new LogDO();
		log.setCustomerId(customerId);
		log.setOpType(logOp.getCode());
		logDAO.insert(log);
	}
	
	public void write(Long customerId, String keyword,Long tagId){
		LogDO log = new LogDO();
		log.setCustomerId(customerId);
		log.setOpType(LogOpEnum.搜索.getCode());
		Long logId = null;
		Result<Long> logResult = logDAO.insert(log);
		if(logResult.isSuccess()){
			logId = logResult.getDataObject();
		} else {
			return;
		}
		writeSearchLog(logId,keyword);
	}
	
	public void write(Long customerId, Integer creditPoint,CreditActionEnum creditActionEnum, String userReason){
		LogDO log = new LogDO();
		log.setCustomerId(customerId);
		log.setOpType(LogOpEnum.积分.getCode());
		Long logId = null;
		Result<Long> logResult = logDAO.insert(log);
		if(logResult.isSuccess()){
			logId = logResult.getDataObject();
		} else {
			return;
		}
		writeCreditLog(logId,creditActionEnum,creditPoint, userReason);
	}
	
	public void write(Long customerId, DeliveryStatusEnum deliveryStatus, Long paymentId){
		LogDO log = new LogDO();
		log.setCustomerId(customerId);
		log.setOpType(LogOpEnum.送达.getCode());
		Long logId = null;
		Result<Long> logResult = logDAO.insert(log);
		if(logResult.isSuccess()){
			logId = logResult.getDataObject();
		} else {
			return;
		}
		writeDeliveryLog(logId,paymentId,deliveryStatus);
	}

	private void writeSearchLog(Long logId,String keyword){
		LogSearchDO logSerach = new LogSearchDO();
		logSerach.setLogId(logId);
		logSerach.setKeyword(keyword);
		logSearchDAO.insert(logSerach);
	}
	
	private void writeCreditLog(Long logId, CreditActionEnum creditActionEnum, Integer creditPoint,String userReason){
		LogCreditDO logCredit = new LogCreditDO();
		logCredit.setLogId(logId);
		logCredit.setUserAction(creditActionEnum.getCode());
		logCredit.setCreditPoint(creditPoint);
		logCredit.setUserReason(userReason);
		logCreditDAO.insert(logCredit);
	}
	
	private void writeDeliveryLog(Long logId,Long paymentId,DeliveryStatusEnum deliveryStatus){
		LogDeliveryDO logDeliveryDO = new LogDeliveryDO();
		logDeliveryDO.setLogId(logId);
		logDeliveryDO.setPaymentId(paymentId);
		logDeliveryDO.setDeliveryStatus(deliveryStatus.getCode());
		logDeliveryDAO.insert(logDeliveryDO);
	}
}
