package com.zhaile.biz.web.manager;

import java.util.Date;
import java.util.List;

import com.victor.framework.common.shared.Result;
import com.victor.framework.dal.basic.Paging;
import com.zhaile.biz.web.json.OrderQueryJson;
import com.zhaile.biz.web.json.PaymentQueryJson;
import com.zhaile.biz.web.json.PaymentStatisticJson;
import com.zhaile.biz.web.model.ChargeRequestVO;
import com.zhaile.biz.web.model.OrderVO;
import com.zhaile.biz.web.model.PaymentVO;
import com.zhaile.biz.web.model.ShoppingCarVO;
import com.zhaile.dal.model.ProductDO;
import com.zhaile.dal.model.ShoppingCarDO;
import com.zhaile.dal.model.SmsTaskDO;
import com.zhaile.dal.model.vo.PaymentFormBean;
import com.zhaile.dal.query.condition.OrderQueryCondition;
import com.zhaile.dal.query.condition.PaymentQueryCondition;

public interface TransactionManager {
	/**
	 * 添加到购物车
	 * @param prodId
	 * @param quntity
	 * @param customerId
	 * @return
	 */
	Result<ShoppingCarVO> addToShoppingCar(Long prodId, Integer quntity, Long customerId);
	
	/**
	 * 修改购物车
	 * @param prodId
	 * @param quntity
	 * @param customerId
	 * @return
	 */
	Result<ShoppingCarVO> editShoppingCar(Long prodId, Integer quntity, Long customerId);
	
	/**
	 * 从购物车中清除
	 * @param prodId
	 * @param customerId
	 * @return
	 */
	Result<Boolean> RemoveFromShoppingCar(Long prodId, Long customerId);
	
	
	List<ShoppingCarVO> checkValid(List<ShoppingCarVO> list);
	
	/**
	 * 根据用户ID获取购物车
	 * @param customerId
	 * @return
	 */
	List<ShoppingCarVO> getShoppingCarByCustomerId(Long customerId);
	
	/**
	 * 类型转换
	 * @param shoppingCarDO
	 * @return
	 */
	ShoppingCarVO shoppingCarDO2VO(ShoppingCarDO shoppingCarDO);
	
	/**
	 * 是否是打折商品
	 * @param prod
	 * @return
	 */
	boolean isProdPromot(ProductDO prod);
	
	/**
	 * 支付
	 * @param paymentFormBean
	 * @param list
	 * @param customerId
	 * @return
	 */
	Result<PaymentQueryJson> addPayment(PaymentFormBean paymentFormBean,List<ShoppingCarVO> list,Long customerId);
	
	/**
	 * 查询支付
	 * @param queryCondition
	 * @return
	 */
	Paging<PaymentQueryJson> queryPayment(PaymentQueryCondition queryCondition);
	
	/**
	 * 查询您短信情况
	 * @return
	 */
	List<SmsTaskDO> queryPaymentSmsTask();
	
	/**
	 * 查询支付分析
	 * @param date
	 * @param shops
	 * @return
	 */
	PaymentStatisticJson queryPaymentStatistic(Date date,Long[] shops);
	
	/**
	 * 更新订单状态
	 * @param id
	 * @param customerId
	 * @param employeeId
	 * @param status
	 * @param charge
	 * @param received
	 * @param paymentStatus
	 * @return
	 */
	PaymentQueryJson updatePaymentStatus(Long id,Long customerId,Long employeeId, String status, Double charge, Double received, String paymentStatus);
	
	/**
	 * 更新订单评论
	 * @param id
	 * @param comment
	 * @return
	 */
	PaymentQueryJson updatePaymentComment(Long id,String comment);
	
	/**
	 * 更新短信状态
	 * @param id
	 * @param smsStatus
	 * @return
	 */
	PaymentQueryJson updatePaymentSmsStatus(Long id,String smsStatus);
	
	/**
	 * 更新支付状态
	 * @param id
	 * @param paymentStatus
	 * @param paymentCode
	 * @param paymentResp
	 * @return
	 */
	PaymentQueryJson updatePaymentStatus(Long id,String paymentStatus,String paymentCode,String paymentResp);
	
	/**
	 * 更新订单地址标记
	 * @param id
	 * @param charge
	 * @param received
	 * @param lng
	 * @param lat
	 * @param mapAdd
	 * @return
	 */
	PaymentQueryJson updatePaymentMapInfo(Long id,Double charge, Double received, Double lng, Double lat, String mapAdd);
	
	/**
	 * 获取一笔支付
	 * @param paymentId
	 * @return
	 */
	PaymentVO getPaymentById(Long paymentId);
	
	/**
	 * 计算外卖费
	 * @param loginUser 登录的用户
	 * @param list 购物车的商店的集合
	 * @param defaultCharge 默认的外卖费
	 * @param zhaileShopId 宅乐网店铺的ID
	 * @param firstFree 是否首单免费
	 * @param distanceMap 店铺ID和距离的MAP
	 * @return 计算外卖费
	 */
	Double getCharge(ChargeRequestVO requestVO);
	
	/**
	 * 计算合计（不包含外卖费）
	 * @param list
	 * @return
	 */
	Double getTotalValue(List<ShoppingCarVO> list);
	
	/**
	 * 获取订单
	 * @param orderQueryCondition
	 * @return
	 */
	List<OrderVO> getOrderVO(OrderQueryCondition orderQueryCondition);
	
	/**
	 * 查询订单报告
	 * @param orderQueryCondition
	 * @return
	 */
	List<OrderQueryJson> getOrderReport(OrderQueryCondition orderQueryCondition);
}	
