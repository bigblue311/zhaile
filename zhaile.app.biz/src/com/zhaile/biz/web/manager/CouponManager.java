package com.zhaile.biz.web.manager;

import java.util.List;

import com.victor.framework.dal.basic.Paging;
import com.zhaile.biz.web.model.CouponCardVO;
import com.zhaile.biz.web.model.CouponMetaVO;
import com.zhaile.dal.model.CouponCardDO;
import com.zhaile.dal.model.CouponMetaDO;
import com.zhaile.dal.query.condition.CouponCardQueryCondition;
import com.zhaile.dal.query.condition.CouponMetaQueryCondition;

public interface CouponManager {
	
	/**
	 * 创建一个卡券
	 * @param couponListDO
	 */
	void createCouponMeta(CouponMetaDO couponMetaDO);
	
	/**
	 * 创建一个用户卡实例
	 * @param couponMetaId
	 * @param customerId
	 */
	void createCouponCard(Long couponMetaId, Long customerId);
	
	/**
	 * 更新一个卡券内容
	 * @param couponListDO
	 */
	void updateCouponMeta(CouponMetaDO couponMetaDO);
	void updateCouponCard(CouponCardDO couponCardDO);
	
	/**
	 * 开启或关闭一个卡券
	 * @param id
	 */
	void enableCouponMeta(Long id);
	void disableCouponMeta(Long id);
	
	/**
	 * 开启或关闭一个卡券
	 * @param id
	 */
	void enableCouponCard(Long id);
	void disableCouponCard(Long id);
	void lockCouponCard(Long id);
	void unlockCouponCard(Long id);
	
	/**
	 * 删除一个卡券
	 * @param id
	 */
	void deleteCouponMeta(Long id);
	
	/**
	 * 根据ID获取
	 * @param id
	 * @return
	 */
	CouponMetaDO getCouponMetaById(Long id);
	CouponMetaVO getCouponMetaVOById(Long id);
	
	CouponCardDO getCouponCardById(Long id);
	CouponCardVO getCouponCardVOById(Long id);
	
	/**
	 * 获取分页数据
	 * @param queryCondition
	 * @return
	 */
	Paging<CouponMetaDO> getCouponMeta(CouponMetaQueryCondition queryCondition);
	Paging<CouponCardVO> getCouponCardVO(CouponCardQueryCondition queryCondition);
	List<CouponCardDO> getCouponCardByCondition(CouponCardQueryCondition queryCondition);
}
