package com.zhaile.biz.web.admin.model;

import com.victor.framework.common.tools.MD5;
import com.zhaile.biz.web.form.LoginFromBean;

public class LoginCredential {
	public static String ADMIN_LOGIN = "admin";
	public static String ADMIN_PASSWORD = "b97008ef02193a006c22e6d38371022f";
	
	public static String SUPER_LOGIN = "super";
	public static String SUPER_PASSWORD = "f7adc7d37dc76f79d0a2773a4f7d46ff";
	
	public static Authentication login(LoginFromBean login){
		if(login == null){
			return null;
		}
		if(ADMIN_LOGIN.equals(login.getLoginId()) && ADMIN_PASSWORD.equals(MD5.getMD5(login.getPassword().getBytes()))){
			Authentication auth = new Authentication();
			auth.setLoginId(ADMIN_LOGIN);
			auth.setRole(Authentication.ROLE_ADMIN);
			auth.setIsZhaile(true);
			return auth;
		}
		if(SUPER_LOGIN.equals(login.getLoginId()) && SUPER_PASSWORD.equals(MD5.getMD5(login.getPassword().getBytes()))){
			Authentication auth = new Authentication();
			auth.setLoginId(SUPER_LOGIN);
			auth.setRole(Authentication.ROLE_SUPER);
			auth.setIsZhaile(true);
			return auth;
		}
		return null;
	}
}
