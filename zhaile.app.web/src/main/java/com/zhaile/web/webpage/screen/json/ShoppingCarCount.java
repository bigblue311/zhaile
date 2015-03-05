package com.zhaile.web.webpage.screen.json;

import java.io.IOException;
import java.util.List;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.zhaile.biz.web.model.ShoppingCarVO;
import com.zhaile.web.common.screen.DefaultLayout;

public class ShoppingCarCount extends DefaultLayout{
	
	public int execute(@Param("userId") Long userId) throws IOException{
		List<ShoppingCarVO> list = super.getShoppingCarList(userId);
		return list.size();
	}
}
