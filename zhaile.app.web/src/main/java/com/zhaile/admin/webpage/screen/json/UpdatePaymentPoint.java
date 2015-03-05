package com.zhaile.admin.webpage.screen.json;

import java.io.IOException;

import java.util.Collection;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.victor.framework.common.shared.Split;
import com.zhaile.admin.webpage.filter.AdminLoginFilter;
import com.zhaile.biz.common.Cache;
import com.zhaile.biz.web.json.PaymentQueryJson;
import com.zhaile.biz.web.manager.CustomerManager;
import com.zhaile.biz.web.manager.ShopManager;
import com.zhaile.biz.web.manager.TransactionManager;
import com.zhaile.biz.web.model.ChargeRequestVO;
import com.zhaile.dal.model.CustomerDO;
import com.zhaile.dal.model.MapPoiDO;
import com.zhaile.dal.model.PaymentDO;
import com.zhaile.dal.model.ShopDO;
import com.zhaile.dal.model.SystemConfigDO;
import com.zhaile.dal.cache.SystemConfigCache;
import com.zhaile.dal.cache.key.SystemConfigCacheKey;

public class UpdatePaymentPoint extends AdminLoginFilter {
	@Autowired
	private Cache cache;
	
	@Autowired
	private TransactionManager transactionManager;
	
	@Autowired
	private ShopManager shopManager;
	
	@Autowired
	private CustomerManager customerManager;
	
	public PaymentQueryJson execute(@Param("id") Long id,
						@Param("lng") Double lng,
						@Param("lat") Double lat,
						@Param("mapAdd") String mapAdd,
						@Param("distanceMap") String distanceMap) throws IOException {
		PaymentDO payment = transactionManager.getPaymentById(id).getPayment();
		
		ChargeRequestVO chargeRequestVO = prepareRequestVO(payment.getCustomerId(),distanceMap);
		Double charge = transactionManager.getCharge(chargeRequestVO);
		PaymentQueryJson queryJson = transactionManager.updatePaymentMapInfo(id, charge, payment.getNetpay()+charge, lng, lat, mapAdd);
		
		MapPoiDO mapPoiDO = new MapPoiDO();
		mapPoiDO.setLng(lng);
		mapPoiDO.setLat(lat);
		mapPoiDO.setIp(payment.getIp());
		mapPoiDO.setCustomerId(payment.getCustomerId());
		customerManager.updateMapPoi(mapPoiDO);
		
		return queryJson;
    }
	
	private Collection<ShopDO> generateShopList(String distanceMap){
		String[] shops = distanceMap.split(Split.分号);
		Collection<ShopDO> shopList = Lists.newArrayList();
		for(String shopSplit : shops){
			try {
				Long shopId = Long.parseLong(shopSplit.split(Split.波浪)[0]);
				ShopDO shopDO = shopManager.getShopById(shopId);
				shopList.add(shopDO);
			} catch (Exception e) {
				continue;
			}
		}
		return shopList;
	}
	
	private Map<Long,Double> generateDistanceMap(String distanceMap){
		String[] shops = distanceMap.split(Split.分号);
		Map<Long,Double> resultMap = Maps.newHashMap();
		for(String shopSplit : shops){
			try {
				Long shopId = Long.parseLong(shopSplit.split(Split.波浪)[0]);
				Double distance = Double.parseDouble(shopSplit.split(Split.波浪)[1]);
				resultMap.put(shopId, distance);
			} catch (Exception e) {
				continue;
			}
		}
		return resultMap;
	}
	
	private ChargeRequestVO prepareRequestVO(Long userId,String distanceMap){
		SystemConfigCache systemCache = cache.getSystemConfigcache();
		SystemConfigDO defaultCharge = systemCache.getConfig(SystemConfigCacheKey.DEFAULT_CHARGE);
		int chargePerShop = Integer.parseInt(defaultCharge.getValue());
		SystemConfigDO zhaileShopId = systemCache.getConfig(SystemConfigCacheKey.ZHAILE_SHOP_ID);
		Long zhaileShopIdLong = Long.parseLong(zhaileShopId.getValue());
		boolean firstFree = systemCache.getSwitch(SystemConfigCacheKey.FIRST_FREE_SWITCH);
		
		CustomerDO loginUser = customerManager.getCustomerById(userId);
		
		ChargeRequestVO requestVO = new ChargeRequestVO();
		requestVO.setLoginUser(loginUser);
		requestVO.setList(generateShopList(distanceMap));
		requestVO.setDefaultCharge(chargePerShop);
		requestVO.setZhaileShopId(zhaileShopIdLong);
		requestVO.setFirstFree(firstFree);
		requestVO.setDistanceMap(generateDistanceMap(distanceMap));
		return requestVO;
	}
}
