package com.zhaile.dal.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.victor.framework.common.shared.Result;
import com.victor.framework.common.tools.LogTools;
import com.victor.framework.common.tools.ObjectTools;
import com.victor.framework.dal.basic.EntityDAO;
import com.zhaile.dal.dao.ShoppingCarDAO;
import com.zhaile.dal.enumerate.CreditActionEnum;
import com.zhaile.dal.enumerate.DeliveryStatusEnum;
import com.zhaile.dal.enumerate.EnableEnum;
import com.zhaile.dal.enumerate.LogOpEnum;
import com.zhaile.dal.enumerate.ServiceTypeEnum;
import com.zhaile.dal.log.DbLogger;
import com.zhaile.dal.model.CustomerCreditDO;
import com.zhaile.dal.model.OrderDO;
import com.zhaile.dal.model.PaymentDO;
import com.zhaile.dal.model.ProductDO;
import com.zhaile.dal.model.ShoppingCarDO;
import com.zhaile.dal.model.vo.PaymentFormBean;
import com.zhaile.dal.model.vo.ShoppingCarPay;
import com.zhaile.dal.query.condition.ShoppingCarQueryCondition;

public class ShoppingCarDAOImpl extends EntityDAO<ShoppingCarDO> implements ShoppingCarDAO {

	@Autowired
	private DbLogger dblogger;
	
	private final LogTools log = new LogTools(ShoppingCarDAO.class);
	
	public ShoppingCarDAOImpl() {
		super(ShoppingCarDO.class.getSimpleName());
	}
	
	@Override
	public Result<Long> insert(ShoppingCarDO shoppingCarDO){
		Result<Long> result = super.insert(shoppingCarDO);
		if(result.isSuccess()){
			dblogger.write(LogOpEnum.购物车, shoppingCarDO.getCustomerId());
		}
		return result;
	}

	@Override
	public Result<PaymentDO> pay(List<ShoppingCarPay> list,
							   ServiceTypeEnum serviceType, 
							   Long customerId,
							   PaymentFormBean paymentFormBean) {
		try {
			getSqlMapClient().startTransaction();
			Double netpay = new Double(0);
			PaymentDO payment = paymentFormBean.getPaymentDO();
			payment.setServiceType(serviceType.getCode());
			payment.setNetpay(netpay);
			payment.setReceived(new Double(0));
			Result<Long> pInsertresult = insert(PaymentDO.class.getSimpleName(),payment);
			if(!pInsertresult.isSuccess()){
				return Result.newInstance(null, "添加支付失败", false);
			}
			for(ShoppingCarPay shoppingCarPay : list){
				ShoppingCarDO shoppingCarDO =	shoppingCarPay.getShopCar();
				ProductDO product = shoppingCarPay.getProductDO();
				
				Result<Boolean> deleteResult = super.delete(shoppingCarDO.getId());
				if(!deleteResult.isSuccess()){
					return Result.newInstance(null, "删除购物车失败", false);
				}
				
				OrderDO order = new OrderDO();
				ObjectTools.copy(shoppingCarDO, order);
				order.setPrice(product.getPrice());
				order.setPaymentId(pInsertresult.getDataObject());
				if(!insert(OrderDO.class.getSimpleName(),order).isSuccess()){
					return Result.newInstance(null, "保存订单失败", false);
				}
				netpay += product.getPrice()*shoppingCarDO.getQuantity();
			}
			payment.setId(pInsertresult.getDataObject());
			payment.setNetpay(netpay);
			payment.setReceived(netpay+payment.getCharge());
			payment.setStatus(DeliveryStatusEnum.未处理.getCode());
			if(customerId != null) {
				payment.setCustomerId(customerId);
				Result<Object> result = queryForEntity(CustomerCreditDO.class.getSimpleName(),"getByCustomerId","customerId", customerId);
				if(result.isSuccess()){
					CustomerCreditDO customerCreditDO = (CustomerCreditDO)result.getDataObject();
					if(customerCreditDO == null){
						customerCreditDO = new CustomerCreditDO();
						customerCreditDO.setCustomerId(customerId);
						customerCreditDO.setCreditPoints(netpay.intValue());
						insert(CustomerCreditDO.class.getSimpleName(),customerCreditDO);
					} else {
						customerCreditDO.setCreditPoints(customerCreditDO.getCreditPoints()+netpay.intValue());
						update(CustomerCreditDO.class.getSimpleName(),customerCreditDO);
					}
				} else {
					CustomerCreditDO customerCreditDO = new CustomerCreditDO();
					customerCreditDO.setCustomerId(customerId);
					customerCreditDO.setCreditPoints(netpay.intValue());
					insert(CustomerCreditDO.class.getSimpleName(),customerCreditDO);
				}
				dblogger.write(customerId, netpay.intValue(), CreditActionEnum.增加, "订餐");
			}
			Result<Boolean> pUpdateresult = update(PaymentDO.class.getSimpleName(),payment);
			if(!pUpdateresult.isSuccess()){
				return Result.newInstance(null, "保存支付失败", false);
			}
			getSqlMapClient().commitTransaction();
			return Result.newInstance(payment, "订单提交成功", true);	
		} catch (Exception e) {
			log.error("开启事务失败",e);
			return Result.newInstance(null, "订单提交失败", false);
		} finally{
			try {
				getSqlMapClient().endTransaction();
			} catch (SQLException e) {
				log.error("关闭事务失败",e);
				return Result.newInstance(null, "订单提交失败", false);
			}
		}
	}

	@Override
	public Result<List<ShoppingCarDO>> getPage(ShoppingCarQueryCondition queryCondition) {
		return super.getPage(queryCondition);
	}

	@Override
	public Result<Integer> getCount(ShoppingCarQueryCondition queryCondition) {
		return super.getCount(queryCondition);
	}

	@Override
	public Result<Boolean> updateValid(Long shopId,Long customerId, EnableEnum valid) {
		ShoppingCarDO forUpdate = new ShoppingCarDO();
		forUpdate.setShopId(shopId);
		forUpdate.setValid(valid.getCode());
		forUpdate.setCustomerId(customerId);
		return super.updateBySID("updateValid",forUpdate);
	}
}
