package com.zhaile.admin.webpage.screen.json;

import java.io.IOException;
import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alibaba.citrus.turbine.dataresolver.Params;
import com.victor.framework.common.shared.Result;
import com.victor.framework.common.tools.DateTools;
import com.victor.framework.common.tools.StringTools;
import com.zhaile.biz.web.manager.FileUploadManager;
import com.zhaile.biz.web.manager.FlashGoManager;
import com.zhaile.dal.model.FlashGoDO;

public class UpdateFlashGo {
	
	@Autowired
	private FlashGoManager flashGoManager;
	
	@Autowired
	private FileUploadManager fileUploadManager;
	
	public void execute(@Params FlashGoDO flashGoDO, 
						@Param("gmtOpenString") String gmtOpenString,
						@Param("gmtCloseString") String gmtCloseString) throws IOException, ParseException {
		flashGoDO.setGmtOpen(DateTools.StringToDate(gmtOpenString));
		flashGoDO.setGmtClose(DateTools.StringToDate(gmtCloseString));
		flashGoDO = setAdImg(flashGoDO);
		if(flashGoDO.getId()!=null){
			flashGoManager.updateFlashGoEvent(flashGoDO);
		} else {
			flashGoManager.addFlashGoEvent(flashGoDO);
		}
	}
	
	private FlashGoDO setAdImg(FlashGoDO flashGoDO){
		if(StringTools.isNotEmpty(flashGoDO.getAdImg()) && flashGoDO.getAdImg().contains("temp/")){
			Result<String> result = fileUploadManager.copyTemp(flashGoDO.getAdImg());
			if(result.isSuccess()){
				flashGoDO.setAdImg(result.getDataObject());
			} else {
				flashGoDO.setAdImg("");
			}
		}
		return flashGoDO;
	}
}
