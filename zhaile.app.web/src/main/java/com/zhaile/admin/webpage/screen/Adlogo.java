package com.zhaile.admin.webpage.screen;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.zhaile.admin.webpage.filter.AdminLoginFilter;
import com.zhaile.biz.common.Cache;
import com.zhaile.dal.model.AdvertisementDO;

public class Adlogo extends AdminLoginFilter {
	
	@Autowired
	private Cache cache;
	
	public void execute(@Param("command") String command,Context context,Navigator nav) throws IOException{
		boolean access = super.doFilter(this.getClass().getSimpleName().toLowerCase(),context,nav);
		if(!access){
			return;
		}
		if(command!=null && command.equalsIgnoreCase("reload")){
			cache.reload();
		}
		List<Integer> adPlist = Lists.newArrayList(75);
		List<AdvertisementDO> adList = Lists.transform(adPlist, new Function<Integer,AdvertisementDO>(){
			@Override
			public AdvertisementDO apply(Integer position) {
				return cache.getAdvertisementcache().getAd(position);
			}
		});
		context.put("title", "网站LOGO");
		context.put("hasLink", false);
		context.put("allowedWidth", 564);
		context.put("allowedHeight", 171);
		context.put("previewWidth", 198);
		context.put("previewHeight", 60);
		context.put("adList", JSONObject.toJSONString(adList));
	}
}
