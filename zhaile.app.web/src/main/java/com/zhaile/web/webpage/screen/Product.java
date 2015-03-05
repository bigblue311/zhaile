package com.zhaile.web.webpage.screen;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.zhaile.biz.common.Cache;
import com.zhaile.biz.web.manager.ShopManager;
import com.zhaile.biz.web.model.ProductVO;
import com.zhaile.web.common.screen.DefaultLayout;

public class Product extends DefaultLayout {
	@Autowired
	private Cache cache;
	
	@Autowired
	private ShopManager shopManager;
	
	public void execute(@Param("id") Long id,@Param("adId") Long adId,Context context){
		super.load(context,null);
		if(adId!=null){
			cache.getAdvertisementcache().click(adId);
		}
		ProductVO prodVO = shopManager.getProdVOById(id);
		context.put("prodVO", prodVO);
		if(prodVO ==null || prodVO.getProductDO() ==null) {
			String title = "富阳宅乐网";
			context.put("title", title);
		} else {
			String title = "富阳宅乐网-"+prodVO.getProductDO().getName();
			context.put("title", title);
			context.put("commentList",prodVO.getCommentList());
			context.put("prodId",prodVO.getProductDO().getId());
			context.put("shopId",prodVO.getProductDO().getShopId());
		}
	}
}
