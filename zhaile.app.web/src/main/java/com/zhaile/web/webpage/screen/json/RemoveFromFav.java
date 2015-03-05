package com.zhaile.web.webpage.screen.json;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.victor.framework.common.shared.Result;
import com.zhaile.biz.web.manager.CustomerManager;
import com.zhaile.dal.model.CustomerDO;
import com.zhaile.web.common.screen.DefaultLayout;

public class RemoveFromFav extends DefaultLayout{
	
	@Autowired
	private CustomerManager CustomerManager;
	
	public Result<Boolean> execute(@Param("prodId") Long prodId,
						@Param("shopId") Long shopId
					   ) throws IOException{
		CustomerDO loginUser = super.getCustomerDO(null);
		
		Result<Boolean> result = null;
		
		if(loginUser==null) {
			result = Result.newInstance(false, "请先登录，从收藏夹中移除", false);
		} else {
			result = CustomerManager.removeFromFav(shopId, loginUser.getId());
		}
		return result;
	}
}
