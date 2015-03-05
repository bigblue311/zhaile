package com.zhaile.web.webpage.screen.json;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.google.common.collect.Lists;
import com.victor.framework.common.tools.SecurityTools;
import com.victor.framework.common.tools.StringTools;
import com.victor.framework.search.basic.KeyWord;
import com.zhaile.search.engine.db.DBSearchFacade;
import com.zhaile.web.common.screen.DefaultLayout;

public class KeywordAutoComplete extends DefaultLayout{
	
	@Autowired
	private DBSearchFacade searchFacade;
	
	public List<KeyWord> execute(@Param("shopId") Long shopId,
						@Param("categoryId") Long categoryId,
						@Param("word") String word) throws IOException{
		//安全过滤
		SecurityTools.injectionFilter(word);
		
		List<KeyWord> list = Lists.newArrayList();
		
		if(StringTools.isEmpty(word) && shopId ==null && categoryId == null){
			list = Lists.newArrayList();
		} else {
			list = searchFacade.keywordAutoComplete(shopId,categoryId,word);
		}
		return list;
	}
}
