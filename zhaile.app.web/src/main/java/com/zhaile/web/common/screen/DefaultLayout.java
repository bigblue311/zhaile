package com.zhaile.web.common.screen;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Context;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.victor.framework.common.tools.CollectionTools;
import com.victor.framework.common.tools.StringTools;
import com.victor.framework.search.basic.SearchResult;
import com.victor.framework.spymemcached.CacheService;
import com.zhaile.biz.common.Cache;
import com.zhaile.biz.web.json.PaymentQueryJson;
import com.zhaile.biz.web.manager.AdvertisementManager;
import com.zhaile.biz.web.manager.CustomerManager;
import com.zhaile.biz.web.manager.TransactionManager;
import com.zhaile.biz.web.model.ShoppingCarVO;
import com.zhaile.dal.cache.key.SystemConfigCacheKey;
import com.zhaile.dal.enumerate.DeliveryStatusEnum;
import com.zhaile.dal.enumerate.EnableEnum;
import com.zhaile.dal.enumerate.MapIconEnum;
import com.zhaile.dal.model.CustomerDO;
import com.zhaile.dal.model.MapMarkerDO;
import com.zhaile.dal.model.MapPoiDO;
import com.zhaile.dal.model.PeopleContactDO;
import com.zhaile.dal.model.ProductDO;
import com.zhaile.dal.model.ShopDO;
import com.zhaile.dal.query.condition.PaymentQueryCondition;
import com.zhaile.search.engine.db.DBSearchFacade;

public class DefaultLayout {
	public final static String MENU_HOMEPAGE = "0";
	
	@Autowired
	private HttpSession session;
	
	@Autowired
    private HttpServletRequest request;
	
	@Autowired
	private Cache cache;
	
	@Autowired
	private CacheService memcachedService;
	
	@Autowired
	private CustomerManager customerManager;
	
	@Autowired
	private DBSearchFacade searchFacade;
	
	@Autowired
	private TransactionManager transactionManager;
	
	@Autowired
	private AdvertisementManager advertisementManager;
	
	public void load(Context context,Long userId){
		context.put("myLocation",getMyLocation());
		context.put("mapIconEnum",MapIconEnum.getAll());
		context.put("shoppingCarCount",getShoppingCarCount(userId));
		context.put("loginUser",getCustomerDO(userId));
		context.put("zhaileShopId", cache.getSystemConfigcache().getConfig(SystemConfigCacheKey.ZHAILE_SHOP_ID).getValue());
		context.put("hotProduct",getBestProductTop10());
		context.put("todayMarkers",getTodayMarker());
		context.put("menuN",MENU_HOMEPAGE);
	}
	
	private List<ProductDO> getBestProductTop10(){
		String key = this.getClass().getSimpleName()+".bestProductTop10";
		List<ProductDO> list = memcachedService.getList(key, ProductDO.class);
		if(list == null){
			list = searchFacade.bestProductTop10();
			memcachedService.setObject(key, list, 12*60*60);
		}
		return list;
	}
	
	private List<MapMarkerDO> getTodayMarker(){
		String key = this.getClass().getSimpleName()+".todayMarker";
		List<MapMarkerDO> list = memcachedService.getList(key, MapMarkerDO.class);
		if(list == null){
			list = advertisementManager.getTodayValid();
			memcachedService.setObject(key, list, 24*60*60);
		}
		return list;
	}
	
	private MapPoiDO getMyLocation(){
		//先尝试从数据库里取
		MapPoiDO mapPoiDO = customerManager.getMapPoiByIP(this.getIpAddr());
		if(mapPoiDO == null){
			mapPoiDO = new MapPoiDO();
			//再尝试从Cookie里取
			String lng = getCookie(CookieKey.MAP_MYPOINT_LNG);
			String lat = getCookie(CookieKey.MAP_MYPOINT_LAT);
			
			//最后尝试从Session里取
			if(StringTools.isEmpty(lng)) lng = getLng()==null?null:getLng().toString();
			if(StringTools.isEmpty(lat)) lat = getLat()==null?null:getLat().toString();
			
			if(StringTools.isNotEmpty(lng)) mapPoiDO.setLng(Double.parseDouble(lng));
			if(StringTools.isNotEmpty(lat)) mapPoiDO.setLat(Double.parseDouble(lat));
		}
		return mapPoiDO;
	}
	
	private int getShoppingCarCount(Long id){
		return getShoppingCarList(id).size();
	}
	
	@SuppressWarnings("unchecked")
	public List<ShoppingCarVO> getShoppingCarList(Long id){
		CustomerDO loginUser = getCustomerDO(id);
		Object sList = session.getAttribute("shoppingCar");
		List<ShoppingCarVO> list = Lists.newArrayList();
		if(loginUser==null){
			if(sList!=null){
				list = (List<ShoppingCarVO>)sList;
				list = transactionManager.checkValid(list);
			}
		} else {
			list = transactionManager.getShoppingCarByCustomerId(loginUser.getId());
		}
		return list;
	}
	
	public Collection<ShopDO> getShopListFromShoppingCar(Long id){
		List<ShoppingCarVO> sList = getShoppingCarList(id);
		Map<Long,ShopDO> shopMap = Maps.newConcurrentMap();
		for(ShoppingCarVO shoppingCar : sList){
			try {
				ShopDO shop = shoppingCar.getProductVO().getShopVO().getShopDO();
				if (shop == null) {
					continue;
				}
				if(shoppingCar.getShopCar().getValid().equals(EnableEnum.无效.getCode())){
					continue;
				}
				shopMap.put(shop.getId(), shop);
			} catch (Exception e) {
				continue;
			}
		}
		return shopMap.values();
	}
	
	@SuppressWarnings("unchecked")
	public List<PeopleContactDO> getPeopleContactList(Long id){
		CustomerDO loginUser = getCustomerDO(id);
		List<PeopleContactDO> list = Lists.newArrayList();
		if(loginUser==null){
			Object cList = session.getAttribute("peopleContact");
			if(cList!=null)
			{
				list = (List<PeopleContactDO>)cList;
				//重新计算ID
				for(int i=0;i<list.size();i++) {
					list.get(i).setId(Long.parseLong(i+1+""));
				}
			}
		} else {
			list = customerManager.getContactByCustomerId(loginUser.getId());
		}
		return list;
	}
	
	public PaymentQueryJson getPaymentStatus(Long id){
		CustomerDO loginUser = getCustomerDO(id);
		Long paymentStatusId = (Long)session.getAttribute("paymentStatusId");
		PaymentQueryJson paymentStatus = null;
		if(loginUser==null){
			if(paymentStatusId!=null){
				paymentStatus = cache.getPaymentStatus(paymentStatusId);
			}
		} else {
			PaymentQueryCondition queryCondition = new PaymentQueryCondition();
			String[] deliveryQueryStatus = new String[]{DeliveryStatusEnum.未处理.getCode(),DeliveryStatusEnum.取单中.getCode(),DeliveryStatusEnum.送达中.getCode()};
			queryCondition.customerId(loginUser.getId()).status(deliveryQueryStatus);
			List<PaymentQueryJson> list = transactionManager.queryPayment(queryCondition).getData();
			if(CollectionTools.isNotEmpty(list)){
				paymentStatus = list.get(0);
				session.setAttribute("paymentStatusId",paymentStatus.getId());
			}
		}
		return paymentStatus;
	}
	
	/*
	public void releaseJson(String resultJson) throws IOException{
		response.setContentType("application/json");
		response.addHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out = response.getWriter();
		out.print(resultJson);
		out.flush();
		out.close();
	}*/
	
	public CustomerDO getCustomerDO(Long id){
		CustomerDO loginUser = (CustomerDO)session.getAttribute("loginUser");
		if(id != null && cache.isLogined(id)) {
			loginUser = customerManager.getCustomerById(id);
		}
		return loginUser;
	}
	
	public void saveMyPosition(Double lng,Double lat){
		CustomerDO loginUser = getCustomerDO(null);
		MapPoiDO mapPoiDO = new MapPoiDO();
		mapPoiDO.setLng(lng);
		mapPoiDO.setLat(lat);
		mapPoiDO.setIp(this.getIpAddr());
		if(loginUser!=null){
			mapPoiDO.setCustomerId(loginUser.getId());
		}
		customerManager.updateMapPoi(mapPoiDO);
		
		session.setAttribute(CookieKey.MAP_MYPOINT_LNG, lng);
		session.setAttribute(CookieKey.MAP_MYPOINT_LAT, lat);
	}
	
	public void saveShopDistance(Long shopId,Double distance){
		String key = CookieKey.MAP_SHOP_SHOP_SHOPID(shopId);
		session.setAttribute(key, distance);
	}
	
	public Double getShopDistance(Long shopId){
		String key = CookieKey.MAP_SHOP_SHOP_SHOPID(shopId);
		return (Double)session.getAttribute(key);
	}
	
	public String getCookie(String name){
		Cookie[] cookies = request.getCookies();
		if(cookies==null) return null;
		for(Cookie cookie:cookies){
			if(name.equals(cookie.getName())){
				return cookie.getValue();
			}
		}
		return null;
	}
	
	public Double getLng(){
		return (Double)session.getAttribute(CookieKey.MAP_MYPOINT_LNG);
	}
	
	public Double getLat(){
		return (Double)session.getAttribute(CookieKey.MAP_MYPOINT_LAT);
	}
	
	public Map<Long,Double> generateDistanceMap(Collection<ShopDO> list){
		Map<Long,Double> map = Maps.newHashMap();
		if(CollectionTools.isEmpty(list)){
			return map;
		}
		for(ShopDO shop : list){
			try {
				Long shopId = shop.getId();
				Double distance =	(Double)session.getAttribute(CookieKey.MAP_SHOP_SHOP_SHOPID(shopId));
				map.put(shopId, distance);
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
		return map;
	}
	
	public void putResult(List<SearchResult<?>> searchResult, Context context){
		loadShopList(searchResult,context);
		loadProdList(searchResult,context);
	}
	
	public void loadShopList(List<SearchResult<?>> searchResult, Context context){
		try {
			@SuppressWarnings("unchecked")
			SearchResult<ShopDO> rShopList = (SearchResult<ShopDO>) searchResult.get(0);
			context.put("shopList", rShopList.getDataObject());
		} catch (Exception e) {
			// TODO: do nothing
		}
	}
	
	public void loadProdList(List<SearchResult<?>> searchResult, Context context){
		try {
			@SuppressWarnings("unchecked")
			SearchResult<ProductDO> prodList = (SearchResult<ProductDO>) searchResult.get(1);
			context.put("prodList", prodList);
		} catch (Exception e) {
			// TODO: do nothing
		}
	}
	
	public String getIpAddr() {   
	     String ipAddress = null;   
	     //ipAddress = this.getRequest().getRemoteAddr();   
	     ipAddress = request.getHeader("x-forwarded-for");   
	     if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {   
	    	 ipAddress = request.getHeader("Proxy-Client-IP");   
	     }   
	     if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {   
	         ipAddress = request.getHeader("WL-Proxy-Client-IP");   
	     }   
	     if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {   
	    	 ipAddress = request.getRemoteAddr();   
	    	 if(ipAddress.equals("127.0.0.1")){   
	    		 //根据网卡取本机配置的IP   
	    		 InetAddress inet=null;   
	    		 try {   
	    			 inet = InetAddress.getLocalHost();   
	    		 } catch (UnknownHostException e) {   
	    			 e.printStackTrace();   
	    		 }   
	    		 ipAddress= inet.getHostAddress();   
	    	 }   
	     }   
	  
	     //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割   
	     if(ipAddress!=null && ipAddress.length()>15){ //"***.***.***.***".length() = 15   
	         if(ipAddress.indexOf(",")>0){   
	             ipAddress = ipAddress.substring(0,ipAddress.indexOf(","));   
	         }   
	     }   
	     return ipAddress;    
	  }    
}
