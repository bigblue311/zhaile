package com.zhaile.web.webpage.screen;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Context;
import com.google.common.collect.Lists;
import com.victor.framework.common.tools.CollectionTools;
import com.zhaile.biz.common.Cache;
import com.zhaile.biz.web.manager.TransactionManager;
import com.zhaile.biz.web.model.ChargeRequestVO;
import com.zhaile.biz.web.model.ShoppingCarVO;
import com.zhaile.dal.cache.SystemConfigCache;
import com.zhaile.dal.cache.key.SystemConfigCacheKey;
import com.zhaile.dal.model.CustomerDO;
import com.zhaile.dal.model.PeopleContactDO;
import com.zhaile.dal.model.ProductDO;
import com.zhaile.dal.model.ShopDO;
import com.zhaile.dal.model.SystemConfigDO;
import com.zhaile.search.engine.db.DBSearchFacade;
import com.zhaile.web.common.screen.DefaultLayout;

public class Order extends DefaultLayout {
	
	@Autowired
	private Cache cache;
	
	@Autowired
	private TransactionManager transactionManager;
	
	@Autowired
	private DBSearchFacade searchFacade;
	
	public void execute(HttpServletRequest request, Context context){
		CustomerDO loginUser = super.getCustomerDO(null);
		context.put("title", "富阳宅乐网-我的购物车");
		super.load(context,null);
		putZhaileItems(context);
		
		List<ShoppingCarVO> list = super.getShoppingCarList(null);
		context.put("shoppingCar",list);
		assambleCharge(request,context,loginUser,list);
		
		List<PeopleContactDO> clist = super.getPeopleContactList(null);
		context.put("peopleContact",clist);
	}
	
	private void putZhaileItems(Context context){
		
		String ZHAILE_SHOP_ID = cache.getSystemConfigcache().getConfig(SystemConfigCacheKey.ZHAILE_SHOP_ID).getValue();
		String ZHAILE_FRUIT_ID = cache.getSystemConfigcache().getConfig(SystemConfigCacheKey.ZHAILE_FRUIT).getValue();
		String ZHAILE_DRINK_ID = cache.getSystemConfigcache().getConfig(SystemConfigCacheKey.ZHAILE_DRINK).getValue();
		
		@SuppressWarnings("unchecked")
		List<ProductDO> zhaileFruit = (List<ProductDO>) searchFacade.findProduct(Long.parseLong(ZHAILE_SHOP_ID),Long.parseLong(ZHAILE_FRUIT_ID)).get(1).getDataObject();
		@SuppressWarnings("unchecked")
		List<ProductDO> zhaileDrink = (List<ProductDO>) searchFacade.findProduct(Long.parseLong(ZHAILE_SHOP_ID),Long.parseLong(ZHAILE_DRINK_ID)).get(1).getDataObject();
		
		if(cache.getSwitch(SystemConfigCacheKey.ZHAILE_PROMOTION_SWITCH)){
			//如果有促销活动
			String[] ZHAILE_PROMOTION_ITEM = cache.getSystemConfigcache().getConfig(SystemConfigCacheKey.ZHAILE_PROMOTION_ITEM).getValue().split(",");
			Long[] promotionItems = new Long[ZHAILE_PROMOTION_ITEM.length];
			for(int i=0;i<ZHAILE_PROMOTION_ITEM.length;i++) {
				promotionItems[i] = Long.parseLong(ZHAILE_PROMOTION_ITEM[i]);
			}
			
			@SuppressWarnings("unchecked")
			List<ProductDO> zhailePromotion = (List<ProductDO>) searchFacade.getProduct(promotionItems).get(0).getDataObject();
			
			context.put("zhaileFruit", zhaileFruit);
			context.put("zhaileDrink", combine(zhaileDrink,zhailePromotion));
		} else {
			context.put("zhaileFruit", zhaileFruit);
			context.put("zhaileDrink", zhaileDrink);
		}
	}
	
	private List<ProductDO> combine(List<ProductDO> items, List<ProductDO> promotions){
		List<ProductDO> result = Lists.newArrayList();
		for(ProductDO promotion : promotions) {
			boolean find = false;
			for(ProductDO item : items) {
				if(item.getId().intValue() == promotion.getId().intValue()) {
					result.add(promotion);
					find = true;
					break;
				}
			}
			if(!find){
				result.add(promotion);
			}
		}
		for(ProductDO item : items) {
			boolean find = false;
			for(ProductDO promotion : promotions) {
				if(item.getId().intValue() == promotion.getId().intValue()) {
					find = true;
					break;
				}
			}
			if(!find){
				result.add(item);
			}
		}
		return result;
	}
	
	private void assambleCharge(HttpServletRequest request, Context context, CustomerDO loginUser, List<ShoppingCarVO> list){
		SystemConfigCache systemCache = cache.getSystemConfigcache();
		SystemConfigDO defaultCharge = systemCache.getConfig(SystemConfigCacheKey.DEFAULT_CHARGE);
		int chargePerShop = Integer.parseInt(defaultCharge.getValue());
		SystemConfigDO zhaileShopId = systemCache.getConfig(SystemConfigCacheKey.ZHAILE_SHOP_ID);
		boolean firstFree = systemCache.getSwitch(SystemConfigCacheKey.FIRST_FREE_SWITCH);
		Long zhaileShopIdLong = Long.parseLong(zhaileShopId.getValue());
		Collection<ShopDO> shopList = super.getShopListFromShoppingCar(null);
		Map<Long, Double> distanceMap = super.generateDistanceMap(shopList);
		
		ChargeRequestVO requestVO = new ChargeRequestVO();
		requestVO.setLoginUser(loginUser);
		requestVO.setList(shopList);
		requestVO.setDefaultCharge(chargePerShop);
		requestVO.setZhaileShopId(zhaileShopIdLong);
		requestVO.setFirstFree(firstFree);
		requestVO.setDistanceMap(distanceMap);
		
		double charge = transactionManager.getCharge(requestVO);
		double sum = getTotalValue(list);
		double total = sum + charge;
		context.put("charge", charge);
		context.put("sum", sum);
		context.put("total", total);
	}
	
	private double getTotalValue(List<ShoppingCarVO> list){
		double total = 0d;
		if(CollectionTools.isNotEmpty(list)){
			for(ShoppingCarVO shoppingCarVO : list){
				double totalValue = shoppingCarVO.getTotalPrice();
				total = total + totalValue;
			}
		}
		return total;
	}
}
