package com.zhaile.dal.dao;

import java.util.List;

import com.victor.framework.common.shared.Result;
import com.zhaile.dal.model.vo.PaymentFormBean;
import com.zhaile.dal.enumerate.EnableEnum;
import com.zhaile.dal.enumerate.ServiceTypeEnum;
import com.zhaile.dal.model.PaymentDO;
import com.zhaile.dal.model.ShoppingCarDO;
import com.zhaile.dal.model.vo.ShoppingCarPay;
import com.zhaile.dal.query.condition.ShoppingCarQueryCondition;

public interface ShoppingCarDAO {
	/**
	 * 插入一条数据
	 * @param shoppingCarDO
	 * @return
	 */
	Result<Long> insert(ShoppingCarDO shoppingCarDO);
	
	/**
	 * 更新购物车
	 * @param shoppingCarDO
	 * @return
	 */
	Result<Boolean> update(ShoppingCarDO shoppingCarDO);
	
	/**
	 * 更新购物车
	 * @param shoppingCarDO
	 * @return
	 */
	Result<Boolean> updateValid(Long shopId,Long customerId, EnableEnum valid);
	
	/**
	 * 删除购物车中商品
	 * @param id
	 * @return
	 */
	Result<Boolean> delete(Long id);
	
	/**
	 * 获取一条购物车记录
	 * @param id
	 * @return
	 */
	Result<ShoppingCarDO> getById(Long id);
	
	/**
	 * 批量获取
	 * @param id
	 * @return
	 */
	Result<List<ShoppingCarDO>> getByIds(Long[] id);
	
	/**
	 * 根据查询条件获取
	 * @param queryCondition
	 * @return
	 */
	Result<List<ShoppingCarDO>> getPage(ShoppingCarQueryCondition queryCondition);
	
	/**
	 * 根据查询条件获取个数
	 * @param queryCondition
	 * @return
	 */
	Result<Integer> getCount(ShoppingCarQueryCondition queryCondition);
	
	/**
	 * 付款
	 * @param list
	 * @param serviceType
	 * @param customerId
	 * @param charge
	 * @param comment
	 * @param contact
	 * @param mobile
	 * @param source
	 * @return
	 */
	Result<PaymentDO> pay(List<ShoppingCarPay> list,ServiceTypeEnum serviceType, Long customerId, PaymentFormBean paymentFormBean);

	/**
	 * 回收那些已经下架的商品
	 */
	Result<Boolean> recycle(Long[] prodId);
}
