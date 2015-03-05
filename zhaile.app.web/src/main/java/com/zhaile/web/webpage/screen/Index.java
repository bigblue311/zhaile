/*
 * Copyright (c) 2002-2012 Alibaba Group Holding Limited.
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zhaile.web.webpage.screen;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.victor.framework.common.tools.CollectionTools;
import com.victor.framework.common.tools.StringTools;
import com.victor.framework.spymemcached.CacheService;
import com.zhaile.biz.common.Cache;
import com.zhaile.biz.web.manager.FlashGoManager;
import com.zhaile.biz.web.model.FlashGoVO;
import com.zhaile.dal.cache.key.SystemConfigCacheKey;
import com.zhaile.web.common.screen.DefaultLayout;

public class Index extends DefaultLayout {
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private Cache cache;
	
	@Autowired
	private CacheService memcachedService;
	
	@Autowired
	private FlashGoManager flashGoManager;
	
    public void execute(Context context, Navigator nav) {
    	if(cache.getSwitch(SystemConfigCacheKey.AD_FULL_SWITCH)){
    		String display = super.getCookie("adfullDisplay");
    		if(StringTools.isEmpty(display) || !display.equals("true")){
    			Boolean adfullDisplay = (Boolean)session.getAttribute("adfullDisplay");
    			if(adfullDisplay == null || !adfullDisplay){
    				nav.forwardTo("adfull");
        			session.setAttribute("adfullDisplay", true);
            		return;
    			}
    		}
    	}
    	super.load(context,null);
    	Map<String,String> thisWeeks = getThisWeek();
    	context.put("thisWeeks", thisWeeks);
    	List<FlashGoVO> recent = getRecent();
    	if(CollectionTools.isEmpty(recent) || recent.get(1) == null){
    		context.put("timeLeft", 0);
        	context.put("timeLast", 0);
    	} else {
    		FlashGoVO current = recent.get(1);
        	context.put("timeLeft", current.getTimeLeft());
        	context.put("timeLast", current.getTimeLast());
    	}
    	context.put("recent", recent);
    	context.put("title", "富阳宅乐网-外卖，美食，随叫随到!");
    }
    
    private Map<String, String> getThisWeek(){
    	String key = this.getClass().getSimpleName()+".getThisWeek";
    	@SuppressWarnings("unchecked")
    	Map<String, String> map = (Map<String, String>)memcachedService.getObject(key, Map.class);
		if(map == null){
			map = flashGoManager.getThisWeek();
			memcachedService.setObject(key, map, 12*60*60);
		}
		return map;
    }
    
    private List<FlashGoVO> getRecent(){
    	String key = this.getClass().getSimpleName()+".getRecent";
    	List<FlashGoVO> list = memcachedService.getList(key, FlashGoVO.class);
		if(list == null || list.isEmpty()){
			list = flashGoManager.getRecent();
			memcachedService.setObject(key, list, 12*60*60);
		}
		return list;
    }
}
