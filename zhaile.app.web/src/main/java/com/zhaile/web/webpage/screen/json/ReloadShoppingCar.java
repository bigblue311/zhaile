package com.zhaile.web.webpage.screen.json;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Context;
import com.zhaile.biz.common.Cache;
import com.zhaile.biz.web.manager.TransactionManager;
import com.zhaile.biz.web.model.ChargeRequestVO;
import com.zhaile.biz.web.model.ShoppingCarVO;
import com.zhaile.dal.cache.SystemConfigCache;
import com.zhaile.dal.cache.key.SystemConfigCacheKey;
import com.zhaile.dal.model.CustomerDO;
import com.zhaile.dal.model.ShopDO;
import com.zhaile.dal.model.SystemConfigDO;
import com.zhaile.web.common.screen.DefaultLayout;

public class ReloadShoppingCar extends DefaultLayout {
	
	@Autowired
	private Cache cache;

	@Autowired
	private TransactionManager transactionManager;
	
	public void execute(Context context) throws IOException{
		CustomerDO loginUser = super.getCustomerDO(null);
		List<ShoppingCarVO> list = super.getShoppingCarList(null);
		Collection<ShopDO> shopList = super.getShopListFromShoppingCar(null);
		list = transactionManager.checkValid(list);
		context.put("shoppingCarList", list);
		SystemConfigCache systemCache = cache.getSystemConfigcache();
		SystemConfigDO defaultCharge = systemCache.getConfig(SystemConfigCacheKey.DEFAULT_CHARGE);
		int chargePerShop = Integer.parseInt(defaultCharge.getValue());
		SystemConfigDO zhaileShopId = systemCache.getConfig(SystemConfigCacheKey.ZHAILE_SHOP_ID);
		Long zhaileShopIdLong = Long.parseLong(zhaileShopId.getValue());
		boolean firstFree = systemCache.getSwitch(SystemConfigCacheKey.FIRST_FREE_SWITCH);
		Map<Long, Double> distanceMap = super.generateDistanceMap(shopList);
		
		ChargeRequestVO requestVO = new ChargeRequestVO();
		requestVO.setLoginUser(loginUser);
		requestVO.setList(shopList);
		requestVO.setDefaultCharge(chargePerShop);
		requestVO.setZhaileShopId(zhaileShopIdLong);
		requestVO.setFirstFree(firstFree);
		requestVO.setDistanceMap(distanceMap);
		
		double charge = transactionManager.getCharge(requestVO);
		double sum = transactionManager.getTotalValue(list);
		double total = sum + charge;
		context.put("charge", charge);
		context.put("sum", sum);
		context.put("total", total);
	}
}
