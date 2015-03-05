package com.zhaile.biz.web.manager;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.victor.framework.common.shared.Result;
import com.victor.framework.common.tools.CollectionTools;
import com.victor.framework.common.tools.StringTools;
import com.victor.framework.dal.basic.Paging;
import com.zhaile.biz.common.manager.CommonManager;
import com.zhaile.biz.web.json.OrderQueryJson;
import com.zhaile.biz.web.json.PaymentAlertJson;
import com.zhaile.biz.web.json.PaymentQueryJson;
import com.zhaile.biz.web.json.PaymentStatisticJson;
import com.zhaile.biz.web.model.ChargeRequestVO;
import com.zhaile.biz.web.model.CustomerVO;
import com.zhaile.biz.web.model.OrderVO;
import com.zhaile.biz.web.model.PaymentVO;
import com.zhaile.biz.web.model.ProductVO;
import com.zhaile.biz.web.model.ShoppingCarVO;
import com.zhaile.biz.web.model.TodayEventVO;
import com.zhaile.dal.cache.EmployeeCache;
import com.zhaile.dal.cache.SystemConfigCache;
import com.zhaile.dal.cache.key.SystemConfigCacheKey;
import com.zhaile.dal.dao.CustomerCommentDAO;
import com.zhaile.dal.dao.MapMarkerDAO;
import com.zhaile.dal.dao.OrderDAO;
import com.zhaile.dal.dao.PaymentDAO;
import com.zhaile.dal.dao.ProductDAO;
import com.zhaile.dal.dao.ShoppingCarDAO;
import com.zhaile.dal.enumerate.DeliveryStatusEnum;
import com.zhaile.dal.enumerate.EnableEnum;
import com.zhaile.dal.enumerate.ServiceTypeEnum;
import com.zhaile.dal.enumerate.SmsStatusEnum;
import com.zhaile.dal.model.CustomerDO;
import com.zhaile.dal.model.EmployeeDO;
import com.zhaile.dal.model.FlashGoDO;
import com.zhaile.dal.model.MapPoiDO;
import com.zhaile.dal.model.OrderDO;
import com.zhaile.dal.model.PaymentDO;
import com.zhaile.dal.model.ProductDO;
import com.zhaile.dal.model.ShopDO;
import com.zhaile.dal.model.ShoppingCarDO;
import com.zhaile.dal.model.SmsTaskDO;
import com.zhaile.dal.model.vo.PaymentFormBean;
import com.zhaile.dal.model.vo.ShoppingCarPay;
import com.zhaile.dal.query.condition.CustomerCommentQueryCondition;
import com.zhaile.dal.query.condition.OrderQueryCondition;
import com.zhaile.dal.query.condition.PaymentQueryCondition;
import com.zhaile.dal.query.condition.ShoppingCarQueryCondition;

public class TransactionManagerImpl extends CommonManager implements TransactionManager {

	@Autowired
	private ShoppingCarDAO shoppingCarDAO;
	
	@Autowired
	private PaymentDAO paymentDAO;
	
	@Autowired
	private ProductDAO productDAO;
	
	@Autowired
	private OrderDAO orderDAO;
	
	@Autowired
	private CustomerCommentDAO customerCommentDAO;
	
	@Autowired
	private MapMarkerDAO mapMarkerDAO;
	
	@Autowired
	private CustomerManager customerManager;
	
	@Autowired
	private ShopManager shopManager;
	
	@Autowired
	private FlashGoManager flashGoManager;
	
	@Autowired
	private EmployeeCache employeeCache;
	
	@Autowired
	private SystemConfigCache systemConfigCache;
	
	@Override
	public Result<ShoppingCarVO> addToShoppingCar(Long prodId, Integer quntity,Long customerId) {
		ShoppingCarQueryCondition queryCondition = new ShoppingCarQueryCondition();
		queryCondition.customerId(customerId).prodId(prodId);
		Object existO = getObject(shoppingCarDAO.getPage(queryCondition));
		if(existO!=null) {
			@SuppressWarnings("unchecked")
			List<ShoppingCarDO> list = (List<ShoppingCarDO>)existO;
			if(CollectionTools.isNotEmpty(list)) {
				return Result.newInstance(null, "购物车中已经存在", false);
			}
		}
		ShopDO shop = shopManager.getShopByProdId(prodId);
		ShoppingCarDO shoppingCarDO = new ShoppingCarDO();
		shoppingCarDO.setCustomerId(customerId);
		shoppingCarDO.setProdId(prodId);
		shoppingCarDO.setQuantity(quntity);
		shoppingCarDO.setShopId(shop.getId());
		shoppingCarDO.setValid(EnableEnum.无效.getCode());
		Result<Long> result = shoppingCarDAO.insert(shoppingCarDO);
		if(result == null || !result.isSuccess()) {
			return Result.newInstance(null, "添加到购物车失败", false);
		} else {
			checkValid(shop,customerId);
			return Result.newInstance(shoppingCarDO2VO(shoppingCarDO), "添加到购物车成功", true);
		}
	}
	
	private void checkValid(ShopDO shop,Long customerId){
		if(shop.getPrice() == null) {
			shoppingCarDAO.updateValid(shop.getId(), customerId, EnableEnum.有效);
			return;
		}
		ShoppingCarQueryCondition queryCondition = new ShoppingCarQueryCondition();
		queryCondition.customerId(customerId).shopId(shop.getId());
		Object existO = getObject(shoppingCarDAO.getPage(queryCondition));
		@SuppressWarnings("unchecked")
		List<ShoppingCarDO> list = (List<ShoppingCarDO>)existO;
		Double total = 0.0;
		for(ShoppingCarDO shoppingCar : list){
			Long prodId = shoppingCar.getProdId();
			ProductDO prod = productDAO.getById(prodId).getDataObject();
			if(prod != null && prod.getEnable().equals(EnableEnum.有效.getCode())){
				total += prod.getPrice() * shoppingCar.getQuantity();
			}
		}
		if(total >= shop.getPrice()){
			shoppingCarDAO.updateValid(shop.getId(), customerId, EnableEnum.有效);
		} else {
			shoppingCarDAO.updateValid(shop.getId(), customerId, EnableEnum.无效);
		}
	}
	
	@Override
	public List<ShoppingCarVO> checkValid(List<ShoppingCarVO> list) {
		if(list==null || list.isEmpty()) {
			return list;
		}
		Map<Long,Double> priceMap = Maps.newHashMap();
		Map<Long,ShopDO> shopMap = Maps.newHashMap();
		List<ShoppingCarVO> result = Lists.newArrayList();
		for(ShoppingCarVO shoppingCarVO : list){
			ShopDO shop = shoppingCarVO.getProductVO().getShopVO().getShopDO();
			ProductDO prod = shoppingCarVO.getProductVO().getProductDO();
			ShoppingCarDO sp = shoppingCarVO.getShopCar();
			shopMap.put(shop.getId(), shop);
			Double priceBuy = priceMap.get(shop.getId());
			if(priceBuy == null){
				priceBuy = 0.0;
			}
			priceBuy += prod.getPrice() * sp.getQuantity();
			priceMap.put(shop.getId(), priceBuy);
		}
		for(ShoppingCarVO shoppingCarVO : list){
			ShopDO shop = shoppingCarVO.getProductVO().getShopVO().getShopDO();
			ShoppingCarDO sp = shoppingCarVO.getShopCar();
			Double priceBuy = priceMap.get(shop.getId());
			Double price = shopMap.get(shop.getId()).getPrice();
			if(price == null) {
				sp.setValid(EnableEnum.有效.getCode());
				shoppingCarVO.setShopCar(sp);
			} else {
				if(priceBuy >= price){
					sp.setValid(EnableEnum.有效.getCode());
					shoppingCarVO.setShopCar(sp);
				} else {
					sp.setValid(EnableEnum.无效.getCode());
					shoppingCarVO.setShopCar(sp);
				}
			}
			result.add(shoppingCarVO);
		}
		return result;
	}

	@Override
	public List<ShoppingCarVO> getShoppingCarByCustomerId(Long customerId) {
		ShoppingCarQueryCondition queryCondition = new ShoppingCarQueryCondition();
		queryCondition.customerId(customerId);
		Object existO = getObject(shoppingCarDAO.getPage(queryCondition));
		List<ShoppingCarVO> voList = Lists.newArrayList();
		if(existO!=null) {
			@SuppressWarnings("unchecked")
			List<ShoppingCarDO> list = (List<ShoppingCarDO>)existO;
			if(CollectionTools.isNotEmpty(list)) {
				for(ShoppingCarDO shoppingCarDO : list){
					ShoppingCarVO shoppingCarVO = shoppingCarDO2VO(shoppingCarDO);
					voList.add(shoppingCarVO);
				}
			}
		}
		return voList;
	}
	
	@Override
	public ShoppingCarVO shoppingCarDO2VO(ShoppingCarDO shoppingCarDO){
		ShoppingCarVO shoppingCarVO = new ShoppingCarVO();
		shoppingCarVO.setShopCar(shoppingCarDO);
		ProductVO prod = shopManager.getProdVOById(shoppingCarDO.getProdId());
		shoppingCarVO.setProductVO(prod);
		assamblePromoionItem(shoppingCarVO);
		try{
			shoppingCarVO.setTotalPrice(prod.getProductDO().getPrice() * shoppingCarDO.getQuantity());
			shoppingCarVO.setCredit(prod.getProductDO().getPrice() * shoppingCarDO.getQuantity());
		} catch(Exception ex){
			shoppingCarVO.setTotalPrice(0d);
			shoppingCarVO.setCredit(0d);
		}
		return shoppingCarVO;
	}
	
	private void assamblePromoionItem(ShoppingCarVO item){
		if(item==null || item.getProductVO()==null || item.getProductVO().getProductDO()==null){
			return;
		}
		ShoppingCarDO shoppingCarDO = item.getShopCar();
		ProductVO prodVO = item.getProductVO();
		ProductDO prod = prodVO.getProductDO();
		boolean isPromot = isProdPromot(prod);
		if(isPromot && shoppingCarDO.getCustomerId()!=null){
			shoppingCarDO.setQuantity(1);
			prod.setPrice(1d);
			item.setShopCar(shoppingCarDO);
			prodVO.setProductDO(prod);
			item.setProductVO(prodVO);
		}
		FlashGoDO flashGoDO = flashGoManager.getToday();
		if(isFlashGoProd(flashGoDO,prod)){
			if(item.getShopCar().getQuantity() >= flashGoDO.getLimitBuy()){
				shoppingCarDO.setQuantity(flashGoDO.getLimitBuy());
			}
			prod.setPrice(flashGoDO.getPrice());
			item.setShopCar(shoppingCarDO);
			prodVO.setProductDO(prod);
			item.setProductVO(prodVO);
			item.setIsFlashGo(true);
			item.setLimitBuy(limitBuy(flashGoDO,prod));
		} 
	}
	
	private boolean isFlashGoProd(FlashGoDO flashGoDO,ProductDO prod){
		if(prod == null){
			return false;
		}
		try{
			if(flashGoDO.getProdId().equals(prod.getId())){
				TodayEventVO todayEventVO = new TodayEventVO();
				todayEventVO.setFlashGo(flashGoDO);
				return todayEventVO.getEnable();
			}
			return false;
		} catch(Exception e){
			return false;
		}
	}
	
	private int limitBuy(FlashGoDO flashGoDO,ProductDO prod){
		if(prod == null){
			return 0;
		}
		try{
			TodayEventVO todayEventVO = new TodayEventVO();
			todayEventVO.setFlashGo(flashGoDO);
			return todayEventVO.getItemAllowed();
		} catch(Exception e){
			return 0;
		}
	}
	
	@Override
	public boolean isProdPromot(ProductDO prod){
		if(!systemConfigCache.getSwitch(SystemConfigCacheKey.ZHAILE_PROMOTION_SWITCH)){
			return false;
		}
		if(prod == null) {
			return false;
		}
		try {
			String[] ZHAILE_PROMOTION_ITEM = systemConfigCache
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

	@Override
	public Result<ShoppingCarVO> editShoppingCar(Long prodId, Integer quantity, Long customerId) {
		ShoppingCarQueryCondition queryCondition = new ShoppingCarQueryCondition();
		queryCondition.customerId(customerId).prodId(prodId);
		Object existO = getObject(shoppingCarDAO.getPage(queryCondition));
		if(existO!=null) {
			@SuppressWarnings("unchecked")
			List<ShoppingCarDO> list = (List<ShoppingCarDO>)existO;
			if(CollectionTools.isNotEmpty(list)) {
				for(ShoppingCarDO shoppingCarDO : list){
					shoppingCarDO.setQuantity(quantity);
					Result<Boolean> result = shoppingCarDAO.update(shoppingCarDO);
					if(result.isSuccess()) {
						ShopDO shop = shopManager.getShopByProdId(prodId);
						checkValid(shop,customerId);
						return Result.newInstance(shoppingCarDO2VO(shoppingCarDO), "修改购物车商品数量成功", true);
					} else {
						return Result.newInstance(null, "修改购物车商品数量失败", false);
					}
				}
			} else {
				return Result.newInstance(null, "购物车中不存在", false);
			}
		}
		return Result.newInstance(null, "购物车中不存在", false);
	}

	@Override
	public Result<Boolean> RemoveFromShoppingCar(Long prodId, Long customerId) {
		ShoppingCarQueryCondition queryCondition = new ShoppingCarQueryCondition();
		queryCondition.customerId(customerId).prodId(prodId);
		Object existO = getObject(shoppingCarDAO.getPage(queryCondition));
		if(existO!=null) {
			@SuppressWarnings("unchecked")
			List<ShoppingCarDO> list = (List<ShoppingCarDO>)existO;
			if(CollectionTools.isNotEmpty(list)) {
				for(ShoppingCarDO shoppingCarDO : list){
					Result<Boolean> result = shoppingCarDAO.delete(shoppingCarDO.getId());
					if(result == null || !result.isSuccess()) {
						return Result.newInstance(false, "移除购物车商品失败", false);
					} else {
						ShopDO shop = shopManager.getShopByProdId(prodId);
						checkValid(shop,customerId);
						return Result.newInstance(true, "移除购物车商品成功", true);
					}
				}
			} else {
				return Result.newInstance(false, "购物车中不存在", false);
			}
		}
		return Result.newInstance(false, "购物车中不存在", false);
	}

	@Override
	public Result<PaymentQueryJson> addPayment(PaymentFormBean paymentFormBean,List<ShoppingCarVO> list,Long customerId) {
		List<ShoppingCarPay> shoppingCarList = Lists.newArrayList();
		for(ShoppingCarVO shoppingCarVO : list){
			if(shoppingCarVO.getShopCar().getValid().equals(EnableEnum.有效.getCode())){
				ShoppingCarPay shoppingCarPay = new ShoppingCarPay();
				shoppingCarPay.setShopCar(shoppingCarVO.getShopCar());
				shoppingCarPay.setProductDO(shoppingCarVO.getProductVO().getProductDO());
				shoppingCarList.add(shoppingCarPay);
			}
		}
		
		MapPoiDO mapPoiDO = paymentFormBean.getMapPoiDO();
		mapPoiDO.setCustomerId(customerId);
		customerManager.updateMapPoi(mapPoiDO);
		
		Result<PaymentDO> result = shoppingCarDAO.pay(shoppingCarList, 
													ServiceTypeEnum.宅乐外送, 
													customerId, 
													paymentFormBean);
		if(result.isSuccess()){
			return Result.newInstance(DO2Json(result.getDataObject()), result.getMessage(), true);
		} else {
			return Result.newInstance(null, result.getMessage(), false);
		}
	}
	
	@Override
	public Paging<PaymentQueryJson> queryPayment(PaymentQueryCondition queryCondition) {
		int totalSize = paymentDAO.getCount(queryCondition).getDataObject();
		@SuppressWarnings("unchecked")
		Paging<PaymentQueryJson> paymentPage = queryCondition.getPaging(totalSize, 5);
		List<PaymentQueryJson> paymentList = Lists.newArrayList();
		List<PaymentDO> list = paymentDAO.getPage(queryCondition).getDataObject();
		if(CollectionTools.isNotEmpty(list)){
			for(PaymentDO paymentDO : list){
				paymentList.add(DO2Json(paymentDO));
			}
		}
		paymentPage.setData(paymentList);
		return paymentPage;
	}
	
	@Override
	public List<SmsTaskDO> queryPaymentSmsTask() {
		List<SmsTaskDO> result = Lists.newArrayList();
		List<PaymentDO> list = paymentDAO.getSmsTask().getDataObject();
		
		if(CollectionTools.isNotEmpty(list)){
			for(PaymentDO paymentDO : list){
				SmsTaskDO task = new SmsTaskDO();
				task.setMobile(paymentDO.getSmsMobile());
				task.setText(paymentDO.getSmsText());
				task.setCallBackUrl("admin/json/updateSmsStatus.json?paymentId="+paymentDO.getId()+"&smsStatus=[status]");
				result.add(task);
			}
		}
		return result;
	}
	
	@Override
	public PaymentVO getPaymentById(Long paymentId) {
		PaymentDO payment = paymentDAO.getById(paymentId).getDataObject();
		if(payment!=null){
			return DO2VO(payment);
		}
		return null;
	}
	
	private PaymentVO DO2VO(PaymentDO paymentDO){
		if(paymentDO == null) {
			return null;
		}
		PaymentVO paymentVO = new PaymentVO();
		paymentVO.setPayment(paymentDO);
		CustomerVO customer = customerManager.getCustomer(paymentDO.getCustomerId());
		paymentVO.setCustomer(customer);
		paymentVO.setOrderList(queryOrder(paymentDO));
		return paymentVO;
	}
	
	private PaymentQueryJson DO2Json(PaymentDO paymentDO){
		if(paymentDO == null) {
			return null;
		}
		PaymentQueryJson paymentJson = new PaymentQueryJson();
		CustomerDO customer = customerManager.getCustomerById(paymentDO.getCustomerId());
		if(customer == null){
			paymentJson.setCustomerId(0l);
			paymentJson.setCustomerName("游客");
		} else {
			paymentJson.setCustomerName(customer.getName());
			paymentJson.setCustomerId(customer.getId());
		}
		paymentJson.setCharge(paymentDO.getCharge());
		paymentJson.setId(paymentDO.getId());
		paymentJson.setOrderTime(paymentDO.getGmtCreate());
		paymentJson.setTotalPrice(paymentDO.getNetpay());
		paymentJson.setStatus(paymentDO.getStatus());
		paymentJson.setComment(paymentDO.getComment());
		paymentJson.setContact(paymentDO.getContact());
		paymentJson.setEmployeeId(paymentDO.getEmployeeId());
		paymentJson.setSource(paymentDO.getSource());
		paymentJson.setReceived(paymentDO.getReceived());
		paymentJson.setSmsStatus(paymentDO.getSmsStatus());
		paymentJson.setSmsMobile(paymentDO.getSmsMobile());
		paymentJson.setSmsText(paymentDO.getSmsText());
		paymentJson.setMapAdd(paymentDO.getMapAdd());
		paymentJson.setLng(paymentDO.getLng());
		paymentJson.setLat(paymentDO.getLat());
		paymentJson.setPaymentStatus(paymentDO.getPaymentStatus());
		paymentJson.setShops(getShopListByPaymentId(paymentDO.getId()));
		return paymentJson;
	}
	
	private List<ShopDO> getShopListByPaymentId(Long id){
		List<ShopDO> result = Lists.newArrayList();
		OrderQueryCondition queryCondition = new OrderQueryCondition();
		queryCondition.paymentId(id);
		List<OrderDO> list = orderDAO.getPage(queryCondition).getDataObject();
		if(CollectionTools.isNotEmpty(list)){
			for(OrderDO order : list){
				ShopDO shop = shopManager.getShopById(order.getShopId());
				if(shop!=null){
					result.add(shop);
				}
			}
		}
		return result;
	}

	private List<OrderVO> queryOrder(PaymentDO paymentDO) {
		List<OrderVO> result = Lists.newArrayList();
		OrderQueryCondition queryCondition = new OrderQueryCondition();
		queryCondition.paymentId(paymentDO.getId());
		List<OrderDO> list = orderDAO.getPage(queryCondition).getDataObject();
		if(CollectionTools.isNotEmpty(list)){
			for(OrderDO order : list){
				result.add(orderDO2VO(order));
			}
		}
		return result;
	} 
	
	@Override
	public PaymentStatisticJson queryPaymentStatistic(Date date,Long[] shops) {
		PaymentStatisticJson result = new PaymentStatisticJson();
		List<PaymentAlertJson> paymentAlert = Lists.newArrayList();
		List<DeliveryStatusEnum> statusEnumList = DeliveryStatusEnum.getAll();
		String[] colors = {"red","blue","orange","grey","red","grey"};
		
		String[] statusValid = new String[]{DeliveryStatusEnum.取单中.getCode(),
				   DeliveryStatusEnum.已送到.getCode(),
				   DeliveryStatusEnum.送达中.getCode()};
		PaymentQueryCondition paymentToday = new PaymentQueryCondition();
		paymentToday.gmtModifyStart(date).gmtModifyEnd(date).shopId(shops).status(statusValid);
		
		CustomerCommentQueryCondition commentToday = new CustomerCommentQueryCondition();
		commentToday.gmtModifyStart(date).gmtModifyEnd(date).shopIds(shops);
		
		for(int i=0;i<statusEnumList.size();i++){
			DeliveryStatusEnum status = statusEnumList.get(i);
			PaymentQueryCondition queryCondition = new PaymentQueryCondition();
			queryCondition.gmtModifyStart(date);
			queryCondition.gmtModifyEnd(date);
			queryCondition.shopId(shops);
			queryCondition.status(status.getCode());
			Result<Integer> queryResult = paymentDAO.getCount(queryCondition);
			PaymentAlertJson paymentAlertJson = new PaymentAlertJson();
			paymentAlertJson.setCount(queryResult.getDataObject());
			if(queryResult.isSuccess()){
				paymentAlertJson.setMsg("查询成功");
				paymentAlertJson.setSuccess(true);
			} else {
				paymentAlertJson.setMsg("查询失败，请速与管理员联系");
				paymentAlertJson.setSuccess(false);
			}
			paymentAlertJson.setColor(colors[i]);
			paymentAlertJson.setStatusCode(status.getCode());
			paymentAlertJson.setStatusDesc(status.getDesc());
			paymentAlert.add(paymentAlertJson);
		}
		
		result.setStatusList(paymentAlert);
		result.setTotalCount(paymentDAO.getCount(paymentToday).getDataObject());
		result.setTotalNetPay(paymentDAO.getTotalNetpay(paymentToday).getDataObject());
		result.setTotalCharge(paymentDAO.getTotalCharge(paymentToday).getDataObject());
		result.setTotalComment(customerCommentDAO.getCount(commentToday).getDataObject());
		result.setMapMarkerTaskCount(mapMarkerDAO.getTaskCount());
		return result;
	}

	@Override
	public PaymentQueryJson updatePaymentStatus(Long id,
												Long customerId,
												Long employeeId, 
												String status, 
												Double charge,
												Double received,
												String paymentStatus) {
		PaymentDO forUpdate = paymentDAO.getById(id).getDataObject();
		if(charge != null) {
			forUpdate.setCharge(charge);
		}
		if(received != null) {
			forUpdate.setReceived(received);
		}
		if(forUpdate.getSmsStatus().equals(SmsStatusEnum.未发送.getCode())){
			forUpdate.setSmsStatus(SmsStatusEnum.准备发送.getCode());
		}
		if(StringTools.equalAny(status, DeliveryStatusEnum.已取消.getCode(),DeliveryStatusEnum.未送到.getCode())){
			forUpdate.setReceived(0d);
		}
		forUpdate.setId(id);
		forUpdate.setCustomerId(customerId);
		forUpdate.setEmployeeId(employeeId);
		forUpdate.setStatus(status);
		forUpdate.setPaymentStatus(paymentStatus);
		if(paymentDAO.update(forUpdate).isSuccess()){
			return DO2Json(forUpdate);
		}
		return null;
	}

	@Override
	public Double getCharge(ChargeRequestVO requestVO){
		
		CustomerDO loginUser = requestVO.getLoginUser();
		Collection<ShopDO> list = requestVO.getList();
		int defaultCharge = requestVO.getDefaultCharge();
		Long zhaileShopId = requestVO.getZhaileShopId();
		Boolean firstFree = requestVO.getFirstFree();
		Map<Long,Double> distanceMap = requestVO.getDistanceMap();
		
		if(CollectionTools.isEmpty(list)){
			return 0d;
		}
		
		List<Long> shopAdded = Lists.newArrayList();
		Double totalCharge = 0d;
		boolean hasZhaile = false;		//是否有宅乐网
		boolean hasZhaileDelvery = false;	//是否有其他店铺
		boolean hasDiscount = false;	//是否有打折
		
		Double defaultPerCharge = Double.parseDouble(defaultCharge+""); //默认外卖费
		Double zhaileCharge = defaultPerCharge;
		
		Double customerDiscount = defaultPerCharge;
		if(firstFree && loginUser!=null) { //登录用户，QQ或宅乐或微信用户
			hasDiscount = true;
			if(isFirstOrder(loginUser.getId())) { 
				customerDiscount = 0d; //首次下订单
			} else {
				customerDiscount = 3d; //旧客户仍然3元
			}
		}
		
		for(ShopDO shop : list){
			if(shop == null) continue;
			Long shopId = shop.getId();
			
			if(shopId.intValue() != zhaileShopId.intValue()) {
				boolean isSelfDelivery = shop.getCharge() < defaultPerCharge; //自送还是宅乐送
				if(!shopAdded.contains(shopId)){
					Double shopPerCharge = defaultPerCharge;
					if(shop.getCharge()!=null){
						shopPerCharge = shop.getCharge();
						if(!isSelfDelivery && distanceMap!=null && !distanceMap.isEmpty()){
							Double distance = distanceMap.get(shopId);
							shopPerCharge = getChargeByDistance(distance);
							if(shopPerCharge == null){
								shopPerCharge = shop.getCharge();
							}
						}
						if(!isSelfDelivery && hasDiscount) {	//非自送，并且有优惠
							shopPerCharge = customerDiscount;
						}
					}
					totalCharge += shopPerCharge;
					shopAdded.add(shopId);
					if(!isSelfDelivery){
						hasZhaileDelvery = true;
					}
					
				} 
			} else {
				zhaileCharge = shop.getCharge();
				hasZhaile = true;
			}
		}
		if(hasZhaile && !hasZhaileDelvery){
			if(hasDiscount) {
				totalCharge += customerDiscount;
			} else {
				totalCharge += zhaileCharge;
			}
		}
		return totalCharge;
	}
	
	public Double getTotalValue(List<ShoppingCarVO> list){
		Double total = 0d;
		if(CollectionTools.isNotEmpty(list)){
			for(ShoppingCarVO shoppingCarVO : list){
				if(shoppingCarVO.getShopCar().getValid().equals(EnableEnum.无效.getCode())){
					continue;
				}
				double totalValue = shoppingCarVO.getTotalPrice();
				total = total + totalValue;
			}
		}
		return total;
	}
	
	private static Double getChargeByDistance(Double distance){
		if(distance == null){
			return null;
		}
		if(distance<=1000d){
			return 3d;
		}
		Double disB = distance - 1000d;
		int charge = disB.intValue()/500+((disB%500d)>0?1:0) + 3;
		return Double.parseDouble(charge+"");
	}
	
	private boolean isFirstOrder(Long customerId){
		OrderQueryCondition queryCondition = new OrderQueryCondition();
		queryCondition.customerId(customerId);
		List<OrderQueryJson> orderList = Lists.newArrayList();
		orderList = getOrderReport(queryCondition);
		return CollectionTools.isEmpty(orderList);
	}

	@Override
	public PaymentQueryJson updatePaymentComment(Long id, String comment) {
		PaymentDO forUpdate = new PaymentDO();
		forUpdate.setId(id);
		forUpdate.setComment(comment);
		if(paymentDAO.update(forUpdate).isSuccess()){
			return DO2Json(forUpdate);
		}
		return null;
	}
	
	@Override
	public PaymentQueryJson updatePaymentSmsStatus(Long id, String smsStatus) {
		PaymentDO forUpdate = new PaymentDO();
		forUpdate.setId(id);
		forUpdate.setSmsStatus(smsStatus);
		if(paymentDAO.update(forUpdate).isSuccess()){
			return DO2Json(forUpdate);
		}
		return null;
	}
	
	@Override
	public PaymentQueryJson updatePaymentStatus(Long id, String paymentStatus,String paymentCode,String paymentResp) {
		PaymentDO forUpdate = new PaymentDO();
		forUpdate.setId(id);
		forUpdate.setPaymentStatus(paymentStatus);
		forUpdate.setPaymentCode(paymentCode);
		forUpdate.setPaymentResp(paymentResp);
		if(paymentDAO.update(forUpdate).isSuccess()){
			return DO2Json(forUpdate);
		}
		return null;
	}
	
	@Override
	public PaymentQueryJson updatePaymentMapInfo(Long id, Double charge, Double received,
			Double lng, Double lat, String mapAdd) {
		PaymentDO forUpdate = new PaymentDO();
		forUpdate.setId(id);
		forUpdate.setCharge(charge);
		forUpdate.setReceived(received);
		forUpdate.setLng(lng);
		forUpdate.setLat(lat);
		forUpdate.setMapAdd(mapAdd);
		if(paymentDAO.update(forUpdate).isSuccess()){
			return DO2Json(forUpdate);
		}
		return null;
		
	}

	@Override
	public List<OrderVO> getOrderVO(OrderQueryCondition orderQueryCondition) {
		List<OrderVO> result = Lists.newArrayList();
		List<OrderDO> list = orderDAO.getPage(orderQueryCondition).getDataObject();
		if(CollectionTools.isNotEmpty(list)){
			for(OrderDO order : list){
				result.add(orderDO2VO(order));
			}
		}
		return result;
	}
	
	@Override
	public List<OrderQueryJson> getOrderReport(OrderQueryCondition orderQueryCondition) {
		List<OrderQueryJson> result = Lists.newArrayList();
		List<OrderDO> list = orderDAO.getPage(orderQueryCondition).getDataObject();
		if(CollectionTools.isNotEmpty(list)){
			for(OrderDO order : list){
				result.add(orderDO2Json(order));
			}
		}
		return result;
	}
	
	private OrderVO orderDO2VO(OrderDO order){
		if(order == null) {
			return null;
		}
		OrderVO orderVO = new OrderVO();
		orderVO.setOrder(order);
		PaymentDO paymentDO = paymentDAO.getById(order.getPaymentId()).getDataObject();
		orderVO.setPayment(paymentDO);
		
		CustomerVO customer = customerManager.getCustomer(order.getCustomerId());
		orderVO.setCustomer(customer);
		
		ProductVO product = shopManager.getProdVOById(order.getProdId());
		orderVO.setProd(product);
		
		if(paymentDO!=null && paymentDO.getEmployeeId()!=null){
			EmployeeDO employee = employeeCache.getEmployee(paymentDO.getEmployeeId());
			orderVO.setEmployee(employee);
		}
		return orderVO;
	}

	private OrderQueryJson orderDO2Json(OrderDO order){
		if(order == null) {
			return null;
		}
		OrderQueryJson orderJson = new OrderQueryJson();
		CustomerVO customer = customerManager.getCustomer(order.getCustomerId());
		if(customer == null){
			orderJson.setCustomerId(0l);
			orderJson.setCustomerName("游客");
		} else {
			orderJson.setCustomerName(customer.getCustomer().getName());
			orderJson.setCustomerId(customer.getCustomer().getId());
		}
		orderJson.setShopId(order.getShopId());
		ProductVO product = shopManager.getProdVOById(order.getProdId());
		if(product!=null && product.getProductDO()!=null){
			orderJson.setProdName(product.getProductDO().getName());
			orderJson.setProdId(product.getProductDO().getId());
			if(product.getShopVO()!=null && product.getShopVO().getShopDO()!=null) {
				orderJson.setShopName(product.getShopVO().getShopDO().getName());
			}
		}
		orderJson.setOrderTime(order.getGmtCreate());
		orderJson.setQuantity(Double.parseDouble(order.getQuantity().toString()));
		orderJson.setPrice(order.getPrice());
		orderJson.setTotalPrice(Double.parseDouble(order.getQuantity().toString())*order.getPrice());
		PaymentDO paymentDO = paymentDAO.getById(order.getPaymentId()).getDataObject();
		if(paymentDO!=null){
			orderJson.setPaymentId(paymentDO.getId());
			orderJson.setStatus(paymentDO.getStatus());
			orderJson.setComment(paymentDO.getComment());
			orderJson.setContact(paymentDO.getContact());
			orderJson.setEmployeeId(paymentDO.getEmployeeId());
			orderJson.setSource(paymentDO.getSource());
		}
		return orderJson;
	}
}
