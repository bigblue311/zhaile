package com.zhaile.biz.web.form;

import com.victor.framework.common.tools.StringTools;
import com.zhaile.dal.query.condition.ShopQueryCondition;

public class ShopQueryFormBean extends QueryFormBean{
	private String shopName;
	private int page = 1;
	private int pageSize = 20;
	
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	
	public ShopQueryCondition getShopQueryCondition(){
		ShopQueryCondition queryCondition = new ShopQueryCondition();
		queryCondition.clearEnable();
		if(StringTools.isNotEmpty(shopName)){
			queryCondition.keyword(shopName);
		}
		if(modifyStart!=null){
			queryCondition.gmtModifyStart(modifyStart);
		}
		if(modifyEnd!=null){
			queryCondition.gmtModifyEnd(modifyEnd);
		}
		queryCondition.pageSize(pageSize).start((page-1)*pageSize);
		return queryCondition;
	}
}
