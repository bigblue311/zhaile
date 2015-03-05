package com.zhaile.web.webpage.screen.json;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.victor.framework.common.shared.Result;
import com.zhaile.biz.common.Cache;
import com.zhaile.biz.web.manager.CustomerManager;
import com.zhaile.biz.web.manager.ShopManager;
import com.zhaile.dal.enumerate.EnableEnum;
import com.zhaile.dal.model.CustomerDO;
import com.zhaile.dal.model.ProductDO;
import com.zhaile.dal.model.ShopDO;
import com.zhaile.web.common.screen.DefaultLayout;

public class AddToFav extends DefaultLayout{
	
	@Autowired
	private Cache cache;
	
	@Autowired
	private CustomerManager CustomerManager;
	
	@Autowired
	private ShopManager shopManager;
	
	public Result<Boolean> execute(@Param("prodId") Long prodId,
						@Param("shopId") Long shopId
					   ) throws IOException{
		CustomerDO loginUser = super.getCustomerDO(null);
		
		Result<Boolean> result = null;
		
		ShopDO shop = shopManager.getShopById(shopId);
		
		if(loginUser==null) {
			result = Result.newInstance(false, "请先登录，再添加到收藏夹", false);
		} else {
			if(shop!=null && shop.getEnable().equals(EnableEnum.有效.getCode())){
				if(prodId!=null) {
					ProductDO prod = cache.getCategorycache().getProd(prodId);
					if(prod!=null && prod.getEnable().equals(EnableEnum.有效.getCode())) {
						Result<Long> addRes = CustomerManager.addToFav(shopId, prodId, loginUser.getId());
						result = Result.newInstance(addRes.isSuccess(), "添加到收藏夹成功功能", addRes.isSuccess());
					} else {
						result = Result.newInstance(false, "无效的商品", false);
					}
				} else {
					Result<Long> addRes = CustomerManager.addToFav(shopId, prodId, loginUser.getId());
					result = Result.newInstance(addRes.isSuccess(), "添加到收藏夹成功功能", addRes.isSuccess());
				}
			} else {
				result = Result.newInstance(false, "无效的店铺", false);
			}
		}
		return result;
	}
}
