package com.zhaile.admin.webpage.screen;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alibaba.fastjson.JSONObject;
import com.victor.framework.dal.basic.Paging;
import com.zhaile.admin.webpage.filter.AdminLoginFilter;
import com.zhaile.biz.web.manager.CouponManager;
import com.zhaile.biz.web.model.CouponCardVO;
import com.zhaile.biz.web.model.CouponMetaVO;
import com.zhaile.dal.enumerate.DateUnitEnum;
import com.zhaile.dal.query.condition.CouponCardQueryCondition;

public class CouponDetail extends AdminLoginFilter {
	
	@Autowired
	private CouponManager couponManager;
	
	public void execute(@Param("id") Long id,
						@Param(name="customerName", defaultValue="") String customerName,
						@Param(name="customerPhone", defaultValue="") String customerPhone,
						@Param(name="page", defaultValue="1") Integer page,
						Navigator nav, 
						Context context) throws IOException{
		boolean access = super.doFilter("coupon",context,nav);
		if(!access){
			return;
		}
		
		CouponMetaVO couponMetaVO = couponManager.getCouponMetaVOById(id);
		context.put("DateUnitEnum", DateUnitEnum.getAll());
		context.put("coupon", couponMetaVO);
		int pageSize = 20;
		CouponCardQueryCondition queryCondition = new CouponCardQueryCondition();
		queryCondition.metaId(id).customerName(customerName).customerPhone(customerPhone);
		queryCondition.pageSize(pageSize).start((page-1)*pageSize);
		Paging<CouponCardVO> couponList = Paging.emptyPage();
		if(super.isZhaile()){
			couponList = couponManager.getCouponCardVO(queryCondition);
		} else {
			if(super.getShopIds()!=null){
				queryCondition.shopIds(super.getShopIds());
				couponList = couponManager.getCouponCardVO(queryCondition);
			}
		}
		context.put("couponList", JSONObject.toJSONString(couponList.getData()));
		context.put("paging", couponList);
		context.put("id", id);
		context.put("customerName", customerName);
		context.put("customerPhone", customerPhone);
		context.put("page", page);
	}
}
