package com.zhaile.dal.test;

import com.zhaile.dal.dao.ProductDAO;
import com.zhaile.dal.dao.impl.ProductDAOImpl;
import com.zhaile.dal.query.condition.ProductQueryCondition;

public class ProductDAOTest {
	public static void main(String[] args){
		ProductDAO productDAO = new ProductDAOImpl();
		ProductQueryCondition queryContion = new ProductQueryCondition();
		queryContion.keyword("鲜肉");
		//System.out.println(productDAO.getPage(queryContion));
		System.out.println(productDAO.getRandom(2,2l));
	}
}
