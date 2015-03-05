package com.zhaile.biz.web.manager;

import java.util.Collection;
import java.util.List;

import com.zhaile.biz.web.json.PaymentQueryJson;
import com.zhaile.biz.web.model.ShoppingCarVO;
import com.zhaile.dal.model.ShopDO;
import com.zhaile.dal.model.SmsTaskDO;
import com.zhaile.dal.query.condition.SmsTaskQueryCondition;

public interface SmsTaskManager {
	
	/**
	 * 准备客户订单短信
	 * @param paymentJson
	 */
	void prepareSmsTask(PaymentQueryJson paymentJson);
	
	/**
	 * 准备店铺小票短信
	 * @param list
	 * @param smsReceiptShopList
	 * @param contact
	 * @param comment
	 */
	void prepareShopReceipt(List<ShoppingCarVO> list,Collection<ShopDO> shopList,String contact,String comment);
	
	/**
	 * 更新短信发送状态
	 * @param id
	 * @param status
	 */
	boolean updateSmsStatus(Long id, String status);
	
	/**
	 * 重试短信任务
	 * @param id
	 */
	boolean retry(Long id);
	
	/**
	 * 获取待处理的任务
	 */
	List<SmsTaskDO> querySmsTaskPending();
	
	/**
	 * 根据查询条件获取所有短信任务
	 * @param queryCondition
	 */
	List<SmsTaskDO> querySmsTasks(SmsTaskQueryCondition queryCondition);
}
