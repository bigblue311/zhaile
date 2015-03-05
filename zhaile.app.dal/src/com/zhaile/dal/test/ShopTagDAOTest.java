package com.zhaile.dal.test;

import com.zhaile.dal.dao.ShopTagDAO;
import com.zhaile.dal.dao.impl.ShopTagDAOImpl;

public class ShopTagDAOTest {
	public static void main(String[] args){
		ShopTagDAO tagDAO = new ShopTagDAOImpl();
		System.out.println(tagDAO.getAllByCategoryId(1l));
		System.out.println("=======================================");
		System.out.println(tagDAO.getAllByShopId(1l));
		System.out.println("=======================================");
		System.out.println(tagDAO.getOnlyByShopId(1l));
		System.out.println("=======================================");
		System.out.println(tagDAO.getOnlyByProdId(1l));
	}
}
