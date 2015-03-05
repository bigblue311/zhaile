package com.zhaile.dal.query.condition;

import java.util.Date;

import com.victor.framework.common.tools.DateTools;
import com.victor.framework.dal.basic.QueryCondition;

public class CouponMetaQueryCondition extends QueryCondition{

	public CouponMetaQueryCondition keyword(String keyword) {
		put("keyword",keyword);
		return this;
	}
	
	public CouponMetaQueryCondition shopId(Long shopId) {
		put("shopId",shopId);
		return this;
	}
	
	public CouponMetaQueryCondition shopIds(Long[] shopIds) {
		put("shopIds",shopIds);
		return this;
	}
	
	public CouponMetaQueryCondition name(String name) {
		put("name",name);
		return this;
	}
	
	public CouponMetaQueryCondition enable(String enable) {
		put("enable",enable);
		return this;
	}
	
	public CouponMetaQueryCondition locked(String locked) {
		put("locked",locked);
		return this;
	}
	
	public CouponMetaQueryCondition chargable(String chargable) {
		put("chargable",chargable);
		return this;
	}
	
	public CouponMetaQueryCondition topupable(String topupable) {
		put("topupable",topupable);
		return this;
	}
	
	public CouponMetaQueryCondition refundable(String refundable) {
		put("refundable",refundable);
		return this;
	}
	
	public CouponMetaQueryCondition deductable(String deductable) {
		put("deductable",deductable);
		return this;
	}
	
	public CouponMetaQueryCondition fullsent(Double fullsent) {
		put("fullsent",fullsent);
		return this;
	}
	
	public CouponMetaQueryCondition sales(Double sales) {
		put("sales",sales);
		return this;
	}
	
	public CouponMetaQueryCondition valid(String valid) {
		put("valid",valid);
		return this;
	}
	
	@Override
	public CouponMetaQueryCondition gmtModifyStart(Date from) {
		put("gmtModifyStart", DateTools.getDayBegin(from));
		return this;
	}

	@Override
	public CouponMetaQueryCondition gmtModifyEnd(Date to) {
		put("gmtModifyEnd", DateTools.getDayEnd(to));
		return this;
	}

	@Override
	public CouponMetaQueryCondition start(int start) {
		put("start", start);
		return this;
	}

	@Override
	public CouponMetaQueryCondition pageSize(int pageSize) {
		put("pageSize", pageSize);
		return this;
	}

}
