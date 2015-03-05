package com.zhaile.admin.webpage.screen.json;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.dataresolver.Params;
import com.victor.framework.common.shared.Result;
import com.victor.framework.common.tools.StringTools;
import com.zhaile.admin.webpage.filter.AdminLoginFilter;
import com.zhaile.biz.common.Cache;
import com.zhaile.biz.web.manager.FileUploadManager;
import com.zhaile.dal.model.AdvertisementDO;

public class UpdateSystemAd extends AdminLoginFilter {
	
	@Autowired
	private Cache cache;
	
	@Autowired
	private FileUploadManager fileUploadManager;
	
	public void execute(@Params AdvertisementDO advertisementDO) throws IOException {
		if(StringTools.isNotEmpty(advertisementDO.getImgSrc()) && advertisementDO.getImgSrc().contains("temp/")){
			Result<String> result = fileUploadManager.copyTemp(advertisementDO.getImgSrc());
			if(result.isSuccess()){
				advertisementDO.setImgSrc(result.getDataObject());
			} else {
				advertisementDO.setImgSrc("");
			}
		}
		if(StringTools.isNotEmpty(advertisementDO.getContent()) && advertisementDO.getContent().contains("temp/")){
			Result<String> result = fileUploadManager.copyTemp(advertisementDO.getContent());
			if(result.isSuccess()){
				advertisementDO.setContent(result.getDataObject());
			} else {
				advertisementDO.setContent("");
			}
		}
		cache.getAdvertisementcache().updateDB(advertisementDO);
    }
}
