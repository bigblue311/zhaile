package com.zhaile.admin.webpage.action;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.alibaba.citrus.turbine.dataresolver.FormGroup;
import com.victor.framework.common.tools.CollectionTools;
import com.zhaile.biz.web.admin.model.Authentication;
import com.zhaile.biz.web.admin.model.LoginCredential;
import com.zhaile.biz.web.form.LoginFromBean;
import com.zhaile.biz.web.manager.CustomerManager;
import com.zhaile.biz.web.manager.ShopManager;
import com.zhaile.dal.model.CustomerDO;
import com.zhaile.dal.model.ShopDO;

public class LoginAction {
	
	@Autowired
    private HttpSession session;
	
	@Autowired
	private CustomerManager customerManager;
	
	@Autowired
	private ShopManager		shopManager;
	
	public void doLogin(@FormGroup("login") LoginFromBean loginVO, Navigator nav,Context context) {
		Authentication auth = LoginCredential.login(loginVO);
		if(auth != null){
			session.setAttribute("ADMIN_LOGIN", auth);
			nav.redirectTo("admin").withTarget("welcome.vm");
		} else {
			//尝试店家登录
			CustomerDO customer = customerManager.login(loginVO);
			if(customer != null){
				List<ShopDO> shops = shopManager.getShopsByCustomerID(customer.getId());
				if(CollectionTools.isEmpty(shops)){
					session.removeAttribute("ADMIN_LOGIN");
					nav.redirectTo("admin").withTarget("login.vm");
				} else {
					auth = new Authentication();
					auth.setLoginId(loginVO.getLoginId());
					auth.setRole(Authentication.ROLE_SHOP);
					auth.setShops(shops);
					auth.setIsZhaile(false);
					session.setAttribute("ADMIN_LOGIN", auth);
					nav.redirectTo("admin").withTarget("welcome.vm");
				}
				
			} else {
				session.removeAttribute("ADMIN_LOGIN");
				nav.redirectTo("admin").withTarget("login.vm");
			}
		}		
	}
	
	public void doLogout(Navigator nav){
		session.removeAttribute("ADMIN_LOGIN");
		nav.redirectTo("admin").withTarget("login.vm");
	}
}
