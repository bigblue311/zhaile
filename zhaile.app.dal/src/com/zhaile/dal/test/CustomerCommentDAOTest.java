package com.zhaile.dal.test;

import java.util.Date;
import java.util.List;

import com.victor.framework.common.shared.Result;
import com.zhaile.dal.dao.CustomerCommentDAO;
import com.zhaile.dal.dao.impl.CustomerCommentDAOImpl;
import com.zhaile.dal.model.CustomerCommentDO;
import com.zhaile.dal.query.condition.CustomerCommentQueryCondition;

public class CustomerCommentDAOTest {
	public static void main(String[] args){
		CustomerCommentDAO customerCommentDAO = new CustomerCommentDAOImpl();
		CustomerCommentDO customerCommentDO = new CustomerCommentDO();
		customerCommentDO.setContent("很好");
		customerCommentDO.setCustomerId(11l);
		customerCommentDO.setGmtCreate(new Date());
		customerCommentDO.setGmtModify(new Date());
		customerCommentDO.setLikeLevel("0");
		customerCommentDO.setProdId(123l);
		customerCommentDO.setStar("3");
		customerCommentDO.translate();
		System.out.println(customerCommentDO);
		customerCommentDO.format();
		Result<Long> insert = customerCommentDAO.insert(customerCommentDO);
		System.out.println(insert.getDataObject()+":"+insert.getMessage());
		CustomerCommentQueryCondition queryCondtion = new CustomerCommentQueryCondition();
		queryCondtion.customerId(11l);
		Result<List<CustomerCommentDO>> result = customerCommentDAO.getPage(queryCondtion);
		System.out.println(result.getMessage());
		if(result.isSuccess()){
			if(result.getDataObject()!=null) {
				List<CustomerCommentDO> list = result.getDataObject();
				System.out.println(list.toString());
			}
		}
	}
}
