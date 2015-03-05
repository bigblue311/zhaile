package com.zhaile.admin.webpage.screen;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.alibaba.citrus.turbine.dataresolver.Params;
import com.alibaba.fastjson.JSONObject;
import com.victor.framework.dal.basic.Paging;
import com.zhaile.admin.webpage.filter.AdminLoginFilter;
import com.zhaile.biz.web.form.ShopQueryFormBean;
import com.zhaile.biz.web.manager.ShopManager;
import com.zhaile.dal.enumerate.ShopCategoryEnum;
import com.zhaile.dal.model.ShopDO;
import com.zhaile.dal.query.condition.ShopQueryCondition;

public class Shop extends AdminLoginFilter {
	
	@Autowired
	private ShopManager shopManager;
	
	public void execute(@Params ShopQueryFormBean shopQuery,Navigator nav, Context context) throws IOException{
		boolean access = super.doFilter("shop",context,nav);
		if(!access){
			return;
		}
		
		ShopQueryCondition queryCondition = shopQuery.getShopQueryCondition();
		Paging<ShopDO> shopList = Paging.emptyPage();
		
		if(super.isZhaile()){
			shopList = shopManager.getFull(queryCondition);
		} else {
			if(super.getShopIds()!=null){
				queryCondition.ids(super.getShopIds());
				shopList = shopManager.getFull(queryCondition);
			}
		} 
		context.put("shopCategoryEnum", ShopCategoryEnum.getAll());
		context.put("shopList", JSONObject.toJSONString(shopList.getData()));
		context.put("paging", shopList);
		context.put("shopQuery", shopQuery);
	}
}
