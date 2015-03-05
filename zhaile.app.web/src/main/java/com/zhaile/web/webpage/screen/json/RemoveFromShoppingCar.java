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
import com.zhaile.web.common.screen.DefaultLayout;

public class RemoveFromShoppingCar extends DefaultLayout {
	@Autowired
	private HttpSession session;
	
	@Autowired
	private TransactionManager transactionManager;
	
	public Result<Boolean> execute(@Param("prodId") Long prodId,@Param("userId") Long userId) throws IOException{
		CustomerDO loginUser = super.getCustomerDO(userId);
		Result<Boolean> result = null;
		if(loginUser!=null){
			result = transactionManager.RemoveFromShoppingCar(prodId, loginUser.getId());
		} else {
			List<ShoppingCarVO> list = super.getShoppingCarList(userId);
			List<ShoppingCarVO> newList = Lists.newArrayList();
			if(list!=null){
				boolean find = false;
				for(ShoppingCarVO shoppingCarVO : list){
					if(shoppingCarVO.getShopCar().getProdId().equals(prodId)){
						find = true;
						result = Result.newInstance(true, "从购物车中移除成功", true);
					} else {
						newList.add(shoppingCarVO);
					}
				}
				if(!find){
					result = Result.newInstance(false, "购物车中不存在", false);
				}
			} else {
				result = Result.newInstance(false, "购物车中不存在", false);
			}
			newList = transactionManager.checkValid(newList);
			session.setAttribute("shoppingCar",newList);
		}
		return result;
	}
}
