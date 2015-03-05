package com.zhaile.web.webpage.screen.json;

import java.io.IOException;
import java.util.List;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.zhaile.biz.web.model.ShoppingCarVO;
import com.zhaile.web.common.screen.DefaultLayout;

public class GetShoppingCar extends DefaultLayout {
	
	public List<ShoppingCarVO> execute(@Param("userId") Long userId) throws IOException{
		return super.getShoppingCarList(userId);
	}
}
