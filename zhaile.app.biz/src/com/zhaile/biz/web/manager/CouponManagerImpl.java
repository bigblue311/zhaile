package com.zhaile.biz.web.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.victor.framework.dal.basic.Paging;
import com.zhaile.biz.common.manager.CommonManager;
import com.zhaile.biz.web.model.CouponCardVO;
import com.zhaile.biz.web.model.CouponMetaVO;
import com.zhaile.dal.dao.CouponCardDAO;
import com.zhaile.dal.dao.CouponMetaDAO;
import com.zhaile.dal.dao.ShopDAO;
import com.zhaile.dal.enumerate.EnableEnum;
import com.zhaile.dal.model.CouponCardDO;
import com.zhaile.dal.model.CouponMetaDO;
import com.zhaile.dal.model.ShopDO;
import com.zhaile.dal.query.condition.CouponCardQueryCondition;
import com.zhaile.dal.query.condition.CouponMetaQueryCondition;

public class CouponManagerImpl extends CommonManager implements CouponManager {

	@Autowired
	private CouponMetaDAO couponMetaDAO;
	
	@Autowired
	private CouponCardDAO couponCardDAO;
	
	@Autowired
	private ShopDAO shopDAO;
	
	@Autowired
	private CustomerManager customerManager;
	
	@Override
	public void createCouponMeta(CouponMetaDO couponMetaDO) {
		couponMetaDAO.insert(couponMetaDO);
	}
	
	@Override
	public void createCouponCard(Long couponMetaId, Long customerId) {
		CouponMetaDO couponMetaDO = getCouponMetaById(couponMetaId);
		if(couponMetaDO!=null){
			CouponCardDO couponCardDO = new CouponCardDO();
			couponCardDO.setEnable(EnableEnum.有效.getCode());
			couponCardDO.setLocked(EnableEnum.无效.getCode());
			couponCardDO.setValidFrom(couponMetaDO.getValidFrom());
			couponCardDO.setValidTo(couponMetaDO.getValidTo());
			couponCardDO.setMetaId(couponMetaDO.getId());
			couponCardDO.setCustomerId(customerId);
			couponCardDO.setBalance(couponMetaDO.getAmount());
			couponCardDO.setCountDown(couponMetaDO.getDeductCount());
			couponCardDAO.insert(couponCardDO);
		}
	}

	@Override
	public void updateCouponMeta(CouponMetaDO couponMetaDO) {
		couponMetaDAO.update(couponMetaDO);
	}
	
	@Override
	public void updateCouponCard(CouponCardDO couponCardDO) {
		couponCardDAO.update(couponCardDO);
	}

	@Override
	public void enableCouponMeta(Long id) {
		CouponMetaDO forupdate = new CouponMetaDO();
		forupdate.setId(id);
		forupdate.setEnable(EnableEnum.有效.getCode());
		couponMetaDAO.update(forupdate);
	}

	@Override
	public void disableCouponMeta(Long id) {
		CouponMetaDO forupdate = new CouponMetaDO();
		forupdate.setId(id);
		forupdate.setEnable(EnableEnum.无效.getCode());
		couponMetaDAO.update(forupdate);
	}
	
	@Override
	public void enableCouponCard(Long id) {
		CouponCardDO forupdate = new CouponCardDO();
		forupdate.setId(id);
		forupdate.setEnable(EnableEnum.有效.getCode());
		couponCardDAO.update(forupdate);
	}

	@Override
	public void disableCouponCard(Long id) {
		CouponCardDO forupdate = new CouponCardDO();
		forupdate.setId(id);
		forupdate.setEnable(EnableEnum.无效.getCode());
		couponCardDAO.update(forupdate);
	}

	@Override
	public void lockCouponCard(Long id) {
		CouponCardDO forupdate = new CouponCardDO();
		forupdate.setId(id);
		forupdate.setLocked(EnableEnum.有效.getCode());
		couponCardDAO.update(forupdate);
	}

	@Override
	public void unlockCouponCard(Long id) {
		CouponCardDO forupdate = new CouponCardDO();
		forupdate.setId(id);
		forupdate.setLocked(EnableEnum.无效.getCode());
		couponCardDAO.update(forupdate);
	}
	
	@Override
	public void deleteCouponMeta(Long id) {
		couponMetaDAO.delete(id);
	}

	@Override
	public CouponMetaDO getCouponMetaById(Long id) {
		return couponMetaDAO.getById(id).getDataObject();
	}
	
	@Override
	public CouponMetaVO getCouponMetaVOById(Long id) {
		CouponMetaVO couponMetaVO = new CouponMetaVO();
		CouponMetaDO couponMeta = getCouponMetaById(id);
		couponMetaVO.setCouponMeta(couponMeta);
		if(couponMeta != null){
			ShopDO shop = shopDAO.getById(couponMeta.getShopId()).getDataObject();
			couponMetaVO.setShop(shop);
		}
		return couponMetaVO;
	}
	
	@Override
	public CouponCardDO getCouponCardById(Long id) {
		return couponCardDAO.getById(id).getDataObject();
	}

	@Override
	public CouponCardVO getCouponCardVOById(Long id) {
		return cardDO2VO(couponCardDAO.getById(id).getDataObject());
	}

	@SuppressWarnings("unchecked")
	@Override
	public Paging<CouponMetaDO> getCouponMeta(CouponMetaQueryCondition queryCondition) {
		int totalSize = couponMetaDAO.getCount(queryCondition).getDataObject();
		Paging<CouponMetaDO> couponListPage = queryCondition.getPaging(totalSize, 5);
		List<CouponMetaDO> couponList = couponMetaDAO.getPage(queryCondition).getDataObject();
		couponListPage.setData(couponList);
		return couponListPage;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Paging<CouponCardVO> getCouponCardVO(CouponCardQueryCondition queryCondition) {
		int totalSize = couponCardDAO.getCount(queryCondition).getDataObject();
		Paging<CouponCardVO> couponListPage = queryCondition.getPaging(totalSize, 5);
		List<CouponCardDO> couponList = couponCardDAO.getPage(queryCondition).getDataObject();
		List<CouponCardVO> toList = Lists.transform(couponList, new Function<CouponCardDO,CouponCardVO>(){

			@Override
			public CouponCardVO apply(CouponCardDO couponCardDO) {
				return cardDO2VO(couponCardDO);
			}
			
		});
		couponListPage.setData(toList);
		return couponListPage;
	}
	
	private CouponCardVO cardDO2VO(CouponCardDO couponCardDO){
		if(couponCardDO == null) {
			return null;
		}
		CouponCardVO couponCardVO = new CouponCardVO();
		couponCardVO.setCouponCard(couponCardDO);
		couponCardVO.setValidFrom(couponCardDO.getValidFrom());
		couponCardVO.setValidTo(couponCardDO.getValidTo());
		couponCardVO.setCouponMeta(couponMetaDAO.getById(couponCardDO.getMetaId()).getDataObject());
		couponCardVO.setCustomer(customerManager.getCustomer(couponCardDO.getCustomerId()));
		return couponCardVO;
	}

	@Override
	public List<CouponCardDO> getCouponCardByCondition(CouponCardQueryCondition queryCondition) {
		return couponCardDAO.getByCondition(queryCondition).getDataObject();
	}
}
