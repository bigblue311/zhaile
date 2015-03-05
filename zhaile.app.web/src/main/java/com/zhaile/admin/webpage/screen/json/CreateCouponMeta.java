package com.zhaile.admin.webpage.screen.json;

import java.io.IOException;
import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.dataresolver.Params;
import com.victor.framework.common.shared.Result;
import com.victor.framework.common.tools.StringTools;
import com.zhaile.admin.webpage.filter.AdminLoginFilter;
import com.zhaile.biz.web.manager.CouponManager;
import com.zhaile.biz.web.manager.FileUploadManager;
import com.zhaile.biz.web.manager.ShopManager;
import com.zhaile.dal.enumerate.EnableEnum;
import com.zhaile.dal.model.CouponMetaDO;
import com.zhaile.dal.model.ShopDO;

public class CreateCouponMeta extends AdminLoginFilter {
	@Autowired
	private CouponManager couponManager;
	
	@Autowired
	private ShopManager shopManager;
	
	@Autowired
	private FileUploadManager fileUploadManager;
	
	public Boolean execute(@Params CouponMetaDO couponMetaDO) throws IOException, ParseException {
		
		ShopDO shop = shopManager.getShopById(couponMetaDO.getShopId());
		if(shop==null){
			return false;
		}
		couponMetaDO.setLocked(EnableEnum.无效.getCode());
		if(StringTools.isNotEmpty(couponMetaDO.getImgSrc()) && couponMetaDO.getImgSrc().contains("temp/")){
			Result<String> result = fileUploadManager.copyTemp(couponMetaDO.getImgSrc());
			if(result.isSuccess()){
				couponMetaDO.setImgSrc(result.getDataObject());
			} else {
				couponMetaDO.setImgSrc("");
			}
		}
		
		couponManager.createCouponMeta(couponMetaDO);
		return true;
    }
}
