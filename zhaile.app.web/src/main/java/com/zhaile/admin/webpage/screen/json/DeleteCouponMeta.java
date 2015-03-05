package com.zhaile.admin.webpage.screen.json;

import java.io.IOException;
import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.zhaile.admin.webpage.filter.AdminLoginFilter;
import com.zhaile.biz.web.manager.CouponManager;

public class DeleteCouponMeta extends AdminLoginFilter {
	@Autowired
	private CouponManager couponManager;
	
	public Boolean execute(@Param("id") Long id) throws IOException, ParseException {
		couponManager.deleteCouponMeta(id);
		return true;
    }
}
