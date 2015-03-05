package com.zhaile.admin.webpage.screen.json;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.victor.framework.common.shared.Result;
import com.zhaile.admin.webpage.filter.AdminLoginFilter;
import com.zhaile.dal.dao.ProductDAO;

public class UpdateProdsStatus extends AdminLoginFilter {
	@Autowired
	private ProductDAO productDAO;
	
	public Result<Boolean> execute(@Param("shopId") Long shopId, @Param("enable") String enable) throws IOException {
		return productDAO.updateStatusByShopId(enable, shopId);
    }
}
