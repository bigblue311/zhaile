package com.zhaile.web.webpage.screen.json;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.victor.framework.common.shared.Result;
import com.victor.framework.common.tools.SecurityTools;
import com.victor.framework.common.tools.StringTools;
import com.zhaile.biz.common.Cache;
import com.zhaile.biz.web.manager.CustomerManager;
import com.zhaile.biz.web.manager.TransactionManager;
import com.zhaile.biz.web.model.ShoppingCarVO;
import com.zhaile.dal.model.CustomerDO;
import com.zhaile.dal.model.PeopleContactDO;
import com.zhaile.web.common.screen.DefaultLayout;

public class LoginFromWechat extends DefaultLayout{
	@Autowired
    private HttpSession session;
	
	@Autowired
	private Cache cache;
	
	@Autowired
	private CustomerManager customerManager;
	
	@Autowired
	private TransactionManager transactionManager;
	
	private static final String SUCCESS = "T";
	private static final String FAILED = "F";
	
	public Result<CustomerDO> execute(@Param("thirdPartUserId") String thirdPartUserId,@Param("name") String name) throws IOException{
		//安全过滤
		SecurityTools.injectionFilter(name);
		
		if(StringTools.isAnyEmpty(thirdPartUserId,name)){
			return Result.newInstance(null, "参数为空", false);
		}
		session.removeAttribute("loginUser");
		CustomerDO customerDO = customerManager.loginFromWechat(thirdPartUserId, name);
		String success = SUCCESS;
		String msg = "";
		if(customerDO!=null){
			cache.login(customerDO.getId());
			success = SUCCESS;
			msg = "登录成功";
			List<ShoppingCarVO> list = super.getShoppingCarList(null);
			for(ShoppingCarVO shoppingCarVO : list){
				transactionManager.addToShoppingCar(shoppingCarVO.getShopCar().getProdId(), shoppingCarVO.getShopCar().getQuantity(), customerDO.getId());
			}
			session.removeAttribute("shoppingCar");
			
			List<PeopleContactDO> clist = super.getPeopleContactList(null);
			for(PeopleContactDO peopleContactDO : clist){
				peopleContactDO.setForeignId(customerDO.getId());
				customerManager.addPeopleContact(peopleContactDO);
			}
			session.removeAttribute("peopleContact");
			session.setAttribute("loginUser", customerDO);
		} else {
			success = FAILED;
			msg = "登录失败";
		}
		return Result.newInstance(customerDO, msg, success.equals(SUCCESS));
	}
}
