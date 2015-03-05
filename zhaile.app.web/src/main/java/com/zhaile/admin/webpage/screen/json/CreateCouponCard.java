package com.zhaile.admin.webpage.screen.json;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.victor.framework.common.shared.Result;
import com.victor.framework.common.tools.CollectionTools;
import com.zhaile.admin.webpage.filter.AdminLoginFilter;
import com.zhaile.biz.web.manager.CouponManager;
import com.zhaile.biz.web.manager.CustomerManager;
import com.zhaile.dal.enumerate.EnableEnum;
import com.zhaile.dal.model.CouponCardDO;
import com.zhaile.dal.model.CouponMetaDO;
import com.zhaile.dal.query.condition.CouponCardQueryCondition;

public class CreateCouponCard extends AdminLoginFilter {
	@Autowired
	private CouponManager couponManager;
	
	@Autowired
	private CustomerManager customerManager;
	
	public Result<String> execute(@Param("customerId") Long customerId,
						   @Param("couponMetaId") Long couponMetaId){
		if(customerId == null || couponMetaId == null) {
			return Result.newInstance("非法的参数", "非法的参数", false);
		}
		if(customerManager.getCustomerById(customerId) == null){
			return Result.newInstance("用户不存在", "用户不存在", false);
		}
		CouponMetaDO couponMetaDO = couponManager.getCouponMetaById(couponMetaId);
		if(couponMetaDO == null){
			return Result.newInstance("卡券不存在", "卡券不存在", false);
		}
		CouponCardQueryCondition condition = new CouponCardQueryCondition();
		condition.valid(EnableEnum.有效.getCode()).metaId(couponMetaId);
		List<CouponCardDO> list = couponManager.getCouponCardByCondition(condition);
		if(CollectionTools.isNotEmpty(list)){
			int size = list.size();
			if(size >= couponMetaDO.getTotal()){
				return Result.newInstance("卡券已经发完了", "卡券已经发完了", false);
			}
		}
		couponManager.createCouponCard(couponMetaId, customerId);
		return Result.newInstance("发送成功", "发送成功", true);
    }
}
