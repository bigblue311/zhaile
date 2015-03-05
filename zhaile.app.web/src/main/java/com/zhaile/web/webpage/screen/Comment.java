package com.zhaile.web.webpage.screen;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.zhaile.biz.web.manager.ShopManager;
import com.zhaile.biz.web.model.ShopVO;
import com.zhaile.web.common.screen.DefaultLayout;

public class Comment extends DefaultLayout {
	
	@Autowired
	private ShopManager shopManager;
	
	public void execute(@Param("id") Long id,Context context){
		super.load(context,null);
		ShopVO shopVO = shopManager.getShopVOById(id);
		context.put("shopVO", shopVO);
		if(shopVO ==null || shopVO.getShopDO() ==null) {
			String title = "富阳宅乐网";
			context.put("title", title);
		} else {
			String title = "富阳宅乐网-"+shopVO.getShopDO().getName();
			context.put("title", title);
			context.put("commentList",shopVO.getCommentList());
			context.put("prodId","");
			context.put("shopId",shopVO.getShopDO().getId());
		}
	}
}
