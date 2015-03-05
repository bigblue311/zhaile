package com.zhaile.dal.query.condition;

import java.util.Date;

import com.victor.framework.common.tools.DateTools;
import com.victor.framework.common.tools.StringTools;
import com.victor.framework.dal.basic.QueryCondition;

public class CouponCardQueryCondition extends QueryCondition{

	public CouponCardQueryCondition enable(String enable) {
		put("enable",enable);
		return this;
	}
	
	public CouponCardQueryCondition locked(String locked) {
		put("locked",locked);
		return this;
	}
	
	public CouponCardQueryCondition valid(String valid) {
		put("valid",valid);
		return this;
	}
	
	public CouponCardQueryCondition customerName(String customerName) {
		if(StringTools.isNotEmpty(customerName)){
			put("customerName",customerName);
		}
		return this;
	}
	
	public CouponCardQueryCondition customerPhone(String customerPhone) {
		if(StringTools.isNotEmpty(customerPhone)){
			put("customerPhone",customerPhone);
		}
		return this;
	}
	
	public CouponCardQueryCondition customerId(Long customerId) {
		put("customerId",customerId);
		return this;
	}
	
	public CouponCardQueryCondition metaId(Long metaId) {
		put("metaId",metaId);
		return this;
	}
	
	public CouponCardQueryCondition shopId(Long shopId) {
		put("shopId",shopId);
		return this;
	}
	
	public CouponCardQueryCondition shopIds(Long[] shopIds) {
		put("shopIds",shopIds);
		return this;
	}
	
	public CouponCardQueryCondition name(String name) {
		put("name",name);
		return this;
	}
	
	public CouponCardQueryCondition chargable(String chargable) {
		put("chargable",chargable);
		return this;
	}
	
	public CouponCardQueryCondition topupable(String topupable) {
		put("topupable",topupable);
		return this;
	}
	
	public CouponCardQueryCondition refundable(String refundable) {
		put("refundable",refundable);
		return this;
	}
	
	public CouponCardQueryCondition deductable(String deductable) {
		put("deductable",deductable);
		return this;
	}
	
	public CouponCardQueryCondition fullsent(Double fullsent) {
		put("fullsent",fullsent);
		return this;
	}
	
	public CouponCardQueryCondition sales(Double sales) {
		put("sales",sales);
		return this;
	}
	
	@Override
	public CouponCardQueryCondition gmtModifyStart(Date from) {
		put("gmtModifyStart", DateTools.getDayBegin(from));
		return this;
	}

	@Override
	public CouponCardQueryCondition gmtModifyEnd(Date to) {
		put("gmtModifyEnd", DateTools.getDayEnd(to));
		return this;
	}

	@Override
	public CouponCardQueryCondition start(int start) {
		put("start", start);
		return this;
	}

	@Override
	public CouponCardQueryCondition pageSize(int pageSize) {
		put("pageSize", pageSize);
		return this;
	}

}
