package com.zhaile.web.webpage.screen.json;

import java.io.IOException;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.victor.framework.common.shared.Result;
import com.zhaile.web.common.screen.DefaultLayout;

public class SaveShopDistance extends DefaultLayout {
	
	public Result<Boolean> execute(@Param("shopId") Long shopId,@Param("distance") Double distance)throws IOException{
		Result<Boolean> result = null;
		if(shopId!=null&&distance!=null){
			super.saveShopDistance(shopId, distance);
			result = Result.newInstance(true, "更新成功", true);
		} else {
			result = Result.newInstance(false, "参数不能为空", false);
		}
		return result;
	}
}
