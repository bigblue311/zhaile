package com.zhaile.admin.webpage.screen;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.zhaile.biz.web.admin.model.Authentication;

public class Login {
	
	@Autowired
    private HttpSession session;
	
	public void execute(Navigator nav, Context context){
		Authentication auth = (Authentication)session.getAttribute("ADMIN_LOGIN");
		if(auth!=null){
			nav.redirectTo("admin").withTarget("welcome.vm");
			return;
		}
	}
}
