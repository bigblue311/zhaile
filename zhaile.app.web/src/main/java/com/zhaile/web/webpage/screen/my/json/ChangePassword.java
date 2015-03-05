package com.zhaile.web.webpage.screen.my.json;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.victor.framework.common.shared.Result;
import com.zhaile.biz.web.manager.CustomerManager;
import com.zhaile.dal.model.CustomerDO;
import com.zhaile.web.common.screen.DefaultLayout;

public class ChangePassword extends DefaultLayout {
	
	@Autowired
	private CustomerManager customerManager;
	
	public Boolean execute(@Param("oldPwd") String oldPwd,
						@Param("newPwd") String newPwd
					   ) throws IOException{
		boolean result = false;
		CustomerDO loginUser = super.getCustomerDO(null);
		if(loginUser == null){
			result = false;
		}
		Result<Boolean> changeResult = customerManager.changePassword(loginUser.getName(), null, oldPwd, newPwd);
		result = changeResult.getDataObject();
		return result;
	}
}
