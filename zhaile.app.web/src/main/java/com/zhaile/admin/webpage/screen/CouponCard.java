package com.zhaile.admin.webpage.screen;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.zhaile.admin.webpage.filter.AdminLoginFilter;
import com.zhaile.biz.web.manager.CouponManager;
import com.zhaile.biz.web.model.CouponCardVO;

public class CouponCard extends AdminLoginFilter {
	
	@Autowired
	private CouponManager couponManager;
	
	public void execute(@Param("id") Long id,
						@Param(name="page", defaultValue="1") Integer page,
						Navigator nav, 
						Context context) throws IOException{
		boolean access = super.doFilter("coupon",context,nav);
		if(!access){
			return;
		}
		
		CouponCardVO couponCardVO = couponManager.getCouponCardVOById(id);
		context.put("coupon", couponCardVO);
	}
}
