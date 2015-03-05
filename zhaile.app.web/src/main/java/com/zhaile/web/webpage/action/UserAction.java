package com.zhaile.web.webpage.action;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Navigator;
import com.alibaba.citrus.turbine.dataresolver.FormGroup;
import com.victor.framework.common.shared.Result;
import com.victor.framework.common.tools.CollectionTools;
import com.zhaile.biz.common.Cache;
import com.zhaile.biz.web.form.LoginFromBean;
import com.zhaile.biz.web.form.RegistFormBean;
import com.zhaile.biz.web.json.PaymentQueryJson;
import com.zhaile.biz.web.manager.AdvertisementManager;
import com.zhaile.biz.web.manager.CustomerManager;
import com.zhaile.biz.web.manager.FlashGoManager;
import com.zhaile.biz.web.manager.SmsTaskManager;
import com.zhaile.biz.web.manager.TransactionManager;
import com.zhaile.biz.web.model.ChargeRequestVO;
import com.zhaile.biz.web.model.ShoppingCarVO;
import com.zhaile.biz.web.model.TodayEventVO;
import com.zhaile.dal.cache.SystemConfigCache;
import com.zhaile.dal.cache.key.SystemConfigCacheKey;
import com.zhaile.dal.enumerate.PaymentTypeEnum;
import com.zhaile.dal.model.CustomerCommentDO;
import com.zhaile.dal.model.CustomerDO;
import com.zhaile.dal.model.MapMarkerDO;
import com.zhaile.dal.model.PeopleContactDO;
import com.zhaile.dal.model.ShopDO;
import com.zhaile.dal.model.SystemConfigDO;
import com.zhaile.dal.model.vo.MapMarkerFormBean;
import com.zhaile.dal.model.vo.PaymentFormBean;
import com.zhaile.web.common.screen.DefaultLayout;

public class UserAction extends DefaultLayout{
	@Autowired
    private HttpSession session;
	
	@Autowired
	private Cache cache;
	
	@Autowired
	private CustomerManager customerManager;
	
	@Autowired
	private TransactionManager transactionManager;
	
	@Autowired
	private SmsTaskManager smsTaskManager;
	
	@Autowired
	private AdvertisementManager advertisementManager;
	
	@Autowired
	private FlashGoManager flashGoManager;
	
	private static final String SUCCESS = "T";
	private static final String FAILED = "F";
	
	public void doLogin(@FormGroup("login") LoginFromBean loginFromBean, Navigator nav) {
		CustomerDO customerDO = customerManager.login(loginFromBean);
		session.setAttribute("loginUser", customerDO);
		String success = SUCCESS;
		String msg = "";
		if(customerDO!=null){
			cache.login(customerDO.getId());
			success = SUCCESS;
			msg = "登录成功";
			Object sList = session.getAttribute("shoppingCar");
			if(sList!=null){
				@SuppressWarnings("unchecked")
				List<ShoppingCarVO> list = (List<ShoppingCarVO>)sList;
				for(ShoppingCarVO shoppingCarVO : list){
					transactionManager.addToShoppingCar(shoppingCarVO.getShopCar().getProdId(), shoppingCarVO.getShopCar().getQuantity(), customerDO.getId());
				}
				session.removeAttribute("shoppingCar");
			}
			Object cList = session.getAttribute("peopleContact");
			if(cList!=null){
				@SuppressWarnings("unchecked")
				List<PeopleContactDO> list = (List<PeopleContactDO>)cList;
				for(PeopleContactDO peopleContactDO : list){
					peopleContactDO.setForeignId(customerDO.getId());
					customerManager.addPeopleContact(peopleContactDO);
				}
				session.removeAttribute("peopleContact");
				nav.redirectTo("zhaile").withTarget("index.vm");
			}
		} else {
			success = FAILED;
			msg = "登录失败";
			nav.redirectTo("zhaile").withTarget("finish.vm").withParameter("success", success).withParameter("msg", msg);
		}
        
    }
	
	public void doLogout(Navigator nav){
		CustomerDO loginUser = (CustomerDO)session.getAttribute("loginUser");
		try {
			cache.logout(loginUser.getId());
		} finally{
			session.removeAttribute("loginUser");
			nav.redirectTo("zhaile").withTarget("index.vm");
		}
	}
	
	public void doRegist(@FormGroup("register") RegistFormBean registFormBean, Navigator nav){
		CustomerDO customerDO = customerManager.regist(registFormBean);
		session.setAttribute("loginUser", customerDO);
		String success = "F";
		String msg = "";
		if(customerDO!=null){
			success = SUCCESS;
			msg = "注册成功";
			nav.redirectTo("zhaile").withTarget("index.vm");
		} else {
			success = FAILED;
			msg = "注册失败";
			nav.redirectTo("zhaile").withTarget("finish.vm").withParameter("success", success).withParameter("msg", msg);
		}
	}
	
	public void doComment(@FormGroup("comment") CustomerCommentDO comment, Navigator nav){
		if(comment.getContent().contains("url") || comment.getContent().contains("http")){
			return;
		}
		comment.setIp(super.getIpAddr());
		customerManager.addComment(comment);
		//nav.redirectTo("zhaile").withTarget("product.vm").withParameter("id", comment.getProdId().toString());
	}
	
	public void doMark(@FormGroup("mapMarker") MapMarkerFormBean mapMarkerFormBean,Navigator nav){
		String success = "F";
		String msg = "";
		try {
			MapMarkerDO mapMarkerDO = mapMarkerFormBean.toDO();
			if(mapMarkerDO == null) return;
			advertisementManager.applyMapMarker(mapMarkerDO);
			success = SUCCESS;
			msg = "您的广告标注已经提交成功";
			nav.redirectTo("zhaile").withTarget("finish.vm").withParameter("success", success).withParameter("msg", msg);
		} catch (ParseException e) {
			success = FAILED;
			msg = "您的广告标注提交失败";
		}
		nav.redirectTo("zhaile").withTarget("finish.vm").withParameter("success", success).withParameter("msg", msg);
	}
	
	public void doPay(@FormGroup("payment") PaymentFormBean paymentFormBean, Navigator nav){
		CustomerDO loginUser = super.getCustomerDO(null);
		
		Long customerId = null;
		String success = FAILED;
		String msg = "";
		if(loginUser!=null){
			customerId = loginUser.getId();
		} 
		List<ShoppingCarVO> list = super.getShoppingCarList(customerId);
		Collection<ShopDO> shopList = super.getShopListFromShoppingCar(customerId);
		
		SystemConfigCache systemCache = cache.getSystemConfigcache();
		SystemConfigDO defaultCharge = systemCache.getConfig(SystemConfigCacheKey.DEFAULT_CHARGE);
		int chargePerShop = Integer.parseInt(defaultCharge.getValue());
		SystemConfigDO zhaileShopId = systemCache.getConfig(SystemConfigCacheKey.ZHAILE_SHOP_ID);
		Long zhaileShopIdLong = Long.parseLong(zhaileShopId.getValue());
		boolean firstFree = systemCache.getSwitch(SystemConfigCacheKey.FIRST_FREE_SWITCH);
		Map<Long, Double> distanceMap = super.generateDistanceMap(shopList);

		Double lng = super.getLng();
		Double lat = super.getLat();
		
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
		paymentFormBean.setIp(super.getIpAddr());
		Result<PaymentQueryJson> paymentStatus = transactionManager.addPayment(paymentFormBean, list, customerId);
		msg = paymentStatus.getMessage();
		if(paymentStatus.isSuccess()){
			success = SUCCESS;
			checkFlashGo(list);
			session.removeAttribute("shoppingCar");
			cache.setPaymentStatus(paymentStatus.getDataObject());
			session.setAttribute("paymentStatusId", paymentStatus.getDataObject().getId());
			smsTaskManager.prepareSmsTask(paymentStatus.getDataObject());
			smsTaskManager.prepareShopReceipt(list,shopList,paymentFormBean.getContact(),paymentFormBean.getComment());
			if(paymentFormBean.getPaymentType().equals(PaymentTypeEnum.支付宝.getCode())){
				String paymentId = paymentStatus.getDataObject().getId().toString();
				Double totalFee = paymentStatus.getDataObject().getTotalPrice() + paymentStatus.getDataObject().getCharge();
				BigDecimal total = new BigDecimal(totalFee).setScale(2,BigDecimal.ROUND_DOWN);
				nav.redirectTo("zhaile").withTarget("alipay.vm").withParameter("paymentId", paymentId).withParameter("total_fee", total.toString());
				return;
			}
		} else {
			success = FAILED;
		}
		nav.redirectTo("zhaile").withTarget("finish.vm").withParameter("success", success).withParameter("msg", msg);
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
