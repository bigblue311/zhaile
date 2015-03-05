package com.zhaile.web.webpage.screen.json;

import java.io.IOException;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.zhaile.dal.model.CustomerDO;
import com.zhaile.web.common.screen.DefaultLayout;

public class GetLoginedUser extends DefaultLayout {
	
	public CustomerDO execute(@Param("userId") Long userId) throws IOException{
		return super.getCustomerDO(userId);
	}
}
