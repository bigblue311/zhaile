package com.zhaile.web.webpage.screen.json;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;
import com.zhaile.biz.common.Cache;
import com.zhaile.biz.web.json.RandomProductJson;
import com.zhaile.biz.web.manager.ShopManager;
import com.zhaile.dal.model.ProductDO;
import com.zhaile.dal.model.ShopDO;
import com.zhaile.search.engine.db.DBSearchFacade;
import com.zhaile.web.common.screen.DefaultLayout;

public class RandomProduct extends DefaultLayout {
	
	@Autowired
	private Cache cache;
	
	@Autowired
	private DBSearchFacade searchFacade;
	
	@Autowired
	private ShopManager shopManager;
	
	public List<RandomProductJson> execute() throws IOException{
		List<RandomProductJson> result = Lists.newArrayList();
		List<ProductDO> list = searchFacade.randomProduct(120,cache.getValidLuckyCategoryId());
		for(ProductDO productDO : list){
			try {
				result.add(DO2Json(productDO));
			} catch (Exception e) {
				//do nothing
			}
		}
		return result;
	}
	
	private RandomProductJson DO2Json(ProductDO productDO){
		ShopDO shopDO = shopManager.getShopById(productDO.getShopId());
		RandomProductJson json = new RandomProductJson();
		json.setSrc(productDO.getImgS());
		json.setShopName(shopDO.getName());
		json.setShopLink("/shop.htm?id="+shopDO.getId());
		json.setCharge(shopDO.getCharge());
		json.setTitle(productDO.getName());
		json.setPrice(productDO.getPrice());
		json.setDesc(productDO.getDescription());
		json.setValid(cache.isShopValid(shopDO.getId()));
		json.setHref("/product.htm?id="+productDO.getId());
		json.setJsonUrl("/json/addToShoppingCar.json?prodId="+productDO.getId()+"&quantity=1");
		return json;
	}
}
