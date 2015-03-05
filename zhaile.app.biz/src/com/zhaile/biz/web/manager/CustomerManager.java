package com.zhaile.biz.web.manager;

import java.util.Date;
import java.util.List;

import com.victor.framework.common.shared.Result;
import com.zhaile.biz.web.form.LoginFromBean;
import com.zhaile.biz.web.form.RegistFormBean;
import com.zhaile.biz.web.json.CommentJson;
import com.zhaile.biz.web.model.CommentVO;
import com.zhaile.biz.web.model.CustomerVO;
import com.zhaile.dal.enumerate.ThirdPartUserEnum;
import com.zhaile.dal.model.CustomerCommentDO;
import com.zhaile.dal.model.CustomerDO;
import com.zhaile.dal.model.MapPoiDO;
import com.zhaile.dal.model.PeopleContactDO;

public interface CustomerManager {
	
	/**
	 * 注册用户
	 * @param registFormBean
	 * @return
	 */
	CustomerDO regist(RegistFormBean registFormBean);
	
	/**
	 * 用户登录
	 * @param loginFromBean
	 * @return
	 */
	CustomerDO login(LoginFromBean loginFromBean);
	
	/**
	 * 用手机号码登录
	 * @param loginId
	 * @param mobile
	 * @return
	 */
	CustomerDO login(String loginId,String mobile);
	
	/**
	 * QQ登录
	 * @param thirdPartUserId
	 * @param name
	 * @return
	 */
	CustomerDO loginFromQQ(String thirdPartUserId,String name);
	
	/**
	 * 微信登录
	 * @param thirdPartUserId
	 * @param name
	 * @return
	 */
	CustomerDO loginFromWechat(String thirdPartUserId,String name);
	
	/**
	 * 手机App用户登录
	 * @param thirdPartUserId
	 * @param name
	 * @return
	 */
	CustomerDO loginFromApp(String thirdPartUserId, String name);
	
	/**
	 * 从第三方登录
	 * @param thirdPartUserEnum
	 * @param thirdPartUserId
	 * @param name
	 * @return
	 */
	CustomerDO loginFromThirdPart(ThirdPartUserEnum thirdPartUserEnum,String thirdPartUserId, String name);
	
	/**
	 * 修改密码
	 * @param name
	 * @param mobile
	 * @param oldPassword
	 * @param newPassword
	 * @return
	 */
	Result<Boolean> changePassword(String name,String mobile,String oldPassword,String newPassword);
	
	/**
	 * 修改个人信息
	 * @param loginUser
	 * @param password
	 * @return
	 */
	Result<Boolean> changePersonalInfo(CustomerDO loginUser,String password);
	
	/**
	 * 检查用户是否存在
	 * @param loginId
	 * @param field
	 * @return
	 */
	Result<Boolean> checkExist(String loginId,String field);
	
	/**
	 * 添加评论
	 * @param comment
	 */
	void addComment(CustomerCommentDO comment);
	
	/**
	 * 获取用户联系方式
	 * @param customerId
	 * @return
	 */
	List<PeopleContactDO> getContactByCustomerId(Long customerId);
	
	/**
	 * 添加用户联系方式
	 * @param peopleContactDO
	 * @return
	 */
	Result<Long> addPeopleContact(PeopleContactDO peopleContactDO);
	
	/**
	 * 更新用户联系方式
	 * @param peopleContactDO
	 * @return
	 */
	Result<Long> updatePeopleContact(PeopleContactDO peopleContactDO);
	
	/**
	 * 删除用户联系方式
	 * @param id
	 * @return
	 */
	Result<Boolean> removePeopleContact(Long id);
	
	/**
	 * 获取用户列表
	 * @param name
	 * @param email
	 * @param mobile
	 * @return
	 */
	List<CustomerDO> getCustomer(String name, String email, String mobile);
	
	/**
	 * 获取一个用户
	 * @param id
	 * @return
	 */
	CustomerVO getCustomer(Long id);
	
	/**
	 * 获取一个用户
	 * @param id
	 * @return
	 */
	CustomerDO getCustomerById(Long id);
	
	/**
	 * 获取评论json对象
	 * @param begin
	 * @param end
	 * @param shopIds
	 * @return
	 */
	List<CommentJson> getCommentJson(Date begin, Date end, Long...shopIds);
	
	/**
	 * 根据用户ID获取评论
	 * @param userId
	 * @return
	 */
	List<CommentVO> getCommentByUserId(Long userId);
	
	/**
	 * 添加到收藏夹
	 * @param shopId
	 * @param prodId
	 * @param customerId
	 * @return
	 */
	Result<Long> addToFav(Long shopId, Long prodId, Long customerId);
	
	/**
	 * 从收藏夹中删除
	 * @param shopId
	 * @param customerId
	 * @return
	 */
	Result<Boolean> removeFromFav(Long shopId,Long customerId);
	
	/**
	 * 更新地图坐标
	 * @param mapPoiDO
	 */
	void updateMapPoi(MapPoiDO mapPoiDO);
	
	/**
	 * 根据IP获取地图坐标
	 * @param ip
	 * @return
	 */
	MapPoiDO getMapPoiByIP(String ip);
}
