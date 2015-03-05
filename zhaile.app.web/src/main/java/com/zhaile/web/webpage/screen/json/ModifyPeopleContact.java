package com.zhaile.web.webpage.screen.json;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.google.common.collect.Lists;
import com.victor.framework.common.shared.Result;
import com.victor.framework.common.tools.SecurityTools;
import com.zhaile.biz.web.manager.CustomerManager;
import com.zhaile.dal.enumerate.ForeignKeyEnum;
import com.zhaile.dal.model.CustomerDO;
import com.zhaile.dal.model.PeopleContactDO;
import com.zhaile.web.common.screen.DefaultLayout;

public class ModifyPeopleContact extends DefaultLayout{
	@Autowired
	private HttpSession session;
	
	@Autowired
	private CustomerManager customerManager;
	
	@SuppressWarnings("unchecked")
	public List<PeopleContactDO> execute(@Param("id") Long id,
						@Param("gender") String gender,
						@Param("name") String name,
						@Param("mobile") String mobile,
						@Param("phone") String phone,
						@Param("address") String address,
						@Param("userId") Long userId
						) throws IOException{
		//安全过滤
		SecurityTools.injectionFilter(name);
		SecurityTools.injectionFilter(mobile);
		SecurityTools.injectionFilter(phone);
		SecurityTools.injectionFilter(address);
		
		PeopleContactDO peopleContactDO = new PeopleContactDO();
		peopleContactDO.setId(id==null?0l:id);
		peopleContactDO.setForeignKeyType(ForeignKeyEnum.用户.getCode());
		peopleContactDO.setGender(gender==null?"":gender);
		peopleContactDO.setName(name==null?"":name);
		peopleContactDO.setMobile(mobile==null?"":mobile);
		peopleContactDO.setPhone(phone==null?"":phone);
		peopleContactDO.setAddress(address==null?"":address);
		
		CustomerDO loginUser = super.getCustomerDO(userId);
		List<PeopleContactDO> resultList = Lists.newArrayList();
		if(loginUser != null){
			//如果已经登录过
			peopleContactDO.setForeignId(loginUser.getId());
			Result<Long> operResult = null;
			if(id==null){
				operResult = customerManager.addPeopleContact(peopleContactDO);
			} else {
				operResult = customerManager.updatePeopleContact(peopleContactDO);
			}
			if(operResult.isSuccess()){
				resultList = customerManager.getContactByCustomerId(loginUser.getId());
			}
		} else {
			//如果未登录过
			Object cList = session.getAttribute("peopleContact");
			List<PeopleContactDO> list = Lists.newArrayList();
			List<PeopleContactDO> newList = Lists.newArrayList();
			//session中是否为空
			if(cList!=null){
				list = (List<PeopleContactDO>) cList;
				//如果ID为空那么新加
				if(id == null){
					newList = list;
					newList.add(peopleContactDO);
				} else {
					//遍历旧的List
					for(int i=0;i<list.size();i++) {
						if(id.longValue() == Long.parseLong(i+1+"")){
							//更新该条记录
							newList.add(peopleContactDO);
						} else {
							//旧的记录仍保持
							newList.add(list.get(i));
						}
					}
				}
			} else {
				//session为空，那么新加
				newList.add(peopleContactDO);
			}
			//重新排ID
			for(int i=0;i<newList.size();i++) {
				newList.get(i).setId(Long.parseLong(i+1+""));
			}
			//更新Session
			session.setAttribute("peopleContact",newList);
			resultList = newList;
		}
		return resultList;
	}
}
