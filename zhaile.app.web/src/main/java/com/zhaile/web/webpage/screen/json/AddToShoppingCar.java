package com.zhaile.web.webpage.screen.json;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.victor.framework.common.shared.Result;
import com.zhaile.biz.common.Cache;
import com.zhaile.biz.web.manager.ShopManager;
import com.zhaile.biz.web.manager.TransactionManager;
import com.zhaile.biz.web.model.ShoppingCarVO;
import com.zhaile.dal.model.CustomerDO;
import com.zhaile.dal.model.ShopDO;
import com.zhaile.dal.model.ShoppingCarDO;
import com.zhaile.web.common.screen.DefaultLayout;

public class AddToShoppingCar extends DefaultLayout{
	@Autowired
	private HttpSession session;
	
	@Autowired
	private Cache cache;
	
	@Autowired
	private TransactionManager transactionManager;
	
	@Autowired
	private ShopManager shopManager;
	
	public Result<ShoppingCarVO> execute(@Param("prodId") Long prodId,
						@Param("quantity") Integer quantity,
						@Param("adId") Long adId,
						@Param("userId") Long userId
					   ) throws IOException{
		if(adId!=null){
			cache.getAdvertisementcache().click(adId);
		}
		CustomerDO loginUser = super.getCustomerDO(userId);
		
		Result<ShoppingCarVO> result = null;
		ShopDO shop = shopManager.getShopByProdId(prodId);
		if(shop == null){
			return Result.newInstance(null, "店铺已打烊", false);
		}
		if(!cache.isShopValid(shop.getId())){
			return Result.newInstance(null, "店铺已打烊", false);
		}
		Double distance = super.getShopDistance(shop.getId());
		if(shop.getDistance()!=null &&  distance != null){
			if(shop.getDistance() <= distance){
				return Result.newInstance(null, "超过了店铺起送距离", false);
			}
		}
		if(loginUser!=null){
			//如果登录过
			result = transactionManager.addToShoppingCar(prodId, quantity, loginUser.getId());
		} else {
			List<ShoppingCarVO> list = super.getShoppingCarList(userId);
			boolean find = false;
			for(ShoppingCarVO shoppingCarVO : list){
				if(shoppingCarVO.getShopCar().getProdId().equals(prodId)){
					result = Result.newInstance(null, "购物车中已经存在", false);
					find = true;
				}
			}
			if(!find){
				ShoppingCarDO shoppingCarDO = new ShoppingCarDO();
				shoppingCarDO.setProdId(prodId);
				shoppingCarDO.setQuantity(quantity);
				ShoppingCarVO shoppingCarVO = transactionManager.shoppingCarDO2VO(shoppingCarDO);
				list.add(shoppingCarVO);
				list = transactionManager.checkValid(list);
				session.setAttribute("shoppingCar",list);
				result = Result.newInstance(shoppingCarVO, "添加到购物车成功", true);
			}
		}
		return result;
	}
}
