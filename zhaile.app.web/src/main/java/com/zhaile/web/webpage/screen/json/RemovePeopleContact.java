package com.zhaile.web.webpage.screen.json;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.google.common.collect.Lists;
import com.zhaile.biz.web.manager.CustomerManager;
import com.zhaile.dal.model.CustomerDO;
import com.zhaile.dal.model.PeopleContactDO;
import com.zhaile.web.common.screen.DefaultLayout;

public class RemovePeopleContact extends DefaultLayout{
	@Autowired
	private HttpSession session;
	
	@Autowired
	private CustomerManager customerManager;
	
	public List<PeopleContactDO> execute(@Param("id") Long id,@Param("userId") Long userId)throws IOException{
		CustomerDO loginUser = super.getCustomerDO(userId);
		if(loginUser != null){
			customerManager.removePeopleContact(id);
		} else {
			List<PeopleContactDO> list = super.getPeopleContactList(userId);
			List<PeopleContactDO> newList = Lists.newArrayList();
			//遍历旧的List
			for(int i=0;i<list.size();i++) {
				if(id.longValue() != Long.parseLong(i+1+"")){
					//旧的记录仍保持
					newList.add(list.get(i));
				}
			}
			//重新排ID
			for(int i=0;i<newList.size();i++) {
				newList.get(i).setId(Long.parseLong(i+1+""));
			}
			//更新Session
			session.setAttribute("peopleContact",newList);
		}
		return super.getPeopleContactList(userId);
	}
}	
