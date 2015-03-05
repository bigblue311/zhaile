package com.zhaile.web.webpage.screen.json;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.google.common.collect.Lists;
import com.victor.framework.common.shared.Result;
import com.zhaile.biz.web.manager.TransactionManager;
import com.zhaile.biz.web.model.ShoppingCarVO;
import com.zhaile.dal.model.CustomerDO;
import com.zhaile.dal.model.ShoppingCarDO;
import com.zhaile.web.common.screen.DefaultLayout;

public class UpdateShoppingCar extends DefaultLayout {
	@Autowired
	private HttpSession session;
	
	@Autowired
	private TransactionManager transactionManager;
	
	public Result<ShoppingCarVO> execute(@Param("prodId") Long prodId,
						@Param("quantity") Integer quantity,
						@Param("userId") Long userId) throws IOException{
		CustomerDO loginUser = super.getCustomerDO(userId);
		Result<ShoppingCarVO> result = null;
		ShoppingCarVO item = null;
		if(loginUser!=null){
			item = transactionManager.editShoppingCar(prodId, quantity, loginUser.getId()).getDataObject();
			result = Result.newInstance(item, "更新成功", true);
		} else {
			List<ShoppingCarVO> list = super.getShoppingCarList(userId);
			List<ShoppingCarVO> newList = Lists.newArrayList();
			if(list!=null){
				boolean find = false;
				for(ShoppingCarVO shoppingCarVO : list){
					if(shoppingCarVO.getShopCar().getProdId().equals(prodId)){
						ShoppingCarDO shoppingCar = shoppingCarVO.getShopCar();
						shoppingCar.setQuantity(quantity);
						item = transactionManager.shoppingCarDO2VO(shoppingCar);
						result = Result.newInstance(item, "更新成功", true);
						find = true;
						newList.add(item);
					} else {
						newList.add(shoppingCarVO);
					}
				}
				if(!find){
					item = null;
					result = Result.newInstance(item, "购物车中不存在", false);
				}
				newList = transactionManager.checkValid(newList);
				session.setAttribute("shoppingCar",newList);
			} else {
				item = null;
				result = Result.newInstance(item, "购物车为空", false);
			}
		}
		return result;
	}
}
