package com.zhaile.admin.webpage.screen.json;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.dataresolver.Params;
import com.victor.framework.common.shared.Result;
import com.victor.framework.common.tools.DateTools;
import com.victor.framework.common.tools.StringTools;
import com.zhaile.admin.webpage.filter.AdminLoginFilter;
import com.zhaile.biz.web.manager.FileUploadManager;
import com.zhaile.biz.web.manager.ShopManager;
import com.zhaile.dal.dao.ProductDAO;
import com.zhaile.dal.enumerate.EnableEnum;
import com.zhaile.dal.model.ProductDO;
import com.zhaile.dal.model.ShopDO;

public class CreateProdDetail extends AdminLoginFilter {
	@Autowired
	private ProductDAO productDAO;
	
	@Autowired
	private ShopManager shopManager;
	
	@Autowired
	private FileUploadManager fileUploadManager;
	
	public Boolean execute(@Params ProductDO prod) throws IOException {
		
		ShopDO shop = shopManager.getShopById(prod.getShopId());
		if(shop==null){
			return false;
		}
		prod.setDiscount(0d);
		prod.setDiscountFrom(DateTools.today());
		prod.setDiscountEnd(DateTools.today());
		prod.setValidFrom(DateTools.today());
		prod.setValidTo(DateTools.forever());
		prod.setGmtOpen(shop.getGmtOpen());
		prod.setGmtClose(shop.getGmtClose());
		prod.setEnable(EnableEnum.有效.getCode());
		
		if(StringTools.isNotEmpty(prod.getImgS()) && prod.getImgS().contains("temp/")){
			Result<String> result = fileUploadManager.copyTemp(prod.getImgS());
			if(result.isSuccess()){
				prod.setImgS(result.getDataObject());
			} else {
				prod.setImgS("");
			}
		}
		
		return productDAO.insert(prod).isSuccess();
    }
}
