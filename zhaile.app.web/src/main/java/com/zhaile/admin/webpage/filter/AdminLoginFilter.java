package com.zhaile.admin.webpage.filter;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.google.common.collect.Maps;
import com.victor.framework.common.tools.DateTools;
import com.zhaile.biz.common.Cache;
import com.zhaile.biz.web.admin.model.Authentication;
import com.zhaile.dal.model.ShopDO;

public class AdminLoginFilter  {
	@Autowired
    private HttpSession session;
	
	@Autowired
	private Cache cache;
	
	private static Map<String,Boolean> resource = Maps.newHashMap();
	private static Map<String,String> parentMenuItem = Maps.newHashMap();
	
	static{
		// TURE: locked ONLY zhaile access FALSE: shop can access
		resource.put("cache", true);
		resource.put("systemAd", true);
		resource.put("customer", true);
		resource.put("coupon", false);
		resource.put("comment", true);
		resource.put("shop", false);
		resource.put("employee", true);
		resource.put("order", false);
		resource.put("payment", false);
		resource.put("messageCenter", true);
		resource.put("mapMarker", true);
		resource.put("flashGo", true);
		resource.put("adfull", true);
		resource.put("adfocus", true);
		resource.put("adlogo", true);
		resource.put("adtop", true);
		resource.put("adright", true);
		resource.put("adShops", true);
		
		//系统管理
		parentMenuItem.put("cache", "systemMgr");
		parentMenuItem.put("searchLog", "systemMgr");
		parentMenuItem.put("messageCenter", "systemMgr");
		//广告管理
		parentMenuItem.put("adfocus", "adMgr");
		parentMenuItem.put("adfull", "adMgr");
		parentMenuItem.put("adlogo", "adMgr");
		parentMenuItem.put("adtop", "adMgr");
		parentMenuItem.put("adright", "adMgr");
		parentMenuItem.put("adshops", "adMgr");
		parentMenuItem.put("mapMarker", "adMgr");
		parentMenuItem.put("flashGo", "adMgr");
		//客户管理
		parentMenuItem.put("customer", "customerMgr");
		parentMenuItem.put("comment", "customerMgr");
		//员工管理
		parentMenuItem.put("employee", "employeeMgr");
		//店铺管理
		parentMenuItem.put("shop", "shopMgr");
		parentMenuItem.put("coupon", "shopMgr");
		//订单管理
		parentMenuItem.put("payment", "orderMgr");
		parentMenuItem.put("order", "orderMgr");
	}
	
	public boolean doFilter(String pageName, Context context, Navigator nav) throws IOException {
		Authentication auth = (Authentication)session.getAttribute("ADMIN_LOGIN");
		context.put("cacheTools",cache);
		if(auth==null){
			nav.redirectTo("admin").withTarget("login.vm");
			return false;
		} else {
			context.put("auth", auth);
			context.put("pageName", pageName);
			context.put("pageTopName", parentMenuItem.get(pageName));
			if(!pageName.equals("welcome") && !auth.getIsZhaile()) {
				Boolean locked = false;
				if(resource.get(pageName) == null) {
					locked = true;
				} else{
					locked = resource.get(pageName);
				}
				if(locked) {
					nav.redirectTo("admin").withTarget("welcome.vm");
					return false;
				}
			}
		}
		return true;
	}
	
	public Long[] getShopIds(){
		Authentication auth = (Authentication)session.getAttribute("ADMIN_LOGIN");
		if(auth==null){
			return null;
		} else {
			Long[] shopIdList = null;
			List<ShopDO> shops = auth.getShops();
			if(shops!=null){
				shopIdList = new Long[shops.size()];
				for(int i=0;i<shops.size();i++) {
					shopIdList[i] = shops.get(i).getId();
				}
				return shopIdList;
			}
			return null;
		}
	}
	
	public boolean isZhaile(){
		Authentication auth = (Authentication)session.getAttribute("ADMIN_LOGIN");
		if(auth==null){
			return false;
		} else {
			return auth.getIsZhaile();
		}
	}
	
	public Date StringToDate(String date){
		Date toDate = DateTools.today();
		try {
			toDate = DateTools.StringToDate(date);
		} catch (Exception e) {
			toDate = DateTools.today();
		}
		return toDate==null?DateTools.today():toDate;
	}
}
