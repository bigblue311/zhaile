package com.zhaile.admin.webpage.screen.json;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.dataresolver.Params;
import com.victor.framework.common.shared.Result;
import com.victor.framework.common.tools.StringTools;
import com.zhaile.admin.webpage.filter.AdminLoginFilter;
import com.zhaile.biz.web.admin.model.ShopDetailVO;
import com.zhaile.biz.web.manager.FileUploadManager;
import com.zhaile.biz.web.manager.ShopManager;

public class UpdateShopDetail extends AdminLoginFilter {
	
	@Autowired
	private ShopManager shopManager;
	
	@Autowired
	private FileUploadManager fileUploadManager;
	
	public Map<String, Long> execute(@Params ShopDetailVO shopDetailVO) throws IOException {
		if(StringTools.isNotEmpty(shopDetailVO.getShopImage()) && shopDetailVO.getShopImage().contains("temp/")){
			Result<String> result = fileUploadManager.copyTemp(shopDetailVO.getShopImage());
			if(result.isSuccess()){
				shopDetailVO.setShopImage(result.getDataObject());
			} else {
				shopDetailVO.setShopImage("");
			}
		}
		if(StringTools.isNotEmpty(shopDetailVO.getLicenseImg()) && shopDetailVO.getLicenseImg().contains("temp/")){
			Result<String> result = fileUploadManager.copyTemp(shopDetailVO.getLicenseImg(),"宅乐网 www.fyzhaile.com");
			if(result.isSuccess()){
				shopDetailVO.setLicenseImg(result.getDataObject());
			} else {
				shopDetailVO.setLicenseImg("");
			}
		}
		return shopManager.updateShopDetail(shopDetailVO);
    }
}
