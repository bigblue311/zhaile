package com.zhaile.admin.webpage.screen.json;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.dataresolver.Param;
import com.victor.framework.common.tools.PinYinTools;
import com.zhaile.admin.webpage.filter.AdminLoginFilter;
import com.zhaile.dal.dao.CustomerDAO;
import com.zhaile.dal.dao.ShopDAO;
import com.zhaile.dal.model.CustomerDO;
import com.zhaile.dal.model.ShopDO;

public class UpdateShopPassword extends AdminLoginFilter {
	
	@Autowired
	private CustomerDAO customerDAO;
	
	@Autowired
	private ShopDAO shopDAO;
	
	public Long execute(@Param("customerId") Long id,@Param("shopId") Long shopId, @Param("password") String password) throws IOException {
		if(id==null){
			ShopDO shop = shopDAO.getById(shopId).getDataObject();
			if(shop!=null){
				CustomerDO customerDO = new CustomerDO();
				customerDO.setName(PinYinTools.getPinYin(shop.getName()).toLowerCase());
				customerDO.setPassword(password);
				id = customerDAO.insert(customerDO).getDataObject();
			}
		} else {
			CustomerDO customerDO = new CustomerDO();
			customerDO.setId(id);
			customerDO.setPassword(password);
			if(super.isZhaile()){
				customerDAO.update(customerDO);
			} else {
				if(super.getShopIds()!=null){
					for(Long existId : super.getShopIds()){
						if(existId.longValue() == shopId.longValue()) {
							customerDAO.update(customerDO);
						}
					}
				}
			}
		}
		return id;
    }
}
