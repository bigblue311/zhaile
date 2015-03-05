package com.zhaile.biz.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;
import com.victor.framework.common.tools.DateTools;
import com.zhaile.biz.web.json.PaymentQueryJson;
import com.zhaile.dal.cache.AdvertisementCache;
import com.zhaile.dal.cache.CategoryCache;
import com.zhaile.dal.cache.EmployeeCache;
import com.zhaile.dal.cache.PriceRangeCache;
import com.zhaile.dal.cache.SystemConfigCache;
import com.zhaile.dal.cache.SystemContentCache;
import com.zhaile.dal.cache.key.SystemConfigCacheKey;
import com.zhaile.dal.model.AdvertisementDO;
import com.zhaile.dal.model.EmployeeDO;
import com.zhaile.dal.model.ProductDO;
import com.zhaile.dal.model.ShopDO;
import com.zhaile.dal.model.SystemConfigDO;
import com.zhaile.dal.model.SystemContentDO;

public class Cache implements InitializingBean {
	@Autowired
	private SystemConfigCache systemConfigCache;
	
	@Autowired
	private SystemContentCache systemContentCache;
	
	@Autowired
	private CategoryCache categoryCache;
	
	@Autowired
	private EmployeeCache employeeCache;
	
	@Autowired
	private AdvertisementCache advertisementCache;
	
	@Autowired
	private PriceRangeCache priceRangeCache;
	
	
	private final Map<Long,PaymentQueryJson> paymentStatusCache = new HashMap<Long,PaymentQueryJson>(); 
	private final List<Long> loginedUser = Lists.newArrayList();
	
	private String timestamp;
	
	public void login(Long id){
		if(id!=null){
			loginedUser.add(id);
		}
	}
	
	public void logout(Long id){
		if(id!=null && loginedUser.contains(id)){
			loginedUser.remove(id);
		}
	}
	
	public boolean isLogined(Long id){
		return loginedUser.contains(id);
	}
	
	public PaymentQueryJson getPaymentStatus(Long id){
		if(id!=null){
			return paymentStatusCache.get(id);
		}
		return null;
	}
	
	public void setPaymentStatus(PaymentQueryJson payment){
		paymentStatusCache.put(payment.getId(), payment);
	}

	public SystemConfigCache getSystemConfigcache() {
		return systemConfigCache;
	}
	
	public EmployeeCache getEmployeecache() {
		return employeeCache;
	}

	public AdvertisementCache getAdvertisementcache() {
		return advertisementCache;
	}

	public CategoryCache getCategorycache() {
		return categoryCache;
	}

	public PriceRangeCache getPriceRangecache() {
		return priceRangeCache;
	}

	public SystemContentCache getSystemcontentcache() {
		return systemContentCache;
	}

	public Map<Long, PaymentQueryJson> getPaymentstatuscache() {
		return paymentStatusCache;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void reload(){
		timestamp = DateTools.DateToString(DateTools.today());
		systemConfigCache.reload();
		categoryCache.reload();
		employeeCache.reload();
		advertisementCache.reload();
		systemContentCache.reload();
	}
	
	public void update2DB(SystemConfigDO systemConfigDO){
		systemConfigCache.updateDB(systemConfigDO);
	}
	
	public void update2DB(SystemContentDO systemContentDO){
		systemContentCache.updateDB(systemContentDO);
	}
	
	public void update2DB(EmployeeDO employeeDO){
		employeeCache.updateDB(employeeDO);
	}
	
	public void insert2DB(EmployeeDO employeeDO){
		employeeCache.insertDB(employeeDO);
	}
	
	public void deleteEmployee(Long id){
		employeeCache.deleteById(id);
	}
	
	public AdvertisementDO getValidLucky(){
		AdvertisementDO resultAd = advertisementCache.getAd(40);
		for(int position=40;position<45;position++){
			resultAd = advertisementCache.getAd(position);
			if(resultAd.isValid()){
				return resultAd;
			}
		}
		return resultAd;
	}
	
	public AdvertisementDO getValidTop(){
		int[] tops = {1,66,67,68,69};
		AdvertisementDO resultAd = advertisementCache.getAd(1);
		for(int position : tops){
			resultAd = advertisementCache.getAd(position);
			if(resultAd.isValid()){
				return resultAd;
			}
		}
		return resultAd;
	}
	
	public Long[] getValidLuckyCategoryId(){
		String type = getValidLucky().getType();
		String[] types = type.split(",");
		List<Long> categoryIds = Lists.newArrayList();
		for(String category:types) {
			Long categoryId = Long.parseLong(category);
			categoryIds.add(categoryId);
		}
		return categoryIds.toArray(new Long[categoryIds.size()]);
	}
	
	public boolean getSwitch(String name) {
		return systemConfigCache.getSwitch(name);
	}
	
	public boolean isProdValid(Long prodId){
		if(getSwitch(SystemConfigCacheKey.DEBUG_MODE_SWITCH)){
			return true;
		}
		if(prodId == null) {
			return false;
		}
		ProductDO prod = categoryCache.getProd(prodId);
		if(prod == null){
			return false;
		}
		if(isShopValid(prod.getShopId())){
			return prod.isValid();
		}
		return false;
	}
	
	public boolean isProdPromot(Long prodId, boolean logined){
		if(!getSwitch(SystemConfigCacheKey.ZHAILE_PROMOTION_SWITCH)){
			return false;
		}
		if(prodId == null) {
			return false;
		}
		ProductDO prod = categoryCache.getProd(prodId);
		if(!logined){
			return false;
		}
		try {
			String[] ZHAILE_PROMOTION_ITEM = getSystemConfigcache()
					.getConfig(SystemConfigCacheKey.ZHAILE_PROMOTION_ITEM)
					.getValue().split(",");
			for (int i = 0; i < ZHAILE_PROMOTION_ITEM.length; i++) {
				Long id = Long.parseLong(ZHAILE_PROMOTION_ITEM[i]);
				if (id.intValue() == prod.getId().intValue()) {
					return true;
				}
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}
	
	public boolean isShopValid(Long shopId){
		if(getSwitch(SystemConfigCacheKey.DEBUG_MODE_SWITCH)){
			return true;
		}
		if(shopId == null) {
			return false;
		}
		ShopDO shop = categoryCache.getShop(shopId.toString());
		if(shop==null){
			return false;
		}
		boolean zhaileValid = true;
		boolean shopValid = true;
		boolean selfDelivery = shop.getCharge() < 3d;
		String zhaileOpen = "09:00:00";
		String zhaileClose = "21:00:00";
		//计算店铺是否有效
		try {
			shopValid = shop.isValid();
		} catch (Exception e) {
			shopValid = true;
		}
		//计算宅乐是否有效
		try {
			zhaileOpen = systemConfigCache.getConfig(SystemConfigCacheKey.ZHAILE_OPEN).getValue();
			zhaileClose = systemConfigCache.getConfig(SystemConfigCacheKey.ZHAILE_CLOSE).getValue();
			zhaileValid = DateTools.inTime(zhaileOpen, zhaileClose);
		} catch (Exception e) {
			zhaileOpen = "09:00:00";
			zhaileClose = "21:00:00";
			try {
				zhaileValid = DateTools.inTime(zhaileOpen, zhaileClose);
			} catch (Exception e1) {
				zhaileValid = true;
			}
		}
		if(selfDelivery) {
			return shopValid;
		} else {
			return shopValid && zhaileValid;
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		reload();
	}
}
