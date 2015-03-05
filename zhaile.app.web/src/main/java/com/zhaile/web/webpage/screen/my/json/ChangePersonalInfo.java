package com.zhaile.web.webpage.screen.my.json;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.victor.framework.common.shared.Result;
import com.zhaile.biz.web.manager.CustomerManager;
import com.zhaile.dal.model.CustomerDO;
import com.zhaile.web.common.screen.DefaultLayout;

public class ChangePersonalInfo extends DefaultLayout {
	
	@Autowired
	private CustomerManager customerManager;
	
	public Result<Boolean> execute(@Param("pwd") String pwd,
						@Param("mobile") String mobile,
						@Param("email") String email
					   ) throws IOException{
		Result<Boolean> result = Result.newInstance(false, "更新个人信息失败", false);
		CustomerDO loginUser = super.getCustomerDO(null);
		if(loginUser == null){
			result = Result.newInstance(false, "请重新登录", false);
		}
		loginUser.setEmail(email);
		loginUser.setMobile(mobile);
		result = customerManager.changePersonalInfo(loginUser, pwd);
		if(result.isSuccess()){
			result = Result.newInstance(true, "更新个人信息成功", true);
		}
		return result;
	}
}
