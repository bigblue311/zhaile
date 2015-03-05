package com.zhaile.web.webpage.screen.json;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.zhaile.dal.model.ProductDO;
import com.zhaile.search.engine.db.DBSearchFacade;
import com.zhaile.web.common.screen.DefaultLayout;

public class BestProductTop10 extends DefaultLayout {
	
	@Autowired
	private DBSearchFacade searchFacade;
	
	public List<ProductDO> execute() throws IOException{
		return searchFacade.bestProductTop10();
	}
}
