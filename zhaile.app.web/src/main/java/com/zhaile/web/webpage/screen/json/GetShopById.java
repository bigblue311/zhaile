package com.zhaile.web.webpage.screen.json;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.zhaile.biz.web.manager.ShopManager;
import com.zhaile.biz.web.model.ShopVO;
import com.zhaile.web.common.screen.DefaultLayout;

public class GetShopById extends DefaultLayout {
	
	@Autowired
	private ShopManager shopManager;
	
	public ShopVO execute(@Param("id") Long id)throws IOException{
		return shopManager.getShopVOById(id);
	}
}
