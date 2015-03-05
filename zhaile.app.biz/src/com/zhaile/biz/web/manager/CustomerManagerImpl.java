package com.zhaile.biz.web.manager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;
import com.victor.framework.common.shared.Result;
import com.victor.framework.common.tools.CollectionTools;
import com.victor.framework.common.tools.StringTools;
import com.zhaile.biz.common.manager.CommonManager;
import com.zhaile.biz.web.form.LoginFromBean;
import com.zhaile.biz.web.form.RegistFormBean;
import com.zhaile.biz.web.json.CommentJson;
import com.zhaile.biz.web.model.CommentVO;
import com.zhaile.biz.web.model.CustomerVO;
import com.zhaile.dal.dao.CustomerCommentDAO;
import com.zhaile.dal.dao.CustomerCreditDAO;
import com.zhaile.dal.dao.CustomerDAO;
import com.zhaile.dal.dao.CustomerFavDAO;
import com.zhaile.dal.dao.LogCreditDAO;
import com.zhaile.dal.dao.MapPoiDAO;
import com.zhaile.dal.dao.PeopleContactDAO;
import com.zhaile.dal.dao.ProductDAO;
import com.zhaile.dal.dao.ShopDAO;
import com.zhaile.dal.enumerate.ThirdPartUserEnum;
import com.zhaile.dal.model.CustomerCommentDO;
import com.zhaile.dal.model.CustomerCreditDO;
import com.zhaile.dal.model.CustomerDO;
import com.zhaile.dal.model.CustomerFavDO;
import com.zhaile.dal.model.LogCreditDO;
import com.zhaile.dal.model.MapPoiDO;
import com.zhaile.dal.model.PeopleContactDO;
import com.zhaile.dal.model.ProductDO;
import com.zhaile.dal.model.ShopDO;
import com.zhaile.dal.query.condition.CustomerCommentQueryCondition;
import com.zhaile.dal.query.condition.CustomerFavQueryCondition;
import com.zhaile.dal.query.condition.CustomerQueryCondition;
import com.zhaile.dal.query.condition.LogQueryCondition;

public class CustomerManagerImpl extends CommonManager implements CustomerManager {	
	
	@Autowired
	private CustomerDAO customerDAO;
	
	@Autowired
	private CustomerCommentDAO customerCommentDAO;
	
	@Autowired
	private PeopleContactDAO peopleContactDAO;
	
	@Autowired
	private CustomerCreditDAO customerCreditDAO;
	
	@Autowired
	private LogCreditDAO logCreditDAO;
	
	@Autowired
	private ShopDAO shopDAO;
	
	@Autowired
	private ProductDAO productDAO;
	
	@Autowired
	private CustomerFavDAO customerFavDAO;
	
	@Autowired
	private MapPoiDAO mapPoiDAO;
	
	@Override
	public CustomerDO login(LoginFromBean loginFromBean) {
		Result<CustomerDO> result = null;
		if(StringTools.isEmail(loginFromBean.getLoginId())){
			result = customerDAO.loginByEmail(loginFromBean.getLoginId(), loginFromBean.getPassword());
		} else if(StringTools.isMobile(loginFromBean.getLoginId())){
			result = customerDAO.loginByMobile(loginFromBean.getLoginId(), loginFromBean.getPassword());
		} else {
			result = customerDAO.loginByName(loginFromBean.getLoginId(), loginFromBean.getPassword());
		}
		return (CustomerDO)getObject(result);
	}

	@Override
	public Result<Boolean> checkExist(String loginId,String field) {
		if(StringTools.isEmpty(field)){
			return Result.newInstance(false, "无法检测", false);
		}
		if("name".equals(field)){
			return customerDAO.checkExistName(loginId);
		} 
		if("email".equals(field)){
			if(StringTools.isEmail(loginId)){
				return customerDAO.checkExistEmail(loginId);
			} else {
				return Result.newInstance(false, "非法的邮箱地址", false);
			}
		}
		if("mobile".equals(field)){
			if(StringTools.isMobile(loginId)){
				return customerDAO.checkExistMobile(loginId);
			} else {
				return Result.newInstance(false, "非法的手机号码", false);
			}
		}
		return Result.newInstance(false, "无法检测", false);
	}

	@Override
	public CustomerDO regist(RegistFormBean registFormBean) {
		CustomerDO customerDO = new CustomerDO();
		customerDO.setEmail(registFormBean.getEmail());
		customerDO.setName(registFormBean.getLoginId());
		customerDO.setPassword(registFormBean.getPassword());
		customerDO.setMobile(registFormBean.getMobile());
		Result<Long> result = customerDAO.insert(customerDO);
		if(result == null || !result.isSuccess()){
			return null;
		} else {
			customerDO.setId(result.getDataObject());
			return customerDO;
		}
	}

	@Override
	public void addComment(CustomerCommentDO comment) {
		customerCommentDAO.insert(comment);
	}

	@Override
	public Result<Long> addPeopleContact(PeopleContactDO peopleContactDO) {
		return peopleContactDAO.insert(peopleContactDO);
	}

	@Override
	public List<PeopleContactDO> getContactByCustomerId(Long customerId) {
		Object cList = getObject(peopleContactDAO.getByCustomerId(customerId));
		if(cList!=null){
			@SuppressWarnings("unchecked")
			List<PeopleContactDO> list = (List<PeopleContactDO>)cList;
			return list;
		}
		return null;
	}

	@Override
	public Result<Long> updatePeopleContact(PeopleContactDO peopleContactDO) {
		PeopleContactDO exist = (PeopleContactDO)getObject(peopleContactDAO.getById(peopleContactDO.getId()));
		if(exist!=null){
			exist.setAddress(peopleContactDO.getAddress());
			exist.setMobile(peopleContactDO.getMobile());
			exist.setPhone(peopleContactDO.getPhone());
			exist.setGender(peopleContactDO.getGender());
			exist.setName(peopleContactDO.getName());
			if(peopleContactDAO.update(exist).isSuccess()){
				return Result.newInstance(peopleContactDO.getId(),"更新客户联系方式成功",true);
			} else {
				return Result.newInstance(null,"更新客户联系方式失败",false);
			}
		} else {
			return Result.newInstance(null,"客户联系方式不存在",false);
		}
	}

	@Override
	public Result<Boolean> removePeopleContact(Long id) {
		PeopleContactDO exist = (PeopleContactDO)getObject(peopleContactDAO.getById(id));
		if(exist!=null){
			return peopleContactDAO.delete(id);
		} else {
			return Result.newInstance(false,"客户联系方式不存在",false);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerDO> getCustomer(String name, String email, String mobile) {
		List<CustomerDO> result = Lists.newArrayList();
		if(StringTools.isAllEmpty(name,email,mobile)){
			return result;
		}
		CustomerQueryCondition queryCondition = new CustomerQueryCondition();
		queryCondition.email(email).name(name).mobile(mobile);
		result = (List<CustomerDO>)getObject(customerDAO.getPage(queryCondition));
		return result;
	}
	
	@Override
	public CustomerVO getCustomer(Long id) {
		CustomerDO customerDO = (CustomerDO)getObject(customerDAO.getById(id));
		if(customerDO != null){
			return DO2VO(customerDO);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	private CustomerVO DO2VO(CustomerDO customerDO){
		CustomerVO customerVO = new CustomerVO();
		customerDO.translate();
		customerVO.setCustomer(customerDO);
		
		Long customerId = customerDO.getId();
		List<PeopleContactDO> contactList = (List<PeopleContactDO>)getObject(peopleContactDAO.getByCustomerId(customerId));
		for(PeopleContactDO people:contactList){
			people.translate();
		}
		customerVO.setContactList(contactList);
		
		CustomerCreditDO credit = (CustomerCreditDO)getObject(customerCreditDAO.getByCustomerId(customerId));
		customerVO.setCredit(credit);
		
		LogQueryCondition logQueryCondition = new LogQueryCondition();
		logQueryCondition.customerId(customerId);
		List<LogCreditDO> creditLogList = (List<LogCreditDO>)getObject(logCreditDAO.getPage(logQueryCondition));
		for(LogCreditDO creditLog:creditLogList){
			creditLog.translate();
		}
		customerVO.setCreditLogList(creditLogList);
		
		CustomerFavQueryCondition queryCondition = new CustomerFavQueryCondition();
		List<CustomerFavDO> favList = customerFavDAO.getPage(queryCondition.customerId(customerId)).getDataObject();
		if(CollectionTools.isNotEmpty(favList)){
			ArrayList<ShopDO> shopList = Lists.newArrayList();
			ArrayList<ProductDO> prodList = Lists.newArrayList();
			for(CustomerFavDO customerFavDO : favList) {
				ShopDO shop = shopDAO.getById(customerFavDO.getShopId()).getDataObject();
				if(shop!=null) {
					shopList.add(shop);
				}
				ProductDO prod = productDAO.getById(customerFavDO.getProdId()).getDataObject();
				if(prod!=null) {
					prodList.add(prod);
				}
			}
			customerVO.setShopList(shopList);
			customerVO.setProdList(prodList);
		}
		return customerVO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public CustomerDO login(String loginId, String mobile) {
		List<CustomerDO> result = null;
		CustomerQueryCondition queryCondition = new CustomerQueryCondition();
		queryCondition.mobile(mobile).name(loginId);
		result = (List<CustomerDO>)getObject(customerDAO.getPage(queryCondition));
		if(result == null || result.isEmpty()) {
			return null;
		} else {
			return result.get(0);
		}
	}
	
	@Override
	public CustomerDO loginFromQQ(String thirdPartUserId, String name) {
		return loginFromThirdPart(ThirdPartUserEnum.QQ,thirdPartUserId, name);
	}
	
	@Override
	public CustomerDO loginFromWechat(String thirdPartUserId, String name) {
		return loginFromThirdPart(ThirdPartUserEnum.微信,thirdPartUserId, name);
	}
	
	@Override
	public CustomerDO loginFromApp(String thirdPartUserId, String name) {
		return loginFromThirdPart(ThirdPartUserEnum.APP,thirdPartUserId, name);
	}
	
	@Override
	public CustomerDO loginFromThirdPart(ThirdPartUserEnum thirdPartUserEnum,String thirdPartUserId, String name) {
		if(StringTools.isAnyEmpty(thirdPartUserId,name)){
			return null;
		}
		CustomerDO result = null;
		result = (CustomerDO)getObject(customerDAO.loginFromThrid(thirdPartUserEnum.getCode(), thirdPartUserId));
		if(result == null) {
			CustomerDO customerDO = new CustomerDO();
			customerDO.setThirdPartUser(thirdPartUserEnum.getCode());
			customerDO.setThirdPartUserId(thirdPartUserId);
			customerDO.setName(name);
			Result<Long> regResult = customerDAO.insert(customerDO);
			if(regResult == null || !regResult.isSuccess()){
				return null;
			} else {
				customerDO.setId(regResult.getDataObject());
				return customerDO;
			}
		} else {
			return result;
		}
	}

	@Override
	public CustomerDO getCustomerById(Long id) {
		return customerDAO.getById(id).getDataObject();
	}

	@Override
	public List<CommentJson> getCommentJson(Date begin, Date end, Long... shopIds) {
		List<CommentJson> result = Lists.newArrayList();
		
		CustomerCommentQueryCondition queryCondition = new CustomerCommentQueryCondition();
		queryCondition.gmtModifyStart(begin).gmtModifyEnd(end).shopIds(shopIds);
		@SuppressWarnings("unchecked")
		List<CustomerCommentDO> commentList = (List<CustomerCommentDO>)getObject(customerCommentDAO.getPage(queryCondition));
		for(CustomerCommentDO comment : commentList){
			comment.translate();
			CommentJson json = new CommentJson();
			json.setId(comment.getId());
			json.setComment(comment.getContent());
			json.setCustomerId(comment.getCustomerId());
			json.setShopId(comment.getShopId());
			json.setStar(comment.getStar());
			json.setGmtModify(comment.getGmtModify());
			if(comment.getCustomerId()!=null){
				CustomerDO customer = (CustomerDO)getObject(customerDAO.getById(comment.getCustomerId()));
				if(customer!=null){
					CustomerVO cuctomerVO = DO2VO(customer);
					json.setCustomerName(customer.getName());
					json.setCustomerMobile(customer.getMobile());
					String contactList = "";
					for(PeopleContactDO contact:cuctomerVO.getContactList()){
						contactList += "<p>&nbsp;"+contact.toRevAddress()+"</p>";
					}
					json.setContact(contactList);
				} 
			} else {
				json.setCustomerName("游客");
			}
			if(comment.getShopId()!=null){
				ShopDO shop = (ShopDO)getObject(shopDAO.getById(comment.getShopId()));
				if(shop!=null){
					json.setShopName(shop.getName());
				}
			} else {
				json.setShopName("宅乐网");
			}
			result.add(json);
		}
		return result;
	}

	@Override
	public Result<Boolean> changePassword(String name, String mobile,String oldPassword, String newPassword) {
		CustomerDO customer = null;
		if(StringTools.isEmpty(oldPassword)) {
			//知道用户名和手机号码
			customer = customerDAO.checkByMobile(mobile, name).getDataObject();
		} else {
			if(StringTools.isEmpty(mobile)) {
				//知道用户名
				customer = customerDAO.loginByName(name, oldPassword).getDataObject();
			}
			if(StringTools.isEmpty(name)) {
				//知道手机，忘记了用户名
				customer = customerDAO.loginByMobile(mobile, oldPassword).getDataObject();
			}
		}
		if(customer == null) {
			return Result.newInstance(false,"客户校验失败",false);
		} else {
			CustomerDO forUpdate = new CustomerDO();
			forUpdate.setId(customer.getId());
			forUpdate.setPassword(newPassword);
			return customerDAO.update(forUpdate);
		}
	}

	@Override
	public Result<Long> addToFav(Long shopId, Long prodId, Long customerId) {
		CustomerFavQueryCondition queryCondition = new CustomerFavQueryCondition();
		queryCondition.shopId(shopId).customerId(customerId).prodId(prodId);
		if(shopId == null || customerId == null) {
			return Result.newInstance(null, "参数不能为空", false);
		}
		List<CustomerFavDO> favList = customerFavDAO.getPage(queryCondition).getDataObject();
		if(CollectionTools.isEmpty(favList)){
			CustomerFavDO customerFavDO = new CustomerFavDO();
			customerFavDO.setShopId(shopId);
			customerFavDO.setCustomerId(customerId);
			customerFavDO.setProdId(prodId);
			return customerFavDAO.insert(customerFavDO);
		} else {
			return Result.newInstance(null, "已经存在", false);
		}
	}

	@Override
	public Result<Boolean> removeFromFav(Long shopId,Long customerId) {
		CustomerFavQueryCondition queryCondition = new CustomerFavQueryCondition();
		queryCondition.shopId(shopId).customerId(customerId);
		if(shopId == null || customerId == null) {
			return Result.newInstance(false, "参数不能为空", false);
		}
		List<CustomerFavDO> favList = customerFavDAO.getPage(queryCondition).getDataObject();
		if(CollectionTools.isNotEmpty(favList)){
			for(CustomerFavDO fav : favList){
				Result<Boolean> result = customerFavDAO.delete(fav.getId());
				if(!result.isSuccess()){
					return Result.newInstance(false, "删除失败", false);
				}
			}
		}
		return Result.newInstance(true, "删除成功", true);
	}

	@Override
	public Result<Boolean> changePersonalInfo(CustomerDO loginUser,String password) {
		if(StringTools.isNotEmpty(loginUser.getEmail()) && !StringTools.isEmail(loginUser.getEmail())){
			return Result.newInstance(false,"非法的邮箱地址",false);
		}
		if(StringTools.isNotEmpty(loginUser.getMobile()) && !StringTools.isMobile(loginUser.getMobile())){
			return Result.newInstance(false,"非法的手机号码",false);
		}
		if(StringTools.isNotEmpty(password)){
		CustomerDO validPassword = customerDAO.loginByName(loginUser.getName(), password).getDataObject();
		if(validPassword == null) {
			return Result.newInstance(false,"密码错误",false);
		}}
		
		CustomerDO customer = new CustomerDO();
		customer.setId(loginUser.getId());
		customer.setEmail(loginUser.getEmail());
		customer.setMobile(loginUser.getMobile());
		return customerDAO.update(customer);
	}

	@Override
	public List<CommentVO> getCommentByUserId(Long userId) {
		CustomerDO customer = (CustomerDO)getObject(customerDAO.getById(userId));
		List<CommentVO> result = Lists.newArrayList();
		
		CustomerCommentQueryCondition queryCondition = new CustomerCommentQueryCondition();
		queryCondition.customerId(userId);
		@SuppressWarnings("unchecked")
		List<CustomerCommentDO> commentList = (List<CustomerCommentDO>)getObject(customerCommentDAO.getPage(queryCondition));
		for(CustomerCommentDO comment : commentList){
			CommentVO commentVO = new CommentVO();
			commentVO.setComment(comment);
			
			if(customer!=null){
				commentVO.setCustomer(customer);
			}
			result.add(commentVO);
		}
		return result;
	}

	@Override
	public void updateMapPoi(MapPoiDO mapPoiDO) {
		String ip = mapPoiDO.getIp();
		mapPoiDO.setMapType(ThirdPartUserEnum.百度.getCode());
		if(StringTools.isEmpty(ip) || !ip.contains(".")) return;
		MapPoiDO exist = mapPoiDAO.getByIp(ip).getDataObject();
		if(exist == null) {
			mapPoiDAO.insert(mapPoiDO);
		} else {
			mapPoiDAO.update(mapPoiDO);
		}
	}

	@Override
	public MapPoiDO getMapPoiByIP(String ip) {
		if(StringTools.isEmpty(ip) || !ip.contains(".")) return null;
		return mapPoiDAO.getByIp(ip).getDataObject();
	}
}
