package com.zhaile.dal.test;

import com.zhaile.dal.dao.PeopleContactDAO;
import com.zhaile.dal.dao.impl.PeopleContactDAOImpl;

public class PeopleContactDAOTest {
	public static void main(String[] args){
		PeopleContactDAO peopleDAO = new PeopleContactDAOImpl();
		System.out.println(peopleDAO.getByCustomerId(1l));
	}
}
