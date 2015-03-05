package com.zhaile.admin.webpage.screen.json;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.dataresolver.Params;
import com.victor.framework.common.shared.Result;
import com.victor.framework.common.tools.StringTools;
import com.zhaile.admin.webpage.filter.AdminLoginFilter;
import com.zhaile.biz.web.manager.FileUploadManager;
import com.zhaile.dal.dao.ProductDAO;
import com.zhaile.dal.model.ProductDO;

public class UpdateProdDetail extends AdminLoginFilter {
	
	@Autowired
	private ProductDAO productDAO;
	
	@Autowired
	private FileUploadManager fileUploadManager;
	
	public Boolean execute(@Params ProductDO prod) throws IOException {
		if(StringTools.isNotEmpty(prod.getImgS()) && prod.getImgS().contains("temp/")){
			Result<String> result = fileUploadManager.copyTemp(prod.getImgS());
			if(result.isSuccess()){
				prod.setImgS(result.getDataObject());
			} else {
				prod.setImgS("");
			}
		}
		
		return productDAO.update(prod).getDataObject();
    }
}
