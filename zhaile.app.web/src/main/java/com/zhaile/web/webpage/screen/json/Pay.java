package com.zhaile.web.webpage.screen.json;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alibaba.citrus.turbine.dataresolver.Params;
import com.victor.framework.common.shared.Result;
import com.victor.framework.common.tools.CollectionTools;
import com.zhaile.biz.common.Cache;
import com.zhaile.biz.web.json.PaymentQueryJson;
import com.zhaile.biz.web.manager.FlashGoManager;
import com.zhaile.biz.web.manager.SmsTaskManager;
import com.zhaile.biz.web.manager.TransactionManager;
import com.zhaile.biz.web.model.ChargeRequestVO;
import com.zhaile.biz.web.model.ShoppingCarVO;
import com.zhaile.biz.web.model.TodayEventVO;
import com.zhaile.dal.cache.SystemConfigCache;
import com.zhaile.dal.cache.key.SystemConfigCacheKey;
import com.zhaile.dal.enumerate.SourceEnum;
import com.zhaile.dal.model.CustomerDO;
import com.zhaile.dal.model.ShopDO;
import com.zhaile.dal.model.SystemConfigDO;
import com.zhaile.dal.model.vo.PaymentFormBean;
import com.zhaile.web.common.screen.DefaultLayout;

public class Pay extends DefaultLayout {
	
	@Autowired
    private HttpSession session;
	
	@Autowired
	private Cache cache;
	
	@Autowired
	private TransactionManager transactionManager;
	
	@Autowired
	private SmsTaskManager smsTaskManager;
	
	@Autowired
	private FlashGoManager flashGoManager;
		
	public Result<Boolean> execute(
			@Params PaymentFormBean paymentFormBean,
			@Param("userId") Long userId
			) throws IOException{
		CustomerDO loginUser = super.getCustomerDO(userId);
		List<ShoppingCarVO> list = super.getShoppingCarList(userId);
		Collection<ShopDO> shopList = super.getShopListFromShoppingCar(userId);
		
		Double lng = super.getLng();
		Double lat = super.getLat();
		
		Long customerId = null;
		boolean success = false;
		String msg = "";
		if(loginUser!=null){
			customerId = loginUser.getId();
		} else {
			if(!paymentFormBean.getSource().equals(SourceEnum.宅乐网.getCode())){
				success = false;
				msg = "非法的请求";
				return Result.newInstance(success, msg, success);
			}
		}
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
		paymentFormBean.setCharge(charge);
		paymentFormBean.setLng(lng);
		paymentFormBean.setLat(lat);
		Result<PaymentQueryJson> paymentStatus = transactionManager.addPayment(paymentFormBean, list, customerId);
		if(paymentStatus.isSuccess()){
			success = true;
			checkFlashGo(list);
			session.removeAttribute("shoppingCar");
			cache.setPaymentStatus(paymentStatus.getDataObject());
			session.setAttribute("paymentStatusId", paymentStatus.getDataObject().getId());
			smsTaskManager.prepareShopReceipt(list,shopList,paymentFormBean.getContact(),paymentFormBean.getComment());
		} else {
			success = false;
		}
		msg = paymentStatus.getMessage();
		return Result.newInstance(success, msg, success);
	}
	
	private void checkFlashGo(List<ShoppingCarVO> list){
		TodayEventVO todayEventVO = new TodayEventVO();
		todayEventVO.setFlashGo(flashGoManager.getToday());
		if(!todayEventVO.getEnable()){
			return;
		}
		if(CollectionTools.isEmpty(list)){
			return;
		}
		for(ShoppingCarVO shoppingCarVO : list){
			if(shoppingCarVO.getIsFlashGo()){
				int quantity = shoppingCarVO.getShopCar().getQuantity();
				for(int i=0;i<quantity;i++){
					flashGoManager.sold(todayEventVO.getFlashGo().getId());
				}
			}
		}
	}
}
