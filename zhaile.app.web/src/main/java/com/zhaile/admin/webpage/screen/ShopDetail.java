package com.zhaile.admin.webpage.screen;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.zhaile.admin.webpage.filter.AdminLoginFilter;
import com.zhaile.biz.web.manager.ShopManager;
import com.zhaile.dal.dao.ProductDAO;
import com.zhaile.dal.enumerate.ShopCategoryEnum;
import com.zhaile.dal.model.ProductDO;

public class ShopDetail extends AdminLoginFilter {
	
	@Autowired
	private ShopManager shopManager;
	
	@Autowired
	private ProductDAO productDAO;
	
	public void execute(@Param("id") Long id,
						Navigator nav, Context context) throws IOException{
		boolean access = super.doFilter("shop",context,nav);
		if(!access){
			return;
		}
		context.put("shopCategoryEnum", ShopCategoryEnum.getAll());
		context.put("shop", shopManager.getShopVOById(id));
		List<ProductDO> prodList = productDAO.getFullByShopId(id).getDataObject();
		context.put("prodList", prodList);
	}
}
