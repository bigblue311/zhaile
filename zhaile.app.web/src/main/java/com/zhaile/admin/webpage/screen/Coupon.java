package com.zhaile.admin.webpage.screen;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.alibaba.citrus.turbine.dataresolver.Params;
import com.alibaba.fastjson.JSONObject;
import com.victor.framework.dal.basic.Paging;
import com.zhaile.admin.webpage.filter.AdminLoginFilter;
import com.zhaile.biz.web.form.CouponMetaQueryFormBean;
import com.zhaile.biz.web.manager.CouponManager;
import com.zhaile.dal.enumerate.DateUnitEnum;
import com.zhaile.dal.model.CouponMetaDO;
import com.zhaile.dal.query.condition.CouponMetaQueryCondition;

public class Coupon extends AdminLoginFilter {
	
	@Autowired
	private CouponManager couponManager;
	
	public void execute(@Params CouponMetaQueryFormBean couponQuery,Navigator nav, Context context) throws IOException{
		boolean access = super.doFilter("coupon",context,nav);
		if(!access){
			return;
		}
		
		CouponMetaQueryCondition queryCondition = couponQuery.getCouponMetaQueryCondition();
		Paging<CouponMetaDO> couponList = Paging.emptyPage();
		
		if(super.isZhaile()){
			couponList = couponManager.getCouponMeta(queryCondition);
		} else {
			if(super.getShopIds()!=null){
				queryCondition.shopIds(super.getShopIds());
				couponList = couponManager.getCouponMeta(queryCondition);
			}
		}
		context.put("DateUnitEnum", DateUnitEnum.getAll());
		context.put("couponList", JSONObject.toJSONString(couponList.getData()));
		context.put("paging", couponList);
		context.put("couponQuery", couponQuery);
	}
}
